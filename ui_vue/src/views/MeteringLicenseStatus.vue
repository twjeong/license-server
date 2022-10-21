<template>
<div class="bg_contents">
  <div class="container">
      <!-- 페이지 타이틀  -->
      <section class="pagetitle clearfix">
        <div class="f_left">
          <h2 class="inline_block mr10">종량제 라이선스 사용량 현황</h2>
          <p class="inline_block">종량제 라이선스 사용량 및 빌링 보고서를 확인할 수 있습니다.</p>
        </div>
        <div class="section_title">
          <a @click="buildDateRangeSlider()"><img src="../static/img/icon_refresh.png"     class="icon_refresh mt7" style="width:13px"></a>
        </div>
        <div class="f_right txt_right mr10">
          <p class="inline_block">Home / <span>종량제 라이선스 사용량 현황</span></p>
        </div>
      </section>

      <!-- 종량제 라이선스 사용량 현황  -->
      <section class="section_block">
        <div class="section_contents">
          <!-- 전체 컨텐츠 영역 -->
          <div class="clearfix">

            <!-- 사용량 리스트 : 좌측 -->
            <div class="list_left f_left">
              <div id="cloudUsageTime" class="h600">
                <p class="list_title">고객사명</p>
                <div class="list_menu">
                  <ul>
                    <li v-for="group, index in monitorUsageTimeGroupInfo"
                    :key="group.custName">
                      <a href="#" @click="buildProductCustNameGroupInfoTable(group.custName)"
                        class="on">{{ group.custName }}</a>
                      <span
                        v-bind:style="slideFlag[index] === true ? 'transform: rotateZ(-180deg);' : 'transform: rotateZ(0);'"
                        class="tri_arrow">
                        <a @click="toggleCustomerName(index)">&#9660;</a>
                      </span>
                      <ul>
                        <SlideUpDown :active="slideFlag[index]">
                        <li v-for="item in group.serialNo"
                        :key="item">
                          <a href="#" @click="buildProductDetailInfoTable(item)">
                            {{ item }}</a>
                          </li>
                        </SlideUpDown>
                      </ul>
                    </li>
                  </ul>
                </div>
              </div>
            </div>

            <!-- 기간 제품정보 사용시간 영역 : 우측 -->
            <div class="list_right f_right">
              <h3 class="subtitle mb10">사용 시간</h3>
              <div class="range_slider clearfix mt10 mb20">
                <label>기간 설정 : </label>
                <input type="text" class="minVal w120 ml5" ref="minVal" readonly>
                ~
                <input type="text" class="maxVal w120" ref="maxVal" readonly>
                <div class="mt20">
                  <vue-slider v-model="sliderValue" v-bind="options"
                    v-on:change="updateSlider" :tooltip-formatter="Tformatter"/>
                </div>
              </div>

              <!-- 납품계약 및 제품 정보 상세 -->
              <h3 class="subtitle mb10">납품계약 및 제품 정보 상세</h3>
              <table class="table_vhead mb20">
                <tbody id="productDetailInfoTable">
                </tbody>
              </table>

              <h3 class="subtitle mb10">사용 시간 그래프</h3>
              <!--  라인 그래프 -->
              <div class="line_graph pb20">
                <canvas id="lineChart1"></canvas>
              </div>
            </div>
          </div>

          <div class="txt_center mt30">
            <input type="submit"
              @click="showMeteringUsageTimeView()"
              value="종량제 빌링 보고서"
              class="btn_report btn_basic bg_bluegreen w140">
          </div>

          <modal name="MeteringUsageTimePopup"
            :draggable="true"
            @closed="closeMeteringUsageTimeView()"
            :width="640"
            :height="320">
            <MeteringUsageTimePopup
              v-if="isMeteringUsageTimeViewed"
              :custNm="custNm"
              @ok="showMeteringReportView($event)"
              @close-modal="closeMeteringUsageTimeView()">
            </MeteringUsageTimePopup>
          </modal>

          <modal name="MeteringReportPopup"
            :draggable="true"
            @closed="closeMeteringReportView()"
            :width="850"
            :height="700">
            <MeteringReportPopup
              v-if="isMeteringReportViewed"
              :custNm="custNm"
              :selectedYear="this.selectedYear"
              :selectedMonth="this.selectedMonth"
              :useMonthStartDate="this.useMonthStartDate"
              :useMonthEndDate="this.useMonthEndDate"
              :usedYear="this.usedYear"
              :usedMonth="this.usedMonth"
              @show-alert-modal="showAlertView"
              @close-modal="closeMeteringReportView()">
            </MeteringReportPopup>
          </modal>
        </div>
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
import axios from 'axios'
import $ from 'jquery'
import moment from 'moment'
import { Bar } from 'vue-chartjs'
import SlideUpDown from 'vue-slide-up-down'
import 'vue-slider-component/theme/default.css'
import VueSlider from 'vue-slider-component'
import MeteringUsageTimePopup from './Metering/MeteringUsageTime'
import MeteringReportPopup from './Metering/MeteringUsageTimeReport'
import AlertPopup from './Component/AlertPopup.vue'

