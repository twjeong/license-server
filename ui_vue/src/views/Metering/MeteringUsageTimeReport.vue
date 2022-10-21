<template>
  <div>
    <div class="metering_modal">
      <div class="metering_modal-card">
        <h2 class="metering_modal-head">{{ title }}</h2>

        <div ref="pentaFrontCover" hidden>
          <div class="wrap">
            <div id="cover">
              <img src="../../static/img/damo_logo_c.png" class="damo_logo" alt="D'Amo logo" style="float: right;" />

              <main>
                <h2 class="customer_company">{{ custNm }}</h2>
                <h1 id="doc_title">D'Amo 종량제 빌링 보고서</h1>
                <div id="cover_image">
                  <img src="../../static/img/cover.jpg">
                  <h2>{{ selectedYear }}년 {{ selectedMonth }}월</h2>
                </div>
              </main>

              <footer>
                <img src="../../static/img/penta_logo_c.png" class="penta_logo_c" alt="penta logo" role="presentation" />
              </footer>
            </div>
          </div>

          <div class="wrap_address">
            <div class="header">
              <ul>
                <li>
                  <img src="../../static/img/header_bg_left.png" alt="header bg left">
                </li>
                <li><h3>{{ selectedYear }}년 {{ selectedMonth }}월 종량제 빌링 보고서</h3></li>
                <li style="float: right;">
                  <img src="../../static/img/header_bg_right.png" alt="header bg right">
                </li>
              </ul>
            </div>

            <main>
              <section>
                <h4>기본 정보</h4>
              </section>
              <section>
                <table class="table_basic table_tb table_bottom w760">
                  <thead>
                  <tr class="table_header">
                    <th class="table_first_column">고객 정보</th>
                    <th class="table_second_column">업체명</th>
                    <th colspan="3" class="table_third_column w60pro">{{ custNm }}</th>
                  </tr>
                  <tr>
                    <th rowspan="5" class="table_first_column">공급자 정보</th>
                    <th class="table_second_column">등록 번호</th>
                    <th class="table_third_column">116-91-65189</th>
                  </tr>
                  <tr>
                    <th class="table_second_column w10pro">상호</th>
                    <th class="table_third_column w20pro">펜타시큐리티시스템(주)</th>
                    <th class="table_second_column w10pro">대표</th>
                    <th class="table_third_column w20pro">이대표</th>
                  </tr>
                  <tr>
                    <th class="table_second_column">주소</th>
                    <th colspan="3" class="table_third_column">서울특별시 영등포구 여의공원로 115(세우빌딩) 9층, 07241</th>
                  </tr>
                  <tr>
                    <th class="table_second_column w10pro">업태</th>
                    <th class="table_third_column w20pro">정보통신, 도매</th>
                    <th class="table_second_column w10pro">종목</th>
                    <th class="table_third_column w20pro">소프트웨어 개발 및 공급, 컴퓨터기기</th>
                  </tr>
                  <tr>
                    <th class="table_second_column w10pro">전화</th>
                    <th class="table_third_column w20pro">02-780-7728</th>
                    <th class="table_second_column w10pro">팩스</th>
                    <th class="table_third_column w20pro">02-786-5281</th>
                  </tr>
                  </thead>
                </table>
              </section>
            </main>
          </div>
        </div>

        <div ref="chargeForUsing">
          <div class="header">
            <ul>
              <li>
                <img src="../../static/img/header_bg_left.png" alt="header bg left">
              </li>
              <li><h3>{{ selectedYear }}년 {{ selectedMonth }}월 종량제 빌링 보고서</h3></li>
              <li style="float: right;">
                <img src="../../static/img/header_bg_right.png" alt="header bg right">
              </li>
            </ul>
          </div>

          <main>
            <section>
              <h4>이용 요금 내역</h4>
              <ul>
                <li><p>이용 기간: {{ useMonthStartDate }} ~ {{ useMonthEndDate }}</p></li>
              </ul>
            </section>

            <!-- 이용 요금 카드  -->
            <section>
              <div class="card-container">

                <!-- 이번 달 요금  -->
                <div class="card current">
                  <p class="card-header current">{{ selectedYear }}년 {{ selectedMonth }}월 이용 요금</p>
                  <div class="card-price current">
                    <p class="price">{{ markPrice(priceList.amount) }}원</p>
                    <span>(VAT 포함)</span>
                  </div>
                </div>

                <!-- 전월 요금  -->
                <div class="card">
                  <p class="card-header">전월 요금 ({{ usedMonthStr }})</p>
                  <div class="card-price">
                    <p class="price">{{ markPrice(priceList.lastAmount) }}원</p>
                    <span>(VAT 포함)</span>
                  </div>
                </div>
              </div>
            </section>

            <section class="wrap_report">
              <p class="unit">(단위: 원)</p>

              <template>
                <v-card class="mt10 mb10">
                  <v-layout>
                    <v-flex>
                      <v-data-table
                        :headers="productPriceHeaders"
                        :items="priceList.productList"
                        hide-default-header
                        hide-default-footer
                        :items-per-page=65535>
                        <template v-slot:header="{ props: { headers } }">
                          <thead>
                          <tr class="line-normal">
                            <th v-for="h in headers" :key="h.key_idx" class="bg-dark" v-html="h.text">
                            </th>
                          </tr>
                        </thead>
                        </template>
                        <template v-slot:body="{ items }">
                          <tbody>
                            <template v-for="item in items" >
                              <th :rowspan="item.componentList.length + 2"
                                :key="item.key"
                                class="line-normal">{{ item.category }}</th>
                              <tr v-for="component in item.componentList" :key="component.key_idx">
                                <td>{{ component.componentName }}</td>
                                <td class="txt-center">{{ component.core }}</td>
                                <td class="txt-right">{{ component.calHour }}</td>
                                <td class="txt-right">{{ markPrice(component.providedValue) }}</td>
                                <td class="txt-right">{{ markPrice(component.tax) }}</td>
                              </tr>
                              <tr class="line-normal" :key="item.key">
                                <th colspan="3" class="bg-light txt-center">합계</th>
                                <td class="txt-right txt-bold">{{ markPrice(item.providedValue) }}</td>
                                <td class="txt-right txt-bold">{{ markPrice(item.tax) }}</td>
                              </tr>
                            </template>
                            <tr>
                              <th colspan="4" class="bg-dark">총계</th>
                              <td class="txt-right txt-bold">{{ markPrice(priceList.providedValue) }}</td>
                              <td class="txt-right txt-bold">{{ markPrice(priceList.tax) }}</td>
                            </tr>
                          </tbody>
                        </template>
                      </v-data-table>
                    </v-flex>
                  </v-layout>
                </v-card>
              </template>
            </section>
          </main>
        </div>

        <div ref="compareChargeOfLastMonth">
          <div class="header">
            <ul>
              <li>
                <img src="../../static/img/header_bg_left.png" alt="header bg left" style="width: 20px; height: 20px; margin-right: 15px;">
              </li>
              <li><h3>{{ selectedYear }}년 {{ selectedMonth }}월 종량제 빌링 보고서</h3></li>
              <li style="float: right;">
                <img src="../../static/img/header_bg_right.png" alt="header bg right" style="width: 500px; height: 20px; margin-left: 15px;">
              </li>
            </ul>
          </div>

          <main>
            <section>
              <h4>전월 대비 요금 비교 표</h4>
            </section>

            <section class="wrap_report">
              <p class="unit">(단위: 원)</p>

              <template>
                <v-card class="mt10 mb10">
                  <v-layout>
                    <v-flex>
                      <v-data-table
                        :headers="comparePriceHeaders"
                        :items="priceList.productList"
                        hide-default-header
                        hide-default-footer
                        :items-per-page=65535>
                        <template v-slot:header="{ props: { headers } }">
                          <thead>
                            <tr class="line-normal">
                              <th :rowspan="2" class="bg-dark" v-html="headers[0].text"></th>
                              <th :rowspan="2" class="bg-dark" v-html="headers[1].text"></th>
                              <th :rowspan="2" class="bg-dark" v-html="headers[2].text"></th>
                              <th :colspan="2" class="bg-dark" v-html="headers[3].text"></th>
                              <th :rowspan="2" class="bg-dark" v-html="headers[4].text"></th>
                            </tr>
                            <tr class="line-normal">
                              <th class="bg-light">{{ selectedYear }}년 {{ selectedMonth }}월</th>
                              <th class="bg-light">{{ lastYear }}년 {{ lastMonth }}월</th>
                            </tr>
                          </thead>
                        </template>
                        <template v-slot:body="{ items }">
                          <tbody>
                            <template v-for="item in items" >
                              <th :rowspan="item.componentList.length + 1" :key="item.key" class="line-normal">{{ item.category }}</th>
                              <tr v-for="(component, i) in item.componentList"
                                v-bind:class="{ 'line-normal': i === item.componentList.length - 1 }"
                                :key="component.key_idx">
                                <td>{{ component.componentName }}</td>
                                <td class="txt-center">{{ component.core }}</td>
                                <td class="txt-right bg-skyblue">{{ markPrice(component.amount) }}</td>
                                <td class="txt-right">{{ markPrice(component.lastAmount) }}</td>
                                <td class="txt-right">
                                  <img class="ico-updown" v-if="component.variationAmount > 0" src="../../static/img/up.png">
                                  <img class="ico-updown" v-else-if="component.variationAmount < 0" src="../../static/img/down.png">
                                    {{ markPrice(component.variationAmount) }}
                                </td>
                              </tr>
                            </template>
                            <tr>
                              <th colspan="4" class="bg-dark">총계</th>
                              <td class="txt-right txt-bold bg-skyblue">{{ markPrice(priceList.amount) }}</td>
                              <td class="txt-right txt-bold">{{ markPrice(priceList.lastAmount) }}</td>
                            </tr>
                          </tbody>
                        </template>
                      </v-data-table>
                    </v-flex>
                  </v-layout>
                </v-card>
              </template>
            </section>
          </main>
        </div>

        <div ref="compareChargeGraph">
          <div class="header">
            <ul>
              <li>
                <img src="../../static/img/header_bg_left.png" alt="header bg left">
              </li>
              <li><h3>{{ selectedYear }}년 {{ selectedMonth }}월 종량제 빌링 보고서</h3></li>
              <li style="float: right;">
                <img src="../../static/img/header_bg_right.png" alt="header bg right">
              </li>
            </ul>
          </div>

          <div class="wrap_report_graph">
            <section>
              <h4>지난 6개월 전체 요금 <span class="small">(단위 : 원, VAT 포함)</span> 그래프</h4>
            </section>
            <section class="wrap_report_chart">
              <MeteringMonthBarChart
                :custNm="custNm"
                :selectedYear="selectedYear"
                :selectedMonth="usedMonth"
              ></MeteringMonthBarChart>
            </section>
          </div>
        </div>
      </div>

      <div ref="pentaBackCover" class="wrap" hidden>
        <div id="back">
          <img src="../../static/img/damo_logo_c.png" class="damo_logo" />

          <div>
            <img src="../../static/img/penta_logo_c.png" class="penta_logo_c" />
            <div class="address">
              <p>Penta Security Systems Inc.</p>
              <p>서울특별시 영등포구 여의공원로 115(세우빌딩) 9층 (07241)<br>
                    Tel. 02-780-7728 Fax. 02-786-5281 / http://www.pentasecurity.co.kr</p>
              <p>&copy; 2021 Penta Security Systems Inc. All rights reserved.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <Spinner v-if="loadingStatus" size='medium' class="mt10" message='Loading...'></Spinner>
    <div class="mt15 mb15 txt_center">
      <input type="button" value="PDF로 저장" @click="saveReportPdf()" class="btn_basic btn_cancel bg_bluegreen w120 mr10">
      <input type="button" value="닫기" @click="cancel()" class="btn_cancel btn_basic color_indigo btn_outline w120">
    </div>
  </div>
