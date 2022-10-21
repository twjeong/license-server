<template>
<div class="bg_contents">
  <div class="container">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>D'Amo Policy Server</title>
    <link rel="icon" href="../static/favicon.ico">
  </head>
  <!-- 페이지 타이틀  -->
  <section class="pagetitle clearfix">
      <div class="f_left">
          <h2 class="inline_block mr10">계정 설정</h2>
          <p class="inline_block">사용자 설정을 변경할 수 있습니다.</p>
      </div>
  </section>

  <!-- 사용자 비밀번호 변경  -->
  <section class="section_block w1000">
    <form @submit.prevent="changePassword">
      <div class="section_title">
        <h3 class="inline_block mr20">사용자 비밀번호 변경</h3>
        <p class="inline_block">사용자 비밀번호를 변경할 수 있습니다.</p>
      </div>
      <div class="section_contents">
        <!-- 현재 비밀번호 -->
        <div class="mt20">
            <label class="w150">현재 비밀번호</label>
            <input type="password" v-model="currentPassword" name="currentPassword" class="w500">
        </div>

        <!-- 새 비밀번호 -->
        <div class="mt20">
            <label class="w150">새 비밀번호</label>
            <input type="password" v-model="newPassword" name="newPassword" placeholder="새 비밀번호를 입력해 주세요." class="w500">
        </div>

        <!-- 새 비밀번호 확인 -->
        <div class="mt20">
            <label class="w150">새 비밀번호 확인</label>
            <input type="password" v-model="reNewPassword" name="reNewPassword" placeholder="새 비밀번호를 다시 입력해 주세요." class="w500">
        </div>

        <!-- 버튼 영역 -->
        <div class="txt_center mt30">
            <input type="submit" value="변경" class="btn_basic bg_bluegreen w100">
            <input type="button" value="뒤로 가기" onclick="history.go(-1);"
                    class="btn_cancel btn_basic color_indigo btn_outline w100">
        </div>
      </div>

      <!--  팝업 창 : 비밀번호 변경 알림  -->
      <PwdPopup v-if="isPwdViewed" @close-modal="isPwdViewed = false">
      </PwdPopup>
    </form>
  </section>
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
import { defineComponent } from '@vue/composition-api'
import axios from 'axios'
import VueCookies from 'vue-cookies'
import PwdPopup from './Setting/PwdPopup.vue'
import AlertPopup from './Component/AlertPopup.vue'

export default defineComponent({
  components: {
    PwdPopup,
    AlertPopup
  },
  data: function () {
    return {
      title: '비밀번호 변경',
      msg: '비밀번호가 변경되었습니다.',
      currentPassword: '',
      newPassword: '',
      reNewPassword: '',
      isPwdViewed: false,
      isAlertViewed: false
    }
  },
  methods: {
    changePassword: function () {
      /* eslint-disable no-useless-escape */
      const regPassword = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]).{9,32}$/
      if (regPassword.test(this.newPassword) === false) {
        this.showAlertView('비밀번호는 9자 이상, 32자 이하로 입력해야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.')
        return false
      }

      if (regPassword.test(this.reNewPassword === false)) {
        this.showAlertView('비밀번호는 9자 이상, 32자 이하로 입력해야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.')
        return false
      }

      return axios.post('/license-service/password', {
        id: VueCookies.get('ID'),
        currentPassword: this.currentPassword,
        newPassword: this.newPassword,
        reNewPassword: this.reNewPassword
      }).then(
        res => {
          if (res.data.result === 0) {
            this.isPwdViewed = true
          } else {
            this.showAlertView(res.data.msg)
            return false
          }
        }
      ).catch((err) => {
        alert(err)
        this.$router.push({ name: 'Login' })
      })
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
})
</script>

<style scoped>
@import '../static/css/reset200802.css';
@import '../static/css/controls.css';
@import '../static/css/style.css';
@import '../static/css/style_billing.css';

input {
  margin-right: 5px;
}
</style>
