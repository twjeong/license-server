<template>
<div class="bg_contents">
  <div class="container">
    <!-- 페이지 타이틀  -->
    <section class="pagetitle clearfix">
      <div class="f_left">
        <h2 class="inline_block mr10">설정</h2>      <!-- 타이틀  -->
        <p class="inline_block">알림 설정, SSL 인증서 업로드를 할 수 있습니다.</p>     <!-- 설명  -->
      </div>
      <div class="section_title">
        <a @click="initMailAlarmInformation()"><img src="../static/img/icon_refresh.png" class="icon_refresh mt7" style="width:13px"></a>
      </div>
      <div class="f_right txt_right mr10">
        <p class="inline_block">Home / <span>설정</span></p>       <!-- 페이지 경로  -->
      </div>
    </section>

    <!-- 알림 설정  -->
    <section class="section_block">
      <div class="section_title">
        <h3 class="inline_block mr20">알림 설정</h3>
        <p class="inline_block">알림 메일 전송을 위한 SMTP 정보를 설정합니다. 알림 메일은 매일 오전 9시에 발송됩니다.</p>
      </div>
      <div class="section_contents mt20">
          <!-- 알림기능 설정 -->
          <div>
            <label class="w150">알림 기능 설정</label>
            <div class="btn_switch">
              <label class="switch">
                <input type="checkbox" @change="toggleMailAlarmCheckbox()" id="btn_toggle">
                <span class="slider round"></span>
              </label>

              <p class="toggle_txt ml5" id="toggle_off" style="display: none">OFF</p>
              <p class="toggle_txt ml5" id="toggle_on" style="display: inline-block">ON</p>
            </div>
          </div>

          <!--  팝업 창 : 알림수신 이메일 설정  -->
          <div class="pw_email_add_complete popup_win w400">
            <h2>알림 설정</h2>
            <div class="wrap_cont">
              알림 설정이 완료되었습니다
              <div class="mt20 txt_center">
                <input type="button" value="닫기" onclick="history.go(0);"
                        class="btn_cancel btn_basic color_indigo btn_outline w80">
              </div>
            </div>
          </div>

          <!--  팝업 창 : 알림수신 이메일 삭제  -->
          <div class="pw_email_delete_complete popup_win w400">
            <h2>알림 설정</h2>
            <div class="wrap_cont">
              알림 설정 정보가 삭제되었습니다
              <div class="mt20 txt_center">
                <input type="button" value="닫기" onclick="history.go(0);"
                        class="btn_cancel btn_basic color_indigo btn_outline w80">
              </div>
            </div>
          </div>

          <!-- 알림수신 이메일 설정 -->
          <div class="mt20">
            <label class="w150">SMTP HOST</label>
            <input type="text" id="smtpAddress" name="smtpAddress" placeholder="(예: smtp.gmail.com)"
                    class="w500">
          </div>
          <div class="mt20">
            <label class="w150">SMTP PORT</label>
            <input type="text" id="smtpPort" name="smtpPort" class="w50">
          </div>
          <div class="mt20">
            <label class="w150">받는 사람 E-Mail 주소</label>
            <input type="text" id="toAddress" name="toAddress" class="w500">
          </div>
          <div class="mt20">
            <label class="w150">보내는 사람 E-Mail 주소</label>
            <input type="text" id="fromAddress" name="fromAddress" class="w500">
          </div>
          <div class="mt20">
            <label class="w150">Username</label>
            <input type="text" id="username" name="username" placeholder="사용하실 메일 서버 계정을 입력하세요."
                    class="w500">
          </div>
          <div class="mt20">
            <label class="w150">Password</label>
            <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요." class="w500">
          </div>

          <input type="hidden" id="isUsingTls" name="isUsingTls" value="1">

          <!-- 버튼 영역 -->
          <div class="txt_center mt30">
            <input type="button" @click="registerMailInfo()" id="mailInfoSubmit"
            value="서버 정보 등록" class="btn_basic btn_cancel bg_bluegreen w120">
            <input type="button" @click="showEmailInfoDeleteCheckView()"
            value="초기화" class="btn_basic color_indigo btn_outline w120 ml10">
          </div>
      </div>
    </section>

    <!-- 서버 인증서 변경  -->
    <section class="section_block">
      <div class="section_title">
        <h3 class="inline_block mr20">서버 SSL 인증서 업로드</h3>
        <p class="inline_block">Policy Server의 SSL 인증서 파일을 교체할 수 있습니다.
            새로운 인증서가 적용되기 위해서는 서버 재시작이 필요합니다.</p>
      </div>
      <div class="section_contents">
        <div class="mt20 w500">
          <label class="w100">인증서 파일</label>
          <v-file-input
            ref="certFileInput"
            v-model="certFile"
            show-size
            @change="loadCertFile"
            accept=".crt">
          </v-file-input>
        </div>
        <div class="ml10">
          <input
            type="text"
            v-model="certFileDesc"
            style="background-color: #ecf0f1;"
            class="w500" readonly>
        </div>

        <div class="mt20 w500">
          <label class="w100">KEY 파일</label>
          <v-file-input
            ref="keyFileInput"
            v-model="keyFile"
            show-size
            @change="loadKeyFile"
            accept=".key">
          </v-file-input>
        </div>
        <div class="ml10">
          <input
            type="text"
            v-model="keyFileDesc"
            style="background-color: #ecf0f1;"
            class="w500" readonly>
        </div>

        <div class="mt30">
          <label class="w100">인증서 정보</label>
          <section class="section_block mt10 ml10 w500"
            style="background-color: #ecf0f1;">
            <p ref="certInfo" class="certInformation">발급 대상
              <br> CN : {{ this.countryName }}
              <br><br>발급자
              <br> 국가 : {{ this.organizationName }}
              <br> 회사명 : {{ this.organizationalUnitName }}
              <br><br>유효 기간
              <br> 발급일 : {{ this.validityNotBefore }}
              <br> 만료일 : {{ this.validityNotAfter }}
              <br><br>개인 키
              <br> KEY 길이 : {{ this.keyLength }}
            </p>
          </section>
        </div>

        <!-- 버튼 영역 -->
        <div class="txt_center mt30">
          <input type="button" @click="sendSSLFile()" value="업로드" class="btn_basic bg_bluegreen w120">
        </div>
      </div>
    </section>

    <!--  팝업 창 : 메일 서버 정보 삭제 전 확인 -->
    <modal name="EmailInfoDeleteCheck"
      :draggable="true"
      @closed="closeEmailInfoDeleteCheckView()"
      :width="400"
      :height="150">
      <EmailInfoDeleteCheckPopup
        v-if="isEmailInfoDeleteCheckViewd"
        @ok="deleteMailInfo()"
        @close-modal="closeEmailInfoDeleteCheckView()">
      </EmailInfoDeleteCheckPopup>
    </modal>

    <!--  팝업 창 : 메일 서버 정보 삭제  -->
    <modal name="EmailInfoDelete"
      :draggable="true"
      @closed="closeEmailInfoDeleteView()"
      :width="250"
      :height="150">
      <EmailInfoDeletePopup
        v-if="isEmailInfoDelViewed"
        @close-modal="closeEmailInfoDeleteView()">
      </EmailInfoDeletePopup>
    </modal>

    <!--  팝업 창 : E-Mail 등록  -->
    <modal name="EmailInfoRegister"
      :draggable="true"
      @closed="closeEmailInfoRegisterView()"
      :width="250"
      :height="150">
      <EmailInfoRegisterPopup
        v-if="isEmailInfoRegViewed"
        @close-modal="closeEmailInfoRegisterView()">
      </EmailInfoRegisterPopup>
    </modal>

    <!--  팝업 창 : SSL 파일 업로드  -->
    <modal name="SSLFileUpload"
      :draggable="true"
      @closed="closeSSLFileUploadView()"
      :width="300"
      :height="150">
      <SSLFileUploadPopup
        v-if="isSSLFileUploadViewed"
        @close-modal="closeSSLFileUploadView()">
      </SSLFileUploadPopup>
    </modal>
  </div>

  <modal name="AlertPopup"
    @closed="closeAlertView()"
    :clickToClose="false"
    :width="320"
    :height="200">
    <AlertPopup
      v-if="isAlertViewed"
      :msg="alertMsg"
      @close-modal="closeAlertView()">
    </AlertPopup>
  </modal>
