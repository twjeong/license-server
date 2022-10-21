<template>
<div class="bg_contents">
  <div class="container">
    <head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>D'Amo Policy Server</title>
    <link rel="icon" href="../static/favicon.ico">
    </head>
    <!-- 페이지 타이틀  -->
    <section class="pagetitle clearfix">
        <div class="f_left">
            <h2 class="inline_block mr10">대시보드</h2>      <!-- 타이틀  -->
            <p class="inline_block" id="childtest">제품 사용 현황, 종량제 라이선스 사용 현황, 시스템 현황에 대해 알 수 있습니다.</p>     <!-- 설명  -->
        </div>
        <div class="f_right txt_right">
            <p class="inline_block">Home / <span>대시보드</span></p>       <!-- 페이지 경로  -->
        </div>
    </section>

    <!-- 제품 사용 현황  -->
    <section class="section_block h240">
        <div class="section_title">
            <h3 class="inline_block mr20">제품 사용 현황</h3>
            <p class="inline_block">제품 라이선스에 대한 상세 현황을 알 수 있습니다.</p>
            <a @click="buildProductUsageStatus()"><img src="../static/img/icon_refresh.png" class="icon_refresh"></a>
        </div>
        <div class="section_contents">
            <div class="clearfix">
                <!-- 전체 서버 수  -->
                <div class="layout-3-left">
                    <div class="numberCard clearfix">
                        <div class="card_name f_left w30pro mt40">
                            전체<br> 제품 수
                        </div>
                        <div class="card_circle f_left w70pro">
                            <div class="ring bg_dark">
                              <p>{{ totalProductNumber }}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 만료 예정 라이선스 수  -->
                <div class="layout-3-left">
                    <div class="numberCard clearfix">
                        <div class="card_name f_left w30pro mt40">
                            만료 예정<br> 라이선스 수
                        </div>
                        <div class="card_circle f_left w70pro">
                            <div class="ring bg_yellow">
                              <p>{{ toExpireProductNumber }}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 만료된 라이선스 수  -->
                <div class="layout-3-left">
                    <div class="numberCard clearfix">
                        <div class="card_name f_left w30pro mt40">
                            만료된<br> 라이선스 수
                        </div>
                        <div class="card_circle f_left w70pro">
                            <div class="ring bg_red">
                              <p>{{ expireProductNumber }}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- 클라우드 서비스 사용 현황  -->
    <section class="section_block h270" style="overflow-x: auto; overflow-y: hidden;">
        <div class="section_title">
            <h3 class="inline_block mr20">종량제 라이선스 사용 현황</h3>
            <p class="inline_block">종량제 라이선스에 대한 사용 현황을 알 수 있습니다.</p>
            <a @click="buildCloudServiceUsageStatus()"><img src="../static/img/icon_refresh.png" class="icon_refresh"></a>
        </div>
        <div class="section_contents">
            <div class="clearfix">
                <!-- 종량제 라이선스 사용 제품 수 -->
                <div class="layout-3-left">
                    <div class="numberCard clearfix">
                        <div class="card_name f_left w30pro">
                            종량제 라이선스<br> 사용 제품 수
                        </div>
                        <div class="card_circle f_left w70pro">
                            <div class="ring bg_dark">
                              <p>{{ cloudProductNumber }}</p>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 제품별  사용 시간 -->
                <div class="f_left w60pro">
                    <div class="tableCard clearfix">
                        <div class="card_name f_left w20pro">
                            제품별<br> 사용 시간
                        </div>
                        <div data-app class="card_searchtable w80pro f_left">
                          <label class="w60 mt15 mr5 txt_left f_left">기간 설정</label>
                          <v-menu
                            v-model="beginDateMenu"
                            :close-on-content-click="false"
                            transition="scale-transition"
                            offset-y
                            max-width="290px"
                          >
                            <template v-slot:activator="{ on }">
                              <v-text-field
                                v-model="beginDate"
                                v-on="on"
                                dense
                                prepend-icon="mdi-calendar-range"
                                class="w120 f_left centered-input"
                                @keyup.enter="buildCloudServiceUsageStatus"
                              ></v-text-field>
                            </template>
                          <v-date-picker
                              v-model="beginDate"
                              no-title
                              dark
                              @input="beginDateMenu = false"
                              @change="buildCloudServiceUsageStatus"
                              class="date-picker"
                            ></v-date-picker>
                          </v-menu>
                          <label class="mt15 ml5 mr5 f_left">~</label>
                          <v-menu
                            v-model="endDateMenu"
                            :close-on-content-click="false"
                            transition="scale-transition"
                            offset-y
                            max-width="290px"
                          >
                            <template v-slot:activator="{ on }">
                              <v-text-field
                                v-model="endDate"
                                v-on="on"
                                dense
                                prepend-icon="mdi-calendar-range"
                                class="w120 f_left centered-input"
                                @keyup.enter="buildCloudServiceUsageStatus"
                              ></v-text-field>
                            </template>
                            <v-date-picker
                              v-model="endDate"
                              no-title
                              dark
                              @input="endDateMenu = false"
                              @change="buildCloudServiceUsageStatus"
                              class="date-picker"
                            ></v-date-picker>
                          </v-menu>
                          <div class="fixed_tbl_container f_left"> 
                              <table 
                                class="metering_table fixed_table fixed_table_dashboard_cloud w550"
                                v-bind:class="{ 'metering_table fixed_table fixed_table_dashboard_cloud_ie w550' : isBrowserTypeIE }">
                                  <thead>
                                  <tr>
                                      <th>일련 번호</th>
                                      <th>고객사명</th>
                                      <th>제품명</th>
                                      <th>사용 시간(분)</th>
                                  </tr>
                                  </thead>
                                  <tbody ref="cloudMeteringTable" class="h80">
                                  </tbody>
                              </table>
                          </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- 시스템 현황  -->
    <section class="section_block h270">
        <div class="section_title">
            <h3 class="inline_block mr20">시스템 현황</h3>
            <p class="inline_block">메모리, 디스크, CPU에 대한 사용 현황을 알 수 있습니다.</p>
        </div>
        <div class="section_contents">
            <div class="clearfix">
                <!-- 메모리 사용 현황 -->
                <div class="layout-3-left">
                  <GChart
                    :settings="{ packages: ['corechart'] }"
                    type="PieChart"
                    :data="memoryChartData"
                    :options="memoryChartOptions"
                  />
                </div>

                <!-- 디스크 사용 현황 -->
                <div class="layout-3-left">
                  <GChart
                    :settings="{ packages: ['corechart'] }"
                    type="PieChart"
                    :data="diskChartData"
                    :options="diskChartOptions"
                  />
                </div>

                <!-- CPU 사용 현황 -->
                <div class="layout-3-left">
                  <GChart
                    :settings="{ packages: ['corechart'] }"
                    type="PieChart"
                    :data="cpuChartData"
                    :options="cpuChartOptions"
                  />
                </div>
            </div>
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
import { GChart } from 'vue-google-charts'
import AlertPopup from './Component/AlertPopup.vue'

