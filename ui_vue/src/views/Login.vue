<template>
<div class="bg_contents">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>D'Amo Policy Server</title>
    <link rel="icon" href="../static/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="../static/favicon.ico" type="image/x-icon" />
  </head>
  <!-- 좌측 컨트롤 감싸는 영역  -->
  <div class="login_block">
    <div class="login_wrap">
      <!-- 페이지 타이틀  -->
      <section class="mb50">
        <h1>Policy Server <span class="color_indigo">Login</span></h1>
      </section>

      <!-- 로그인 입력 폼 -->
      <section class="login_input">
        <form @submit.prevent="loginSubmit">
          <input type="text" name="id" v-model="id" placeholder="아이디" class="block wall mb10">
          <img src="../static/img/icon_userid.png">
          <input type="password" name="password"
            v-model="password"
            v-on:keypress="checkCapsLock"
            placeholder="비밀번호"
            class="block wall">
          <img src="../static/img/icon_password.png">
          <label style="position: fixed;">{{ capsMessage }}</label>
          <input type="submit" value="LOGIN" class="btn_big bg_bluegreen wall mt20">
        </form>
      </section>

      <!--  팝업 창 : 사용자 추가  -->
      <div class="pw_user_add_complete popup_win w400">
        <h2>사용자 추가</h2>
        <div class="wrap_cont">
          사용자 생성이 완료되었습니다
          <div class="mt20 txt_center">
            <input type="submit" value="닫기" onclick="history.go(0);"
                    class="btn_cancel btn_basic color_indigo btn_outline w80">
          </div>
        </div>
      </div>

      <!--  팝업 창 : 이용 약관  -->
      <modal name="UserAgreementPopup"
        :clickToClose="false"
        @closed="closeUserAgreementView()"
        :width="650"
        :height="600">
        <UserAgreementPopup
          v-if="isUserAgreementViewed"
          :firstLogin="firstLogin"
          @close-modal="closeUserAgreementView()">
        </UserAgreementPopup>
      </modal>

      <!--  Copyright -->
      <section>
        <img src="../static/img/ci_logo.png"> ⓒ 2004 Penta Security Systems Inc. All rights reserved.
        <a id="userAgreement" @click="showUserAgreementView()" class="btn_userAgreement" style="cursor: pointer;">[이용약관]</a>
      </section>
    </div>
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
import VueCookies from 'vue-cookies'
import UserAgreementPopup from './Login/UserAgreementPopup.vue'
import AlertPopup from './Component/AlertPopup.vue'

export default {
  components: {
    UserAgreementPopup,
    AlertPopup
  },
  data () {
    return {
      id: '',
      password: '',
      msg: '',
      isUserAgreementViewed: false,
      capsMessage: '',
      firstLogin: false,
      isAlertViewed: false,
      alertMsg: ''
    }
  },
  methods: {
    checkInputValue: function () {
      if (!this.id) {
        this.showAlertView('ID를 입력 해주시기 바랍니다.')
        return false
      }

      if (!this.password) {
        this.showAlertView('비밀번호를 입력 해주시기 바랍니다.')
        return false
      }

      if (this.id.length > 64) {
        this.showAlertView('ID 최대 입력 길이를 초과하였습니다.')
        return false
      }

      if (this.password.length > 64) {
        this.showAlertView('비밀번호 최대 입력 길이를 초과하였습니다.')
        return false
      }
    },
    loginSubmit: function () {
      if (this.checkInputValue() === false) {
        return
      }

      VueCookies.set('ID', this.id, '1h')

      return axios.post('/license-service/login', { id: this.id, password: this.password }).then(
        res => {
          if (res.data.result !== 0 && res.data.result !== 100) {
            this.showAlertView('ID 혹은 비밀번호가 일치하지 않습니다.')
            return
          } else if (res.data.result === 100) {
            this.firstLogin = true
            this.showUserAgreementView()

            VueCookies.set('PENTA-AUTH-TOKEN', res.headers['penta-auth-token'], '1h')
          } else {
            VueCookies.set('PENTA-AUTH-TOKEN', res.headers['penta-auth-token'], '1h')
            return this.$router.push({ name: 'Home' })
          }
        }
      ).catch((err) => {
        alert(err)
        this.$router.push({ name: 'Login' })
      })
    },
    checkCapsLock: function (event) {
      if ((navigator.appName === 'Netscape' &&
        navigator.userAgent.search('Trident') !== -1) ||
        (navigator.userAgent.toLowerCase().indexOf('msie') !== -1)) {
        return
      }

      const keyCode = event.keyCode
      const shiftKey = event.shiftKey
      if (((keyCode >= 65 && keyCode <= 90) && !shiftKey) ||
        ((keyCode >= 97 && keyCode <= 122) && shiftKey)) {
        this.capsMessage = '<Caps Lock> 이 켜져 있습니다.'
      } else {
        this.capsMessage = ''
      }
    },
    showUserAgreementView: function () {
      this.isUserAgreementViewed = true
      this.$modal.show('UserAgreementPopup')
    },
    closeUserAgreementView: function () {
      this.isUserAgreementViewed = false
      this.$modal.hide('UserAgreementPopup')
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

<style>
@import '../static/css/reset200802.css';
@import '../static/css/controls.css';
@import '../static/css/style.css';
@import '../static/css/style_billing.css';

html, body {
  background-color: #6c727e;
}
</style>