export default {
  extends: Bar,
  components: {
    VueSlider,
    MeteringUsageTimePopup,
    MeteringReportPopup,
    SlideUpDown,
    AlertPopup
  },
  data () {
    return {
      monitorUsageTimeGroupInfo: [],
      usageTimeType: 'custNm',
      custNm: '',
      serialNo: '',
      usageTimeChart: null,
      sliderValue: [],
      slideFlag: [],
      isMeteringUsageTimeViewed: false,
      isMeteringReportViewed: false,
      isAlertViewed: false,
      selectedYear: '',
      selectedMonth: '',
      useMonthStartDate: '',
      useMonthEndDate: '',
      usedYear: '',
      usedMonth: '',
      options: {
        dotSize: 14,
        width: 'auto',
        height: 10,
        min: 946728000000,
        max: 9111473600000
      },
      Tformatter: val => {
        return this.changeDateFormat(this.formatNoUiDate(new Date(val)))
      }
    }
  },
  mounted () {
    this.buildDateRangeSlider()
  },
  methods: {
    toggleCustomerName: function (index) {
      this.slideFlag = this.slideFlag.map((el, i) =>
        i === index ? !el : el
      )
    },
    buildDateRangeSlider: function () {
      const sliderDate = new Date()
      const sliderMinDays = new Date(sliderDate.setFullYear(sliderDate.getFullYear() - 1))

      const sliderMinYear = sliderMinDays.getFullYear()
      const sliderMinMonth = sliderMinDays.getMonth() + 1
      const sliderMinDate = sliderMinDays.getDate()

      // Default day is today.
      const defaultToday = new Date()
      const defaultYear = defaultToday.getFullYear()
      let defaultMonth = defaultToday.getMonth() + 1
      let defaultDate = defaultToday.getDate()

      const defaultMinDays = new Date(defaultToday.setMonth(defaultToday.getMonth() - 3))
      const defaultMinYear = defaultMinDays.getFullYear()
      let defaultMinMonth = defaultMinDays.getMonth() + 1
      let defaultMinDate = defaultMinDays.getDate()

      if (defaultMinMonth < 10) defaultMinMonth = '0' + defaultMinMonth
      if (defaultMinDate < 10) defaultMinDate = '0' + defaultMinDate
      if (defaultMonth < 10) defaultMonth = '0' + defaultMonth
      if (defaultDate < 10) defaultDate = '0' + defaultDate

      const minValYMD = defaultMinYear + '' + defaultMinMonth + '' + defaultMinDate
      const maxValYMD = defaultYear + '' + defaultMonth + '' + defaultDate
      $('.minVal').val(this.changeDateFormat(minValYMD))
      $('.maxVal').val(this.changeDateFormat(maxValYMD))

      this.buildCloudUsageTimeInfo()

      this.sliderValue = [this.timestamp(new Date(defaultMinYear, defaultMinMonth - 1, defaultMinDate)),
        this.timestamp(new Date(defaultYear, defaultMonth - 1, defaultDate))]

      this.options = {
        dotSize: 14,
        width: 'auto',
        height: 10,
        contained: false,
        direction: 'ltr',
        min: this.timestamp(new Date(sliderMinYear, sliderMinMonth - 1, sliderMinDate)),
        max: this.timestamp(new Date(defaultYear, defaultMonth - 1, defaultDate)),
        interval: 24 * 60 * 60 * 1000,
        tooltip: 'active',
        tooltipPlacement: 'top',
        useKeyboard: true,
        keydownHook: null,
        dragOnClick: true,
        enableCross: true,
        fixed: false,
        minRange: 24 * 60 * 60 * 1000,
        lazy: true
      }

      if (this.usageTimeType === 'serialNo') {
        this.buildProductDetailInfoTable(this.serialNo)
      } else {
        this.buildProductCustNameGroupInfoTable(this.custNm)
      }
      this.drawUsageTimeInfo()
    },
    drawUsageTimeInfo: function () {
      if (this.usageTimeType === 'custNm') {
        axios.get('/license-service/usage-time/monitor/daily-custnm-summary-info', {
          params: {
            custnm: this.custNm,
            'start-date': this.formatDate(this.$refs.minVal.value, ''),
            'end-date': this.formatDate(this.$refs.maxVal.value, '')
          }
        }).then(
          res => {
            const minDate = this.formatDate($('.minVal').val(), '-')
            const maxDate = this.formatDate($('.maxVal').val(), '-')

            const dateArray = this.getDates(minDate, maxDate)
            const usageTimeArray = []

            for (let i = 0; i < dateArray.length; i++) {
              let flagMatchDate = false

              for (let j = 0; j < res.data.monitorUsageTimeDailyInfoDtos.length; j++) {
                if (dateArray[i] === res.data.monitorUsageTimeDailyInfoDtos[j].date) {
                  usageTimeArray.push(res.data.monitorUsageTimeDailyInfoDtos[j].minuteOfUsageTime)
                  flagMatchDate = true

                  res.data.monitorUsageTimeDailyInfoDtos.splice(j, 1)
                  break
                }
              }

              if (flagMatchDate === false) {
                usageTimeArray.push(0)
              }

              flagMatchDate = false
            }

            const data1 = [{
              label: '사용시간(분)',
              data: usageTimeArray,
              backgroundColor: 'rgba(235, 69, 122, 1)',
              hoverBackgroundColor: 'rgba(235, 69, 122, 1)'
            }]

            const ctx = document.getElementById('lineChart1')
            ctx.height = 250

            if (this.usageTimeChart !== null) {
              this.usageTimeChart.destroy()
            }
            this.usageTimeChart = new Chart(ctx, {
              type: 'bar',
              labels: dateArray,
              data: {
                labels: dateArray,
                datasets: data1
              },
              options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                  yAxes: [{
                    ticks: {
                      beginAtZero: true
                    }
                  }]
                }
              }
            })
          })
      } else {
        axios.get('/license-service/usage-time/monitor/daily-serialno-summary-info', {
          params: {
            serialno: this.serialNo,
            'start-date': this.formatDate(this.$refs.minVal.value, ''),
            'end-date': this.formatDate(this.$refs.maxVal.value, '')
          }
        }).then(
          res => {
            const minDate = this.formatDate(this.$refs.minVal.value, '-')
            const maxDate = this.formatDate(this.$refs.maxVal.value, '-')

            const dateArray = this.getDates(minDate, maxDate)
            const usageTimeArray = []

            for (let i = 0; i < dateArray.length; i++) {
              let flagMatchDate = false

              for (let j = 0; j < res.data.monitorUsageTimeDailyInfoDtos.length; j++) {
                if (dateArray[i] === res.data.monitorUsageTimeDailyInfoDtos[j].date) {
                  usageTimeArray.push(res.data.monitorUsageTimeDailyInfoDtos[j].minuteOfUsageTime)
                  flagMatchDate = true

                  res.data.monitorUsageTimeDailyInfoDtos.splice(j, 1)
                  break
                }
              }

              if (flagMatchDate === false) {
                usageTimeArray.push(0)
              }

              flagMatchDate = false
            }

            const data1 = [{
              label: '사용시간(분)',
              data: usageTimeArray,
              backgroundColor: 'rgba(69, 138, 235, 1)',
              hoverBackgroundColor: 'rgba(69, 138, 235, 1)'
            }]

            const ctx = document.getElementById('lineChart1')
            ctx.height = 250

            if (this.usageTimeChart !== null) {
              this.usageTimeChart.destroy()
            }
            this.usageTimeChart = new Chart(ctx, {
              type: 'bar',
              labels: dateArray,
              data: {
                labels: dateArray,
                datasets: data1
              },
              options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                  yAxes: [{
                    ticks: {
                      beginAtZero: true
                    }
                  }]
                }
              }
            })
          })
      }
    },
    formatNoUiDate: function (dateNotFormatted) {
      const localDate = dateNotFormatted

      const year = localDate.getFullYear()
      let month = localDate.getMonth() + 1
      let date = localDate.getDate()

      if (month < 10) month = '0' + month
      if (date < 10) date = '0' + date

      return year + '' + month + '' + date
    },
    buildCloudUsageTimeInfo: function () {
      axios.get('/license-service/usage-time/monitor/group-info', {
        params: {
          'current-page-no': 1, 'records-per-page': 65535
        }
      }).then(
        res => {
          let strHtml = '<p class="list_title">고객사명</p>'
          strHtml = strHtml.concat('<div class="list_menu">')

          this.usageTimeType = 'custNm'
          this.monitorUsageTimeGroupInfo = res.data.usageTimeInfoGroupList

          for (let i = 0; i < res.data.usageTimeInfoGroupList.length; i++) {
            this.slideFlag[i] = true

            strHtml = strHtml.concat('<ul>')
            strHtml = strHtml.concat('<li>')

            if (i === 0) {
              this.custNm = res.data.usageTimeInfoGroupList[i].custName

              strHtml = strHtml.concat(
                '<a href="#" @click="buildProductCustNameGroupInfoTable(\'' +
                res.data.usageTimeInfoGroupList[i].custName + '\')" class="on">' +
                res.data.usageTimeInfoGroupList[i].custName +
                '</a><span class="tri_arrow">&#9660;</span>')

              this.buildProductCustNameGroupInfoTable(res.data.usageTimeInfoGroupList[i].custName)
            } else {
              strHtml = strHtml.concat(
                '<a href="#" @click="buildProductCustNameGroupInfoTable(\'' +
                res.data.usageTimeInfoGroupList[i].custName + '\')">' +
                res.data.usageTimeInfoGroupList[i].custName + '</a>' +
                '<span class="tri_arrow">&#9660;</span>')
            }

            strHtml = strHtml.concat('<ul>')
            for (let j = 0; j < res.data.usageTimeInfoGroupList[i].serialNo.length; j++) {
              strHtml = strHtml.concat('<li><a href="#" @click="buildProductDetailInfoTable(\'' +
                  res.data.usageTimeInfoGroupList[i].serialNo[j] + '\')">' +
                  res.data.usageTimeInfoGroupList[i].serialNo[j] + '</a></li>')
            }
            strHtml = strHtml.concat('</ul>')

            strHtml = strHtml.concat('</li>')
            strHtml = strHtml.concat('</ul>')
          }

          strHtml = strHtml.concat('</div>')
        })
    },
    updateSlider: function (val) {
      $('.minVal').val(this.changeDateFormat(this.formatNoUiDate(new Date(val[0]))))
      $('.maxVal').val(this.changeDateFormat(this.formatNoUiDate(new Date(val[1]))))

      if (this.usageTimeType === 'serialNo') {
        this.buildProductDetailInfoTable(this.serialNo)
      } else {
        this.buildProductCustNameGroupInfoTable(this.custNm)
      }
      this.drawUsageTimeInfo()
    },
    timestamp: function (str) {
      return new Date(str).getTime()
    },
    formatDate: function (val, type) {
      if (val === null || val.length === 0) {
        return
      }

      const days = val.split(' ')
      const year = days[0].replace(/년/gi, '')
      let month = days[1].replace(/월/gi, '')
      if (month.length < 2) {
        month = '0' + month
      }
      let date = days[2].replace(/일/gi, '')
      if (date.length < 2) {
        date = '0' + date
      }

      if (type === '-') {
        return year + '-' + month + '-' + date
      } else {
        return year + '' + month + '' + date
      }
    },
    getDates: function (startDate, stopDate) {
      const dateArray = []
      let currentDate = moment(startDate)
      const localStopDate = moment(stopDate)
      while (currentDate <= localStopDate) {
        dateArray.push(moment(currentDate).format('YYYY-MM-DD'))
        currentDate = moment(currentDate).add(1, 'days')
      }
      return dateArray
    },
    buildProductCustNameGroupInfoTable: function (custNm) {
      this.usageTimeType = 'custNm'
      this.custNm = custNm

      axios.get('/license-service/usage-time/monitor/daily-custnm-summary-info', {
        params: {
          custnm: custNm,
          'start-date': this.formatDate(this.$refs.minVal.value, ''),
          'end-date': this.formatDate(this.$refs.maxVal.value, '')
        }
      }).then(
        res => {
          document.getElementById('productDetailInfoTable').innerHTML = ''

          let strHtml = '<tr><th class="w150">고객사명</th>'
          strHtml = strHtml.concat('<td>' + custNm + '</td></tr>')

          strHtml = strHtml.concat('<tr><th>기간</th>')
          strHtml = strHtml.concat('<td>' + $('.minVal').val() + ' ~ ' + $('.maxVal').val() + '</td></tr>')

          let totalUsageTimeInfo = 0
          for (let i = 0; i < res.data.monitorUsageTimeDailyInfoDtos.length; i++) {
            totalUsageTimeInfo = totalUsageTimeInfo +
                res.data.monitorUsageTimeDailyInfoDtos[i].minuteOfUsageTime
          }

          strHtml = strHtml.concat('<tr><th>총 사용 시간(분)</th>')
          strHtml = strHtml.concat('<td>' + totalUsageTimeInfo + ' 분</td></tr>')

          $('#productDetailInfoTable').append(strHtml)
          this.drawUsageTimeInfo()
        })
    },
    buildProductDetailInfoTable: function (serialNo) {
      this.serialNo = serialNo
      this.usageTimeType = 'serialNo'

      let productDetailInfo = null
      axios.get('/license-service/specific-licenses', {
        params: {
          'current-page-no': 1,
          'records-per-page': 65535,
          'serial-no': serialNo
        }
      }).then(
        res => {
          productDetailInfo = res.data

          let strHtml = '<tr><th class="w150">납품 계약 번호 / 제품명</th>'
          if (productDetailInfo.licenseList.length === 0) {
            strHtml = strHtml.concat('<td></td></tr>')
          } else {
            strHtml = strHtml.concat('<td>' + productDetailInfo.licenseList[0].orderNum + ' / ' +
              productDetailInfo.licenseList[0].componentName + '</td></tr>')

            this.custNm = productDetailInfo.licenseList[0].custNm
          }

          strHtml = strHtml.concat('<tr><th>기간</th>')
          strHtml = strHtml.concat('<td>' + $('.minVal').val() + ' ~ ' + $('.maxVal').val() + '</td></tr>')

          let productUsageTimeInfo = null
          axios.get('/license-service/usage-time/monitor/daily-serialno-summary-info', {
            params: {
              serialno: serialNo,
              'start-date': this.formatDate(this.$refs.minVal.value, ''),
              'end-date': this.formatDate(this.$refs.maxVal.value, '')
            }
          }).then(
            response => {
              document.getElementById('productDetailInfoTable').innerHTML = ''
              productUsageTimeInfo = response.data

              let totalUsageTimeInfo = 0
              for (let i = 0; i < productUsageTimeInfo.monitorUsageTimeDailyInfoDtos.length; i++) {
                totalUsageTimeInfo = totalUsageTimeInfo +
                    productUsageTimeInfo.monitorUsageTimeDailyInfoDtos[i].minuteOfUsageTime
              }

              strHtml = strHtml.concat('<tr><th>총 사용 시간(분)</th>')
              strHtml = strHtml.concat('<td>' + totalUsageTimeInfo + ' 분</td></tr>')

              $('#productDetailInfoTable').append(strHtml)
              this.drawUsageTimeInfo()
            })
        })
    },
    changeDateFormat: function (dateVal) {
      const year = dateVal.substring(0, 4)
      const month = dateVal.substring(4, 6)
      const date = dateVal.substring(6, 8)

      return year + '년 ' + month + '월 ' + date + '일'
    },
    showMeteringUsageTimeView: function () {
      if (this.custNm.length === 0) {
        this.showAlertView('선택된 고객사명이 없습니다.')
        return
      }

      this.isMeteringUsageTimeViewed = true
      this.$modal.show('MeteringUsageTimePopup')
    },
    closeMeteringUsageTimeView: function () {
      this.isMeteringUsageTimeViewed = false
      this.$modal.hide('MeteringUsageTimePopup')
    },
    showMeteringReportView: function (value) {
      this.selectedYear = value.selectedYear
      this.selectedMonth = value.selectedMonth
      this.useMonthStartDate = value.usedMonthStartDate
      this.useMonthEndDate = value.usedMonthEndDate
      this.usedYear = value.usedYear
      this.usedMonth = value.usedMonth

      this.isMeteringReportViewed = true
      this.$modal.show('MeteringReportPopup')
    },
    closeMeteringReportView: function () {
      this.isMeteringReportViewed = false
      this.$modal.hide('MeteringReportPopup')
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