export default ({
  components: {
    GChart,
    AlertPopup
  },
  data () {
    return {
      totalProductNumber: 0,
      toExpireProductNumber: 0,
      expireProductNumber: 0,
      cloudProductNumber: 0,
      memoryChartData: null,
      memoryChartOptions: null,
      diskChartData: null,
      diskChartOptions: null,
      cpuChartData: null,
      cpuChartOptions: null,
      beginDateMenu: false,
      endDateMenu: false,
      beginDate: '',
      endDate: '',
      isBrowserTypeIE : false,
      isAlertViewed: false
    }
  },
  created () {
    if ((navigator.appName === 'Netscape' && navigator.userAgent.search('Trident') !== -1) || (navigator.userAgent.toLowerCase().indexOf('msie') !== -1)) { // IE 일 경우
      this.isBrowserTypeIE = true
    }
  },
  mounted () {
    this.initDate()
    this.buildProductUsageStatus()
    this.buildCloudServiceUsageStatus()
    this.buildSystemResourceStatus()
    this.todo()
  },
  destroyed () {
    clearInterval(this.intervalid)
  },
  methods: {
    todo: function () {
      this.intervalid = setInterval(() => {
        this.buildSystemResourceStatus()
      }, 3000)
    },
    initDate: function () {
      const today = new Date()
      const eDate = leadingZeros(today.getFullYear(), 4) + '-' + leadingZeros(today.getMonth() + 1, 2) + '-' + leadingZeros(today.getDate(), 2)
      this.endDate = eDate

      const aYearAgoDay = new Date()
      const aYearAgoDate = aYearAgoDay.getTime() - (365 * 24 * 60 * 60 * 1000)
      aYearAgoDay.setTime(aYearAgoDate)
      const bDate = leadingZeros(aYearAgoDay.getFullYear(), 4) + '-' + leadingZeros(aYearAgoDay.getMonth() + 1, 2) + '-' + leadingZeros(aYearAgoDay.getDate(), 2)
      this.beginDate = bDate

      function leadingZeros (n, digits) {
        var zero = ''
        n = n.toString()

        if (n.length < digits) {
          for (let i = 0; i < digits - n.length; i++) {
            zero += '0'
          }
        }
        return zero + n
      }

      const strBeginDate = this.beginDate
      const strEndDate = this.endDate

      this.beginDate = strBeginDate
      this.endDate = strEndDate
    },
    buildProductUsageStatus: function () {
      axios.get('/license-service/licenses', {
        params: {
          'current-page-no': 1, 'records-per-page': 65535
        }
      }).then(
        res => {
          let localTotalProductNumber = 0
          let localToExpireProductNumber = 0
          let localExpireProductNumber = 0

          for (let i = 0; i < res.data.licenseList.length; i++) {
            if (res.data.licenseList[i].status === 'UNUSED_LICENSE') {
              continue
            }

            if (res.data.licenseList[i].licenseEndDate) {
              if (res.data.licenseList[i].expireType === 'EXPIRE') {
                localExpireProductNumber++
              } else if (res.data.licenseList[i].expireType === 'TO_EXPIRE') {
                localToExpireProductNumber++
              }
            }

            localTotalProductNumber++
          }

          this.totalProductNumber = localTotalProductNumber
          this.toExpireProductNumber = localToExpireProductNumber
          this.expireProductNumber = localExpireProductNumber
        })
    },
    buildCloudServiceUsageStatus: function () {
      if (this.isValidDate(this.beginDate) === false ||
        this.isValidDate(this.endDate) === false) {
        
        this.showAlertView('YYYY-MM-DD 형식으로 입력해주시기 바랍니다.')
        return
      }

      const tmpStartDate = this.beginDate
      const tmpEndDate = this.endDate

      if (tmpStartDate > tmpEndDate) {
        this.showAlertView('기간 설정 시작 날짜가 종료 날짜보다 미래일 수 없습니다.')
        return false
      }

      axios.get('/license-service/specific-licenses', {
        params: {
          'current-page-no': 1, 'records-per-page': 65535, 'license-type': 'CLOUD_METERING'
        }
      }).then(
        res => {
          let cloudProductNumber = 0

          const startDateYear = tmpStartDate.substring(0, 4)
          const startDateMonth = tmpStartDate.substring(5, 7)
          const startDateDate = tmpStartDate.substring(8, 10)

          const endDateYear = tmpEndDate.substring(0, 4)
          const endDateMonth = tmpEndDate.substring(5, 7)
          const endDateDate = tmpEndDate.substring(8, 10)

          let strHtml = ''
          for (let i = 0; i < res.data.licenseList.length; i++) {
            axios.get('/license-service/specific-usage-time', {
              params: {
                'current-page-no': 1,
                'records-per-page': 65535,
                'summary-type': 'serial-no',
                'serial-no': res.data.licenseList[i].serialNo,
                'start-date': startDateYear + '' + startDateMonth + '' + startDateDate,
                'end-date': endDateYear + '' + endDateMonth + '' + endDateDate
              }
            }).then(
              sutRes => {
                if (sutRes.data.usageTimeList[0].minuteOfUsageTime !== null) {
                  strHtml = strHtml.concat('<tr>')
                  strHtml = strHtml.concat('<td style="font-size: 10px !important;">' +
                      res.data.licenseList[i].serialNo + '</td>')
                  strHtml = strHtml.concat('<td style="font-size: 10px !important;">' +
                      res.data.licenseList[i].custNm + '</td>')
                  strHtml = strHtml.concat('<td style="font-size: 10px !important;">' +
                      res.data.licenseList[i].componentName + '</td>')
                  strHtml = strHtml.concat('<td style="font-size: 10px !important;">' +
                      sutRes.data.usageTimeList[0].minuteOfUsageTime + '</td>')
                  strHtml = strHtml.concat('</tr>')

                  cloudProductNumber++
                }

                this.cloudProductNumber = cloudProductNumber
                this.$refs.cloudMeteringTable.innerHTML = strHtml
              })
          }
        })
    },
    buildSystemResourceStatus: function () {
      axios.get('/license-service/systems').then(
        res => {
          this.memoryChartData = [
            ['Task', 'Percentage'],
            ['Usage', Math.floor(res.data.usageMemory)],
            ['Free', Math.floor((res.data.totalMemory - res.data.usageMemory))]
          ]

          this.memoryChartOptions = {
            title: '메모리 사용 현황 (Total : ' + this.numberWithCommas(res.data.totalMemory) + ' MB)',
            titleTextStyle: {
              color: '#2a3046',
              bold: false
            },
            animation: {
              startup: true,
              duration: 1000,
              easing: 'linear'
            },
            fontSize: 15,
            pieSliceText: 'none',
            slices: {
              0: { color: '#3498DB' },
              1: { color: '#ECF0F1' }
            }
          }

          this.diskChartData = [
            ['Task', 'Percentage'],
            ['Usage', res.data.usageSpace],
            ['Free', (res.data.totalSpace - res.data.usageSpace)]
          ]

          this.diskChartOptions = {
            title: '디스크 사용 현황 (Total : ' + res.data.totalSpace.toFixed(2) + ' GB)',
            titleTextStyle: {
              color: '#2a3046',
              bold: false
            },
            animation: {
              startup: true,
              duration: 1000,
              easing: 'linear'
            },
            fontSize: 15,
            pieSliceText: 'none',
            slices: {
              0: { color: '#f66363' },
              1: { color: '#ECF0F1' }
            }
          }

          this.cpuChartData = [
            ['Task', 'Percentage'],
            ['Usage', res.data.usageCpu],
            ['Free', (100 - res.data.usageCpu)]
          ]

          this.cpuChartOptions = {
            title: 'CPU 사용 현황',
            titleTextStyle: {
              color: '#2a3046',
              bold: false
            },
            animation: {
              startup: true,
              duration: 1000,
              easing: 'linear'
            },
            fontSize: 15,
            pieSliceText: 'none',
            slices: {
              0: { color: '#12af92' },
              1: { color: '#ECF0F1' }
            }
          }
        }
      )
    },
    numberWithCommas: function (x) {
      return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    },
    isValidDate: function (dateString) {
      const regEx = /^\d{4}-\d{2}-\d{2}$/
      if (!dateString.match(regEx)) {
        return false
      }
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
@import '../static/css/jquery-ui-1.12.1.css';

.centered-input >>> input {
  text-align: center;
}
.date-picker {
  height: 380px;
}
.metering_table thead th {
  font-size: 12px !important;
}
</style>
