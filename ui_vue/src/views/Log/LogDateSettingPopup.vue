<template>
  <div class="log_date_set_modal">
    <div class="log_date_set_modal-card">
      <h2 class="log_date_set_modal-head">{{ title }}</h2>
        <div class="wrap_cont">
            <div class="clearfix">
                <div class="layout-2-left pr10 mt5">
                    <p class="txt_center mb10">{{ beginDateTime }}</p>
                    <date-picker
                        v-model="beginDate"
                        type="date"
                        valueType="format"
                        class="w120"
                    ></date-picker>
                    <vue-timepicker
                      v-model="beginTime"
                      value-zone="local"
                      format="HH:mm:ss"
                      input-width="100"
                      hide-clear-button
                      class="wall mt10"></vue-timepicker>
                </div>
                <div class="layout-2-left pl10 mt5">
                    <p class="txt_center mb10">{{ endDateTime }}</p>
                    <date-picker
                      v-model="endDate"
                      type="date"
                      valueType="format"
                      class="w120"
                    ></date-picker>
                    <vue-timepicker
                      v-model="endTime"
                      value-zone="local"
                      format="HH:mm:ss"
                      input-width="100"
                      hide-clear-button
                      class="wall mt10"></vue-timepicker>
                </div>
            </div>
            <div class="mt50 txt_center">
                <input type="submit" @click="confirm()" value="적용" class="btn_basic btn_cancel bg_bluegreen w80 mr10">
                <input type="button" @click="cancel()" value="닫기" class="btn_cancel btn_basic color_indigo btn_outline w80">
            </div>
        </div>
    </div>
  </div>
</template>

<script>
import VueTimepicker from 'vue2-timepicker'
import 'vue2-timepicker/dist/VueTimepicker.css'

export default {
  components: {
    VueTimepicker
  },
  props: {
    agoDate: { type: String },
    currentDate: { type: String },
    agoTime: { type: String },
    currentTime: { type: String }
  },
  data: function () {
    return {
      title: '시간 설정',
      beginDateTime: '시작일자',
      endDateTime: '종료일자',
      beginDate: this.agoDate,
      endDate: this.currentDate,
      beginTime: this.agoTime,
      endTime: this.currentTime,
      beginDateMenu: false,
      endDateMenu: false
    }
  },
  methods: {
    leadingZeros: function (n, digits) {
      let zero = ''
      n = n.toString()

      if (n.length < digits) {
        for (let i = 0; i < digits - n.length; i++) {
          zero += '0'
        }
      }
      return zero + n
    },
    confirm: function () {
      const setBeginDate = this.beginDate.substring(0, 10)
      const setBeginTime = this.beginTime
      const setEndDate = this.endDate.substring(0, 10)
      const setEndTime = this.endTime

      if (setBeginDate > setEndDate) {
        this.$emit('show-alert-modal', '시간설정 시작일자가 종료일자보다 미래일 수 없습니다.')
      } else if (setBeginDate === setEndDate) {
        if (setBeginTime > setEndTime) {
          this.$emit('show-alert-modal', '시간설정 시작시간이 종료시간보다 미래일 수 없습니다.')
        }
      } else {
        this.$emit('ok', {
          currentDate: setEndDate,
          currentTime: setEndTime,
          agoDate: setBeginDate,
          agoTime: setBeginTime,
          searchCreateTimeBegin: setBeginDate + ' ' + setBeginTime,
          searchCreateTimeEnd: setEndDate + ' ' + setEndTime
        })
      }
    },
    cancel: function () {
      this.$emit('close-modal')
    }
  }
}
</script>

<style scoped>
/* Modal */
.log_date_set_modal {
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
}
.log_date_set_modal-card {
  text-align: center;
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  width: 360px !important;
  height: 280px !important;
  transition: all 0.3s;
  z-index: 999;
  opacity: 1;
  box-shadow: 0px 0px 20px #000;
}
.log_date_set_modal-head {
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
.vue__time-picker >>> input {
  border: 1px solid #cbd3e0;
  color: black;
  font-family: 'Noto Sans KR', sans-serif, "Font Awesome 5 Free";
  width: 120px;
  height: 30px;
  font-size: 14px;
}
</style>
