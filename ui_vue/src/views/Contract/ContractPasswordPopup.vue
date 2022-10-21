<template>
  <div class="con_password_modal">
    <div class="con_password_modal-card">
      <h2 class="con_password_modal-head">{{ title }}</h2>
      <slot />
      <div class="wrap_cont">
        <input type="password" style="width: 200px;" ref="input_contract_file_password" @keyup.enter="confirm()">

        <div class="mt10 mb10 txt_center">
          <input type="button" value="확인" @click="confirm()" class="btn_cancel btn_basic bg_bluegreen w80 mr10">
          <input type="button" value="취소" @click="cancel()" class="btn_cancel btn_basic color_indigo btn_outline w80">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import sha256 from 'js-sha256'

export default {
  props: {
    encContractFile: {
      type: String
    },
    fileName: {
      type: String
    }
  },
  data: function () {
    return {
      title: '파일 비밀번호 입력',
      contractList: []
    }
  },
  mounted () {
    this.focusInput()
  },
  methods: {
    focusInput: function () {
      this.$refs.input_contract_file_password.focus()
    },
    checkFile: function (input) {
      const SHA256 = sha256.create()
      const hashedPassword = SHA256.update(input).toString()

      let plainContractFile = null
      try {
        plainContractFile = this.CryptoJS.AES.decrypt(this.encContractFile,
          hashedPassword,
          {
            keySize: 256 / 8,
            iv: hashedPassword,
            mode: this.CryptoJS.mode.CBC,
            padding: this.CryptoJS.pad.Pkcs7
          }).toString(this.CryptoJS.enc.Utf8)

          if (plainContractFile.length === 0) {
            this.$emit('show-alert-modal', '비밀번호가 일치하지 않거나, eci 파일이 아닙니다.')
            return false
          }
      } catch (e) {
        this.$emit('show-alert-modal', '비밀번호가 일치하지 않거나, eci 파일이 아닙니다.')
        
        return false
      }

      const contractFile = JSON.parse(plainContractFile)
      const contractFileName = this.fileName
      contractFile.map(function (object) {
        object.contract_file_name = contractFileName.replace(/\.[^/.]+$/, '')
      })

      this.contractList = contractFile
    },
    confirm: function () {
      const input = this.$refs.input_contract_file_password.value
      const result = this.checkFile(input)
      if (result === false) {
        return
      }

      this.$emit('ok', this.contractList)
    },
    cancel: function () {
      this.$emit('close-modal')
    }
  }
}
</script>

<style scoped>
/* Modal */
.con_password_modal {
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
}
.con_password_modal-card {
  text-align: center;
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  width: 250px !important;
  height: 150px !important;
  transition: all 0.3s;
  z-index: 999;
  opacity: 1;
}
.con_password_modal-head {
  margin-top: 0;
  font-size: 1.5rem;
  font-weight: 400;
  height: 4rem;
  line-height: 4rem;
  padding: 0 2rem;
  border-radius: 0.2rem 0.2rem 0 0;
  color: #fff;
  background-color: #1a2031;
}
.wrap_cont {
  width: 100%;
  padding: 2rem;
  overflow: visible;
  height: 100px;
}
</style>