</div>
</template>

<script>
import axios from 'axios'
import EmailInfoRegisterPopup from './Setup/EmailInfoRegisterPopup'
import EmailInfoDeleteCheckPopup from './Setup/EmailInfoDeleteCheckPopup'
import EmailInfoDeletePopup from './Setup/EmailInfoDeletePopup'
import SSLFileUploadPopup from './Setup/SSLFileUploadPopup'
import AlertPopup from './Component/AlertPopup.vue'

export default {
  components: {
    EmailInfoRegisterPopup,
    EmailInfoDeleteCheckPopup,
    EmailInfoDeletePopup,
    SSLFileUploadPopup,
    AlertPopup
  },
  data () {
    return {
      isEmailInfoRegViewed: false,
      isEmailInfoDelViewed: false,
      isEmailInfoDeleteCheckViewd: false,
      isSSLFileUploadViewed: false,
      isAlertViewed: false,
      certFile: null,
      keyFile: null,
      certFileDesc: '인증서 파일 정보',
      keyFileDesc: 'KEY 파일 정보',
      countryName: '',
      organizationName: '',
      organizationalUnitName: '',
      validityNotBefore: '',
      validityNotAfter: '',
      keyLength: ''
    }
  },
  mounted () {
    this.initToggleMailAlarmCheckbox()
    this.initMailAlarmInformation()
    this.$refs.certInfo.style.display = 'none'
  },
  methods: {
    initToggleMailAlarmCheckbox: function () {
      axios.get('/license-service/setup/email/alarmFlag').then(
        res => {
          if (res.data) {
            document.getElementById('btn_toggle').checked = true
            document.getElementById('toggle_off').style.display = 'none'
            document.getElementById('toggle_on').style.display = 'inline-block'
          } else {
            document.getElementById('btn_toggle').checked = false
            document.getElementById('toggle_off').style.display = 'inline-block'
            document.getElementById('toggle_on').style.display = 'none'
          }

          this.checkAlarmCheckBox()
        })
    },
    toggleMailAlarmCheckbox: function () {
      this.checkAlarmCheckBox()
      axios.put('/license-service/setup/email/alarmFlag', { flag: document.getElementById('btn_toggle').checked }).then(res => {
        this.initToggleMailAlarmCheckbox()
      }).catch((err) => {
        alert(err)
      })
    },
    initMailAlarmInformation: function () {
      axios.get('/license-service/setup/email').then(res => {
        if (res.data.length === 0) {
          document.getElementById('smtpAddress').value = ''
          document.getElementById('smtpPort').value = ''
          document.getElementById('toAddress').value = ''
          document.getElementById('fromAddress').value = ''
          document.getElementById('username').value = ''
          document.getElementById('password').value = ''
        } else {
          document.getElementById('smtpAddress').value = res.data.smtpAddress
          document.getElementById('smtpPort').value = res.data.smtpPort
          document.getElementById('toAddress').value = res.data.toAddress
          document.getElementById('fromAddress').value = res.data.fromAddress
          document.getElementById('username').value = res.data.userName
        }
      })
    },
    validateEmail: function (email) {
      const re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i
      return re.test(email)
    },
    registerMailInfo: function () {
      const smtpAddress = document.getElementById('smtpAddress').value
      const smtpPort = document.getElementById('smtpPort').value
      const toAddress = document.getElementById('toAddress').value
      const fromAddress = document.getElementById('fromAddress').value
      const username = document.getElementById('username').value
      const password = document.getElementById('password').value

      if (smtpAddress.length <= 0) { this.showAlertView('SMTP HOST를 입력하세요'); return false }
      if (smtpPort.length <= 0) { this.showAlertView('SMTP PORT를 입력하세요'); return false }
      if (toAddress.length <= 0) { this.showAlertView('받는 사람 E-Mail 주소를 입력하세요'); return false }
      if (fromAddress.length <= 0) { this.showAlertView('보내는 사람 E-Mail 주소를 입력하세요'); return false }
      if (username.length <= 0) { this.showAlertView('사용자 메일 서버 계정을 입력하세요'); return false }
      if (password.length <= 0) { this.showAlertView('비밀번호를 입력하세요'); return false }

      if (smtpAddress.length > 128) { this.showAlertView('SMTP HOST 최대 입력 길이를 초과하였습니다.'); return false }
      if (isNaN(smtpPort)) { this.showAlertView('사용 가능한 SMTP PORT가 아닙니다.'); return false }
      if (smtpPort <= 0 || smtpPort > 65535) { this.showAlertView('사용 가능한 SMTP PORT가 아닙니다.'); return false }
      if (toAddress.length > 128) { this.showAlertView('받는 사람 E-Mail 주소 최대 입력 길이를 초과하였습니다.'); return false }
      if (fromAddress.length > 128) { this.showAlertView('보내는 사람 E-Mail 주소 최대 입력 길이를 초과하였습니다.'); return false }

      if (this.validateEmail(toAddress) === false) {
        this.showAlertView('받는 사람 E-Mail 주소가 유효한 E-Mail이 아닙니다.')
        return false
      }

      if (this.validateEmail(fromAddress) === false) {
        this.showAlertView('보내는 사람 E-Mail 주소가 유효한 E-Mail이 아닙니다.')
        return false
      }

      if (username.length > 128) { this.showAlertView('사용자 메일 서버 계정 최대 입력 길이를 초과하였습니다.'); return false }
      if (password.length > 128) { this.showAlertView('비밀번호 최대 입력 길이를 초과하였습니다.'); return false }

      axios.post('/license-service/setup/email', {
        smtpAddress: smtpAddress,
        smtpPort: smtpPort,
        toAddress: toAddress,
        fromAddress: fromAddress,
        username: username,
        password: password,
        isUsingTls: '1'
      }).then(res => {
        if (res.data.result !== 0) {
          this.showAlertView('E-Mail 정보 저장을 실패하였습니다.')
        } else {
          this.showEmailInfoRegisterView()
          this.initMailAlarmInformation()
        }
      }).catch((err) => {
        alert(err)
      })
    },
    deleteMailInfo: function () {
      axios.delete('/license-service/setup/email').then(res => {
        if (res.data.result !== 0) {
          this.showAlertView('E-Mail 정보 삭제를 실패하였습니다.')
          this.closeEmailInfoDeleteCheckView()
        } else {
          this.closeEmailInfoDeleteCheckView()
          this.showEmailInfoDeleteView()
          this.initMailAlarmInformation()
        }
      }).catch((err) => {
        alert(err)
      })
    },
    checkAlarmCheckBox: function () {
      if (document.getElementById('btn_toggle').checked) {
        document.getElementById('smtpAddress').readOnly = false
        document.getElementById('smtpPort').readOnly = false
        document.getElementById('toAddress').readOnly = false
        document.getElementById('fromAddress').readOnly = false
        document.getElementById('username').readOnly = false
        document.getElementById('password').removeAttribute('disabled')
        document.getElementById('mailInfoSubmit').removeAttribute('disabled')
      } else {
        document.getElementById('smtpAddress').readOnly = true
        document.getElementById('smtpPort').readOnly = true
        document.getElementById('toAddress').readOnly = true
        document.getElementById('fromAddress').readOnly = true
        document.getElementById('username').readOnly = true
        document.getElementById('password').setAttribute('disabled', 'disabled')
        document.getElementById('mailInfoSubmit').setAttribute('disabled', 'disabled')
      }
    },
    showEmailInfoRegisterView: function () {
      this.isEmailInfoRegViewed = true
      this.$modal.show('EmailInfoRegister')
    },
    closeEmailInfoRegisterView: function () {
      this.isEmailInfoRegViewed = true
      this.$modal.hide('EmailInfoRegister')
    },
    showEmailInfoDeleteCheckView: function () {
      this.isEmailInfoDeleteCheckViewd = true
      this.$modal.show('EmailInfoDeleteCheck')
    },
    closeEmailInfoDeleteCheckView: function () {
      this.isEmailInfoDeleteCheckViewd = false
      this.$modal.hide('EmailInfoDeleteCheck')
    },
    showEmailInfoDeleteView: function () {
      this.isEmailInfoDelViewed = true
      this.$modal.show('EmailInfoDelete')
    },
    closeEmailInfoDeleteView: function () {
      this.isEmailInfoDelViewed = false
      this.$modal.hide('EmailInfoDelete')
    },
    loadCertFile: function () {
      if (!this.certFile) {
        this.certFileDesc = '인증서 파일 정보'
        this.countryName = ''
        this.organizationName = ''
        this.organizationalUnitName = ''
        this.validityNotBefore = ''
        this.validityNotAfter = ''

        this.$refs.certInfo.style.display = 'none'
        return
      }

      const certFileName = this.certFile.name
      const certFileExt = certFileName.substr(certFileName.lastIndexOf('.') + 1).toUpperCase()

      if (certFileExt !== 'CRT') {
        this.showAlertView('인증서 파일(.crt)을 선택하여 주시기 바랍니다.')
        this.$refs.certFileInput.reset()
        return
      }

      let reader = new FileReader()
      reader.readAsText(this.certFile, 'UTF-8')

      reader.onload = (event) => {
        const pki = require('node-forge').pki

        try {
          const cert = pki.certificateFromPem(event.target.result)
          this.$refs.certInfo.style.display = 'block'
          this.certFileDesc = '만료일 : ' + cert.validity.notAfter +
            ', 발급 대상 : ' + cert.subject.attributes[3].value

          this.countryName = cert.subject.attributes[3].value
          this.organizationName = cert.subject.attributes[0].value
          this.organizationalUnitName = cert.subject.attributes[1].value
          this.validityNotBefore = cert.validity.notBefore
          this.validityNotAfter = cert.validity.notAfter
        } catch (e) {
          this.showAlertView('PEM format에 맞는 인증서가 아닙니다.')
          this.$refs.certFileInput.reset()
          return
        }
      }
    },
    loadKeyFile: function () {
      if (!this.keyFile) {
        this.keyFileDesc = 'KEY 파일 정보'
        this.keyLength = ''
        return
      }

      const keyFileName = this.keyFile.name
      const keyFileExt = keyFileName.substr(keyFileName.lastIndexOf('.') + 1).toUpperCase()

      if (keyFileExt !== 'KEY') {
        this.showAlertView('KEY 파일(.key)을 선택하여 주시기 바랍니다.')
        this.$refs.keyFileInput.reset()
        return
      }

      let reader = new FileReader()
      reader.readAsText(this.keyFile, 'UTF-8')

      reader.onload = (event) => {
        const pki = require('node-forge').pki

        try {
          const key = pki.privateKeyFromPem(event.target.result)
          const localKeyLength = key.n.bitLength()
          this.keyFileDesc = 'KEY 길이 : ' + localKeyLength + ' bits'
          this.keyLength = localKeyLength
        } catch (e) {
          this.showAlertView('PEM format에 맞는 KEY가 아닙니다.')
          this.$refs.keyFileInput.reset()
          return
        }
      }
    },
    sendSSLFile: function () {
      if (!this.certFile) {
        this.showAlertView('서버에 업로드할 인증서 파일이 없습니다.')
        return
      }

      if (!this.keyFile) {
        this.showAlertView('서버에 업로드할 KEY 파일이 없습니다.')
        return
      }

      const certFileName = this.certFile.name
      const keyFileName = this.keyFile.name

      const certFileExt = certFileName.substr(certFileName.lastIndexOf('.') + 1).toUpperCase()
      const keyFileExt = keyFileName.substr(keyFileName.lastIndexOf('.') + 1).toUpperCase()

      if (certFileExt !== 'CRT' || keyFileExt !== 'KEY') {
        this.showAlertView('인증서 파일(.crt), 키 파일(.key) 2개를 선택하여 주시기 바랍니다.')
        return
      }

      const files = new Array()
      files[0] = this.certFile
      files[1] = this.keyFile

      for (let i = 0; i < files.length; i++) {
        const frm = new FormData()
        frm.append('sslFile', files[i])

        axios.post('/license-service/ssl-file', frm, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(res => {
          if (res.data.result !== 0) {
            this.showAlertView('SSL 파일 업로드를 실패하였습니다.')
            return
          } else {
            this.showSSLFileUploadView()
          }
        }).catch((err) => {
          alert(err)
        })
      }
    },
    showSSLFileUploadView: function () {
      this.isSSLFileUploadViewed = true
      this.$modal.show('SSLFileUpload')
    },
    closeSSLFileUploadView: function () {
      this.isSSLFileUploadViewed = false
      this.$modal.hide('SSLFileUpload')
    },
    showAlertView: function (msg) {
      this.isAlertViewed = true
      this.alertMsg = msg

      this.$modal.show('AlertPopup')
    },
    closeAlertView: function () {
      this.isAlertViewed = false
      this.$modal.hide('AlertPopup')
    }
  }
}
</script>

<style scoped>
@import '../static/css/reset200802.css';
@import '../static/css/controls.css';
@import '../static/css/style.css';
@import '../static/css/style_billing.css';
@import '../static/css/jquery-ui-1.12.1.css';

.certInformation {
  color: #6c727e;
  font-weight: 400;
  white-space: break-spaces;
  letter-spacing: 2px;
  line-height: 1.4;
}
</style>
