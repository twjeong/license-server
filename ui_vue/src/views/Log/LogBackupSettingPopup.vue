<template>
  <div class="log_backup_modal">
    <div class="log_backup_modal-card">
    <h2 class="log_backup_modal-head">{{ title }}</h2>
    <div class="section_title">
      <h3 class="inline_block mt10 ml10">설정 정보</h3>
      <p class="inline_block mt10 ml10">백업 주기는 설정된 월 1일 00시에 진행됩니다.</p>
    </div>

    <div class="section_contents mt20">
      <div>
        <label class="w160 ml10">로그 백업 설정</label>
        <div class="btn_switch">
          <label class="switch">
            <input type="checkbox" v-model="btn_toggle" @change="check()">
            <span class="slider round"></span>
          </label>

          <p class="toggle_txt ml5" id="toggle_off" style="display: none">OFF</p>
          <p class="toggle_txt ml5" id="toggle_on" style="display: inline-block">ON</p>
        </div>
      </div>

      <div class="mt20 txt_left">
        <label class="w160 ml10">로그 백업 주기 설정</label>
        <div class="select_menu w100">
          <select v-model="logBackupOption">
            <option value="oneMonth">1개월</option>
            <option value="threeMonth">3개월</option>
            <option value="sixMonth">6개월</option>
          </select>
        </div>
      </div>

      <div class="mt20">
        <label class="w160 ml10">현재 설정된 로그 백업 주기</label>
        <input type="text"
          v-model="logBackupCycle"
          placeholder="(미설정)"
          style="background-color: #ecf0f1;"
          class="w500" readonly>
      </div>
      <div class="mt20">
        <label class="w160 ml10">로그 백업 파일 경로</label>
        <input type="text"
          v-model="logBackupDirectory"
          placeholder="로그 백업 파일 경로"
          style="background-color: #ecf0f1;"
          class="w500" readonly>
      </div>
    </div>

    <modal name="LogBackupIntervalPopup"
    :draggable="true"
    @closed="closeLogBackupIntervalView()"
    :width="300"
    :height="150">
    <LogBackupIntervalPopup
      v-if="isLogBackupIntervalViewed"
      :btn_toggle="btn_toggle"
      :logBackupOption="logBackupOption"
      @show-alert-modal="showAlertChildPopup"
      @close-modal="closeLogBackupIntervalView()">
    </LogBackupIntervalPopup>
    </modal>

    <div class="wrap_cont">
      <div class="mb15 txt_center">
        <input type="button" value="적용" @click="confirm()" class="btn_basic btn_cancel bg_bluegreen w80 mr10">
        <input type="button" value="닫기" @click="cancel()" class="btn_cancel btn_basic color_indigo btn_outline w80">
      </div>
    </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import LogBackupIntervalPopup from './LogBackupIntervalPopup.vue'

export default {
  components: {
    LogBackupIntervalPopup
  },
  data: function () {
    return {
      title: '로그 백업 설정',
      btn_toggle: false,
      logBackupCycle: null,
      logBackupDirectory: null,
      isLogBackupIntervalViewed: false,
      logBackupOption: localStorage.getItem('logBackupOption')
    }
  },
  created () {
    this.getLogBackupOption()
    this.logBackupOption = 'oneMonth'
  },
  watch: {
    logBackupOption(value) {
      this.logBackupOption = value
      localStorage.setItem('logBackupOption', value)
    }
  },
  methods: {
    check: function () {
      if (this.btn_toggle) {
        document.getElementById('toggle_off').style.display = 'none'
        document.getElementById('toggle_on').style.display = 'inline-block'
      } else {
        document.getElementById('toggle_off').style.display = 'inline-block'
        document.getElementById('toggle_on').style.display = 'none'
      }
    },
    getLogBackupOption: function () {
      axios.get('/license-service/logs-backup').then(
        res => {
          if (res.data.enable) {
            this.btn_toggle = true
            document.getElementById('toggle_off').style.display = 'none'
            document.getElementById('toggle_on').style.display = 'inline-block'
          } else {
            this.btn_toggle = false
            document.getElementById('toggle_off').style.display = 'inline-block'
            document.getElementById('toggle_on').style.display = 'none'
          }

          const monthList = res.data.month.split(',')
          if (monthList.length === 12) {
            this.logBackupCycle = '매월(1월, 2월, 3월, 4월, 5월, 6월, 7월, 8월, 9월, 10월, 11월, 12월)'
          } else if (monthList.length === 4) {
            this.logBackupCycle = '3개월 마다(3월, 6월, 9월, 12월)'
          } else if (monthList.length === 2) {
            this.logBackupCycle = '6개월 마다(6월, 12월)'
          } else {
            this.logBackupCycle = '설정 없음'
          }

          this.logBackupDirectory = res.data.path.replace(/"/gi, '')
        })
    },
    closeLogBackupIntervalView: function () {
      this.getLogBackupOption()

      this.isLogBackupIntervalViewed = false
      this.$modal.hide('LogBackupIntervalPopup')
    },
    confirm: function () {
      this.isLogBackupIntervalViewed = true
      this.$modal.show('LogBackupIntervalPopup')
    },
    cancel: function () {
      this.$emit('close-modal')
    },
    showAlertChildPopup: function (msg) {
      this.$emit('show-alert-modal', msg)
    }
  }
}
</script>

<style scoped>
.log_backup_modal {
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
}
.log_backup_modal-card {
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  width: 700px !important;
  height: 340px !important;
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
}
.log_backup_modal-head {
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