</template>

<script>
import JsPDF from 'jspdf'
import domtoimage from 'dom-to-image'
import axios from 'axios'
import MeteringMonthBarChart from './MeteringMonthBarChart'
import Spinner from 'vue-simple-spinner'

export default {
  props: {
    custNm: { type: String },
    selectedYear: { type: String },
    selectedMonth: { type: String },
    useMonthStartDate: { type: String },
    useMonthEndDate: { type: String },
    usedYear: { type: String },
    usedMonth: { type: Number }
  },
  components: {
    Spinner,
    MeteringMonthBarChart
  },
  data: function () {
    return {
      title: '종량제 빌링 보고서',
      usedMonthStr: '',
      lastYear: '',
      lastMonth: '',
      productPriceHeaders: [
        { text: '구분', align: 'center', sortable: true, value: 'category', width: '150px' },
        { text: '제품', align: 'center', sortable: true, value: 'componentName', width: '150px' },
        { text: 'Core', align: 'center', sortable: true, value: 'core', width: '100px' },
        { text: '정산 사용량(시간)', align: 'center', sortable: true, value: 'calHour', width: '150px' },
        { text: '공급가액', align: 'center', sortable: true, value: 'providedValue', width: '100px' },
        { text: '세액', align: 'center', sortable: true, value: 'taxAmount', width: '100px' }
      ],
      comparePriceHeaders: [
        { text: '구분', align: 'center', sortable: true, value: 'category', width: '150px' },
        { text: '제품', align: 'center', sortable: true, value: 'componentName', width: '150px' },
        { text: 'Core', align: 'center', sortable: true, value: 'core', width: '100px' },
        { text: '전체 요금 (VAT)', align: 'center', sortable: true, value: 'calHour', width: '200px' },
        { text: '증감액', align: 'center', sortable: true, value: 'providedValue', width: '100px' }
      ],
      priceList: [],
      loadingStatus: false
    }
  },
  mounted () {
    this.initDate()
    this.initPriceList()
  },
  methods: {
    initDate: function () {
      const year = this.useMonthStartDate.substring(0, 4)
      let month = this.useMonthStartDate.substring(5, 7)
      if (month < 10) {
        month = month.substring(1, 2)
      }

      this.usedMonthStr = year + '년 ' + month + '월'

      this.lastMonth = this.selectedMonth - 1
      if (this.lastMonth === 0) {
        this.lastMonth = 12
        this.lastYear = this.selectedYear - 1
      } else {
        this.lastYear = this.selectedYear
      }
    },
    initPriceList: function () {
      axios.get('/license-service/licenses/billing/amount-detail', {
        params: {
          year: this.usedYear,
          month: this.usedMonth,
          custNm: this.custNm
        }
      }).then(res => {
        this.priceList = res.data.amountList
      })
    },
    saveReportPdf: async function () {
      if ((navigator.appName === 'Netscape' && navigator.userAgent.search('Trident') !== -1) || (navigator.userAgent.toLowerCase().indexOf('msie') !== -1)) { // IE 일 경우
        this.$emit('show-alert-modal', 'IE 브라우저에서는 이 기능을 지원하지 않습니다.')
        return
      }

      const pdfName = 'DAmo_Billing_Report'

      const frontCover = this.$refs.pentaFrontCover
      const chargeForUsing = this.$refs.chargeForUsing
      const compareChargeOfLastMonth = this.$refs.compareChargeOfLastMonth
      const compareChargeGraph = this.$refs.compareChargeGraph
      const backCover = this.$refs.pentaBackCover

      const doc = new JsPDF('p', 'mm', 'a4')

      this.startSpinner()
      frontCover.removeAttribute('hidden')
      await this.createDomToImage(frontCover, doc, true)
      frontCover.setAttribute('hidden', 'hidden')

      await this.createDomToImage(chargeForUsing, doc, false)
      await this.createDomToImage(compareChargeOfLastMonth, doc, false)
      await this.createDomToImage(compareChargeGraph, doc, false)

      backCover.removeAttribute('hidden')
      await this.createDomToImage(backCover, doc, false)
      backCover.setAttribute('hidden', 'hidden')

      const isEdge = (window.navigator.userAgent.indexOf('Chrome') !== -1) &&
      (window.navigator.userAgent.indexOf('Edg') !== -1)

      if (isEdge === false) {
        const pageCount = doc.internal.getNumberOfPages()
        doc.deletePage(pageCount)
      }

      doc.save(pdfName + '.pdf')
      this.endSpinner()
      this.$emit('ok')
    },
    cancel: function () {
      this.$emit('close-modal')
    },
    startSpinner: function () {
      this.loadingStatus = true
    },
    endSpinner: function () {
      this.loadingStatus = false
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
    createDomToImage: async function (el, doc, cover) {
      const scale = 2
      const imgWidth = 210
      const pageHeight = 297

      return domtoimage.toJpeg(el, {
        quality: 0.98,
        bgcolor: '#fff',
        width: el.clientWidth * scale,
        height: el.clientHeight * scale,
        style: {
          transform: 'scale(2)',
          transformOrigin: 'top left'
        }
      }).then(function (img) {
        const pdfWidth = el.clientWidth * scale
        const pdfHeight = el.clientHeight * scale
        let position = 0
        const imgHeight = pdfHeight * imgWidth / pdfWidth
        let heightLeft = imgHeight

        if (cover === false) {
          doc.addPage()
        }

        doc.addImage(img, 'JPEG', 0, position, imgWidth, imgHeight)
        heightLeft -= pageHeight

        while (heightLeft >= 0) {
          position = heightLeft - imgHeight
          doc.addPage()
          doc.addImage(img, 'JPEG', 0, position, imgWidth, imgHeight)
          heightLeft -= pageHeight
        }
      })
    },
    markPrice: function (price) {
      let localPrice = price + ''
      if (localPrice[0] === '-') {
        localPrice = localPrice.slice(1)
      }

      return localPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
    }
  }
}
</script>

<style scoped>
@import '../../static/css/style_billing.css';

.metering_modal {
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
  overflow-x: hidden;
  overflow-y: auto;
  height: 630px;
}
.metering_modal-card {
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
}
.metering_modal-head {
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
.v-data-table__wrapper {
  overflow-x: hidden;
  overflow-y: hidden;
}
.table_tb {
  border-top: 2px solid black;
  border-bottom: 3px solid black;
}
.table_header {
  border-bottom: 1.5px solid black;
  opacity: 0.8;
}
.table_first_column {
  width: 10%;
  background-color: #d0d3d4;
  color: black;
}
.table_second_column {
  border: 1px solid #d0d3d4;
  color: black;
}
.table_third_column {
  background-color: #fff;
  color: black;
}
.wrap_address {
  max-width: 210mm; min-width: 210mm; max-height: 297mm; min-height: 297mm; height: 297mm;
  margin-top: 20mm; margin-left: 0; background-color: #fff;
}
.wrap_report {
  margin-left: 10px;
  margin-right: 10px;
}
.wrap_report_graph {
  margin-top: 50px;
  margin: 20px;
}
.wrap_report_chart {
  margin: 20px;
  height: 450px;
}
.header { height: 18mm; margin-top: 50px; }
.header ul { position: relative; }
.header ul li { display: inline-block; height: 7.297mm; }
.header ul li:first-child { position: absolute; left: 0.0mm; top: 0mm; }    /* 좌측 배경이미지 위치 */
.header ul li:nth-child(2) { position: absolute; left: 10.0mm; top: 1mm; }
.header ul li:last-child { position: absolute; left: 103mm; top: 0; }      /* 우측 배경이미지 위치 */
.header ul li:first-child img { width: 7.002mm; height: 7.297mm; }      /*  좌측 배경이미지 크기  */
.header ul li:last-child img { width: 119.818mm; height: 7.297mm; }     /*  우측 배경이미지 크기  */
.header h3 { font-size: 1.72rem; font-weight: 700; color: #02275e; }
</style>
