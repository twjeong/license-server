<template>
  <div class="log_backup_interval_modal">
    <div class="log_backup_interval_modal-card">
      <h2 class="log_backup_interval_modal-head">{{ title }}</h2>
      <slot />
      <div class="wrap_cont">
        {{ msg }}
        <div class="mt20 mb20 txt_center">
          <input type="button" value="확인" @click="confirm()" class="btn_cancel btn_basic bg_bluegreen w80 mr10">
          <input type="button" value="취소" @click="cancel()" class="btn_cancel btn_basic color_indigo btn_outline w80">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  props: {
    btn_toggle: {
      type: Boolean
    },
    logBackupOption: {
      type: String
    }
  },
  data: function () {
    return {
      title: '주기적 로그 백업',
      msg: '설정 주기에 백업이 이루어질 경우 이전 로그들은 삭제됩니다. 백업 설정을 진행하시겠습니까?'
    }
  },
  methods: {
    confirm: function () {
      let strMonth = null
      if (this.logBackupOption === 'oneMonth') {
        strMonth = '1,2,3,4,5,6,7,8,9,10,11,12'
      } else if (this.logBackupOption === 'threeMonth') {
        strMonth = '3,6,9,12'
      } else {
        strMonth = '6,12'
      }

      return axios.put('/license-service/logs-backup', {
        minute: 0,
        hour: 0,
        day: 1,
        enable: this.btn_toggle,
        month: strMonth,
        force: false
      }).then(
        res => {
          if (res.data.result !== 0) {
            this.$emit('show-alert-modal', '로그 백업 설정을 실패하였습니다.')
            return
          }

          this.$emit('close-modal')
        }
      ).catch((err) => {
        alert(err)
      })
    },
    cancel: function () {
      this.$emit('close-modal')
    }
  }
}
</script>

<style scoped>
/* Modal */
.log_backup_interval_modal {
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
}
.log_backup_interval_modal-card {
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  width: 300px !important;
  height: 150px !important;
  transition: all 0.3s;
  z-index: 999;
  opacity: 1;
}
.log_backup_interval_modal-head {
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
