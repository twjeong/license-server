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
            <h2 class="inline_block mr10">제품 라이선스 관리</h2>
            <p class="inline_block">라이선스 발급/갱신/폐기 및 라이선스의 상세 정보를 확인 할 수 있습니다.</p>
        </div>
        <div class="section_title">
            <a @click="buildAllLicenseInfo()"><img src="../static/img/icon_refresh.png" class="icon_refresh mt7" style="width:13px"></a>
        </div>
        <div class="f_right txt_right mr10">
            <p class="inline_block">Home / <span>제품 라이선스 관리</span></p>
        </div>
    </section>

    <!-- 라이선스 사용량  -->
    <section class="section_block">
      <div class="section_contents">
        <!-- 검색 결과 및 조건 영역 -->
        <div class="clearfix">
          <!-- 검색 결과  -->
          <div class="layout-2-left pt10">
            전체 제품 수 : <span class="strong color_black">{{ totalProductNumber }}</span>개
              / Cloud Service 수 : <span class="strong">{{ cloudProductNumber }}</span>개
              / 라이선스 만료 예정 : <span class="strong">{{ toExpireProductNumber }}</span>개
              / 라이선스 만료 : <span class="strong">{{ expireProductNumber }}</span>개
          </div>

          <!-- 검색 입력  -->
          <div class="layout-2-right txt_right">
            <div class="select_menu w100">
              <select id="licenseSearchOption" required>
                <option value="" selected>전체</option>
                <option value="componentName">제품명</option>
                <option value="componentVersion">제품 버전</option>
                <option value="serialNo">라이선스 일련번호</option>
                <option value="ipAddress">IP</option>
                <option value="licenseType">라이선스 타입</option>
              </select>
            </div>
            <input title="주의! > 두 단어 이상은 검색되지 않습니다."
                    type="text" id="licenseSearchText" placeholder="검색 조건을 입력하세요."
                    @keyup.enter="buildSpecificLicenseInfo()" class="w200 ml5">
            <input type="submit" value="검색"
                    @click="buildSpecificLicenseInfo()" class="btn_small bg_dark w60 ml5">
          </div>
        </div>

        <!-- 테이블 영역 -->
        <template>
        <v-card class="fixed_tbl_container mt10 mb10">
          <v-layout column style="width: 2920px;">
            <v-flex style="overflow: auto">
              <v-data-table
                :headers="headers"
                :items="modifyProductList"
                hide-default-header
                hide-default-footer
                :items-per-page=65535
                item-key="serialNo"
                class="fixed_table fixed_table_license_info"
                v-bind:class="{ 'fixed_table fixed_table_license_info_ie' : isBrowserTypeIE }">
                <template v-slot:header="{ props: { headers } }">
                  <thead>
                  <tr>
                    <th><input type="checkbox" v-model="allProductsSelect"></th>
                    <th v-for="h in headers" :key="h.serialNo" @click="sortProducts(h.value)" v-html="h.text">
                    </th>
                  </tr>
                </thead>
                </template>
                <template v-slot:body="{ items }">
                  <tbody class="h400">
                    <tr v-for="item in items" :key="item.serialNo">
                      <td><input type="checkbox" v-model="selectedProducts" :value="item" ref="CbxComponentInfo" class="cbx"></td>
                      <td>{{ item.custNm }}</td>
                      <td>{{ item.orderNum }}</td>
                      <td>{{ item.componentName }}</td>
                      <td>{{ item.componentVersion }}</td>
                      <td>{{ item.ipAddress }}</td>
                      <td>{{ item.core }}</td>
                      <td>{{ item.instanceNm }}</td>
                      <td v-html="item.scpFunction" v-bind:style="item.scpFunction === '-' ? 'text-align: center;' : 'text-align: left;'">
                        {{ item.scpFunction }}</td>
                      <td>{{ item.licenseType }}</td>
                      <td v-html="item.status" v-bind:class="item.expireType === 'EXPIRE' ? 'color_red' :
                        item.expireType === 'TO_EXPIRE' ? 'color_yellow' : ''"> >{{ item.status }}</td>
                      <td>{{ item.licenseStartDate }}</td>
                      <td>{{ item.licenseEndDate }}</td>
                      <td>{{ item.licenseIssueDate }}</td>
                      <td>{{ item.licenseExpiredDate }}</td>
                      <td>{{ item.serialNo }}</td>
                      <td style="width: 500px">{{ item.licenseFile }}</td>
                    </tr>
                  </tbody>
                </template>
              </v-data-table>
            </v-flex>
          </v-layout>
        </v-card>
        </template>

        <!-- 버튼 영역 -->
        <div class="txt_center mt50">
          <button @click="showLicenseIssueView()" class="btn_basic bg_bluegreen mr10">라이선스 발급</button>
          <button @click="showLicenseDiscardView()" class="btn_basic color_indigo btn_outline">라이선스 폐기</button>
        </div>
      </div>

      <!--  팝업 창 : 라이선스 발급  -->
      <modal name="LicenseIssuePopup"
        :draggable="true"
        :resizable="true"
        @closed="closeLicenseIssueView()"
        :width="1000"
        :height="600"
        :max-width="1650"
        :max-height="600">
        <LicenseIssuePopup
          v-if="isIssueViewed"
          :selectedProducts="selectedProducts"
          @ok="confirmLicenseIssueView()"
          @show-alert-modal="showAlertView"
          @close-modal="closeLicenseIssueView()">
        </LicenseIssuePopup>
      </modal>

      <!--  팝업 창 : 라이선스 폐기  -->
      <modal name="LicenseDiscardPopup"
        :draggable="true"
        @closed="closeLicenseDiscardView()"
        :width="400"
        :height="150">
        <LicenseDiscardPopup
          v-if="isDiscardViewed"
          @ok="showLicenseDiscardExecuteView()"
          @close-modal="closeLicenseDiscardView()">
        </LicenseDiscardPopup>
      </modal>

      <!--  팝업 창 : 라이선스 폐기 실행 -->
      <modal name="LicenseDiscardExecutePopup"
        :draggable="true"
        @closed="closeLicenseDiscardExecuteView()"
        :width="400"
        :height="160">
        <LicenseDiscardExecutePopup
          v-if="isDiscardExeViewed"
          @ok="confirmLicenseDiscardExecute()"
          @close-modal="closeLicenseDiscardExecuteView()">
        </LicenseDiscardExecutePopup>
      </modal>

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
    </section>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import LicenseIssuePopup from './License/LicenseIssuePopup'
import LicenseDiscardPopup from './License/LicenseDiscardPopup'
import LicenseDiscardExecutePopup from './License/LicenseDiscardExecutePopup'
import AlertPopup from './Component/AlertPopup.vue'

export default {
  components: {
    LicenseIssuePopup,
    LicenseDiscardPopup,
    LicenseDiscardExecutePopup,
    AlertPopup
  },
  data () {
    return {
      totalProductNumber: 0,
      cloudProductNumber: 0,
      toExpireProductNumber: 0,
      expireProductNumber: 0,
      selectedProducts: [],
      headers: [
        { text: '고객사명', align: 'center', sortable: true, value: 'custNm', width: '150px' },
        { text: '납품 계약 번호', align: 'center', sortable: true, value: 'orderNum', width: '150px' },
        { text: '제품명', align: 'center', sortable: true, value: 'componentName', width: '150px' },
        { text: '제품 버전', align: 'center', sortable: true, value: 'componentVersion', width: '100px' },
        { text: 'IP', align: 'center', sortable: true, value: 'ipAddress', width: '150px' },
        { text: 'Core', align: 'center', sortable: true, value: 'core', width: '100px' },
        { text: '인스턴스명', align: 'center', sortable: true, value: 'instanceNm', width: '150px' },
        { text: '기능<br>라이선스', align: 'center', sortable: false, width: '250px' },
        { text: '라이선스 타입', align: 'center', sortable: true, value: 'licenseType', width: '150px' },
        { text: '상태', align: 'center', sortable: true, value: 'status', width: '150px' },
        { text: '계약 시작일', align: 'center', sortable: true, value: 'licenseStartDate', width: '150px' },
        { text: '계약 만료일', align: 'center', sortable: true, value: 'licenseEndDate', width: '150px' },
        { text: '라이선스 발급일', align: 'center', sortable: true, value: 'licenseIssueDate', width: '160px' },
        { text: '라이선스 만료일', align: 'center', sortable: true, value: 'licenseExpiredDate', width: '160px' },
        { text: '라이선스 일련번호', align: 'center', sortable: true, value: 'serialNo', width: '300px' },
        { text: '라이선스 파일 경로', align: 'center', sortable: true, value: 'licenseFile', width: '516px' }
      ],
      productList: [],
      selectedContractInfo: '',
      currentSortCol: '상태',
      currentSortDir: 'asc',
      isIssueViewed: false,
      isDiscardViewed: false,
      isDiscardExeViewed: false,
      isBrowserTypeIE: false,
      isAlertViewed: false
    }
  },
  created () {
    this.buildAllLicenseInfo()

    if ((navigator.appName === 'Netscape' && navigator.userAgent.search('Trident') !== -1) || (navigator.userAgent.toLowerCase().indexOf('msie') !== -1)) { // IE 일 경우
      this.isBrowserTypeIE = true
    }
  },
  computed: {
    allProductsSelect: {
      get: function () {
        if (this.modifyProductList.length === 0) {
          return false
        }
        return this.modifyProductList.length === this.selectedProducts.length
      },
      set: function (e) {
        this.selectedProducts = []

        if (e) {
          this.modifyProductList.forEach((product) => {
            this.selectedProducts.push(product)
          })
        }
      }
    },
    modifyProductList: function () {
      return this.productList.map((obj) => Object.assign({}, obj, {
        licenseIssueDate: this.changeDateString(obj.licenseIssueDate),
        licenseExpiredDate: this.changeDateString(obj.licenseExpiredDate),
        licenseStartDate: this.changeDateString(obj.licenseStartDate),
        licenseEndDate: this.changeDateString(obj.licenseEndDate),
        instanceName: obj.instanceNm === null ? '' : obj.instanceNm,
        status: obj.expireType === 'EXPIRE' ? this.changeStatusString('EXPIRE_LICENSE', 'kor')
          : obj.expireType === 'TO_EXPIRE' ? this.changeStatusString('TO_EXPIRE_LICENSE', 'kor')
            : this.changeStatusString(`${obj.status}`, 'kor'),
        scpFunction: obj.licenseType !== null ? obj.componentName === 'BA-SCP' ? '대칭키 암복호화 API : ' + this.changeScpOptionText(obj.symApiYn, true) + '<br>' +
            '비대칭키 암복호화 API : ' + this.changeScpOptionText(obj.pubApiYn, false) + '<br>' +
            'KE-WIN 복호화 API : ' + this.changeScpOptionText(obj.kewinApiYn, false) + '<br>' +
            'KMS 외부키 연동 : ' + this.changeScpOptionText(obj.kmsYn, false) + '<br>' +
            'OS 계정별 암복호화 권한 제어 : ' + this.changeScpOptionText(obj.osYn, false) : obj.componentName === 'SG-KMS' || obj.componentName === 'SG-KMS_SA' ? '암호화 및 BA-SCP 연동 : ' +
            this.changeKmsOptionText(obj.kmsEnc) + '<br>' +
            'SCP 외부키 연동 : ' + this.changeKmsOptionText(obj.kmsPubKey) + '<br>' +
            '비공개키 연동 : ' + this.changeKmsOptionText(obj.kmsPriKey) : obj.licenseType !== null ? obj.componentName === 'WAPPLES' ? '보호 대상 웹서버 등록수 제한 : ' + obj.webCnt + '<br>' +
            '라이선스 만료시 대응 설정 : ' + obj.lisExpAct + '<br>' : '-' : '-'
          : null
      }))
    }
  },
  methods: {
    sortProducts: function (col) {
      if (this.currentSortCol === col) {
        this.currentSortDir = this.currentSortDir === 'asc' ? 'desc' : 'asc'
      } else {
        this.currentSortCol = col
      }

      this.productList.sort(this.sortBy(col, this.currentSortDir))
    },
    sortBy: function (property, order) {
      this.currnetSortDir = order
      return function (a, b) {
        let varA =
          typeof a[property] === 'string'
            ? a[property].toUpperCase()
            : a[property]

        let varB =
          typeof b[property] === 'string'
            ? b[property].toUpperCase()
            : b[property]

        if (varA === null) varA = ''
        if (varB === null) varB = ''

        let comparison = 0
        if (varA > varB) comparison = 1
        else if (varA < varB) comparison = -1
        return order === 'desc' ? comparison * -1 : comparison
      }
    },
    buildLicenseInfo: function (data) {
      this.productList = []

      let localTotalProductNumber = 0
      let localCloudProductNumber = 0
      let localToExpireProductNumber = 0
      let localExpireProductNumber = 0

      for (let i = 0; i < data.licenseList.length; i++) {
        if (data.licenseList[i].status === 'UNUSED_LICENSE') {
          continue
        }

        if (data.licenseList[i].licenseType === 'CLOUD_METERING' ||
            data.licenseList[i].licenseType === 'CLOUD_INSTANCE') {
          localCloudProductNumber++
        }

        if (data.licenseList[i].licenseEndDate) {
          if (data.licenseList[i].expireType === 'EXPIRE') {
            localExpireProductNumber++
          } else if (data.licenseList[i].expireType === 'TO_EXPIRE') {
            localToExpireProductNumber++
          }
        }

        localTotalProductNumber++
        this.productList.push(data.licenseList[i])
      }

      this.totalProductNumber = localTotalProductNumber
      this.cloudProductNumber = localCloudProductNumber
      this.toExpireProductNumber = localToExpireProductNumber
      this.expireProductNumber = localExpireProductNumber
    },
    buildAllLicenseInfo: function () {
      axios.get('/license-service/licenses', {
        params: {
          'current-page-no': 1, 'records-per-page': 65535
        }
      }).then(res => {
        this.buildLicenseInfo(res.data)
      })
    },
    buildSpecificLicenseInfo: function () {
      const text = document.getElementById('licenseSearchText').value
      const axiosParams = new URLSearchParams()

      if (text.length > 256) {
        this.showAlertView('검색 최대 입력 길이를 초과하였습니다.')
        return
      }

      if (text.length === 0) {
        this.buildAllLicenseInfo()
        return
      }

      axiosParams.append('current-page-no', 1)
      axiosParams.append('records-per-page', 65535)

      switch (document.getElementById('licenseSearchOption').value) {
        case 'componentName':
          axiosParams.append('component-name', text)
          break

        case 'componentVersion':
          axiosParams.append('component-version', text)
          break

        case 'serialNo':
          axiosParams.append('serial-no', text)
          break

        case 'ipAddress':
          axiosParams.append('ip-address', text)
          break

        case 'licenseType':
          if (text.length !== 0) {
            if (text === 'COMMERCIAL' || text === 'CLOUD_INSTANCE' || text === 'CLOUD_METERING') {
              axiosParams.append('license-type', text)
            } else {
              const emptyLicenseList = {
                licenseList: []
              }

              return this.buildLicenseInfo(emptyLicenseList)
            }
          }
          break

        default:
          if (text.length !== 0) {
            axiosParams.append('component-name', text)
            axiosParams.append('component-version', text)
            axiosParams.append('serial-no', text)
            axiosParams.append('ip-address', text)

            if (text === 'COMMERCIAL' || text === 'CLOUD_INSTANCE' || text === 'CLOUD_METERING') {
              axiosParams.append('license-type', text)
            }
          }
          break
      }

      axios.get('/license-service/specific-licenses', { params: axiosParams }).then(res => {
        this.buildLicenseInfo(res.data)
      })
    },
    changeScpOptionText: function (option, sym) {
      if (sym === true) {
        if (option === '0') {
          return 'OFF'
        } else if (option === '1') {
          return 'DEC'
        } else {
          return 'ALL'
        }
      }

      if (option === '0') {
        return 'OFF'
      } else {
        return 'ON'
      }
    },
    changeKmsOptionText: function (option) {
      if (option === '0') {
        return 'OFF'
      } else {
        return 'ON'
      }
    },
    changeStatusString: function (status, language) {
      if (language === 'eng') {
        if (status === 'EXPIRE_LICENSE') {
          return status
        } else if (status === 'TO_EXPIRE_LICENSE') {
          return status
        } else {
          return status
        }
      } else if (language === 'kor') {
        switch (status) {
          case 'REGISTER_COMPONENT':
            return '발급 대기'
          case 'ISSUE_LICENSE':
            return '발급(사용 전)'
          case 'LICENSE_IN_USE':
            return '사용 중'
          case 'UNUSED_LICENSE':
            return '사용 중지'
          case 'EXPIRE_LICENSE':
            return '만료'
          case 'TO_EXPIRE_LICENSE':
            return '만료 예정'
          default:
            break
        }
      } else {

      }
    },
    changeDateString: function (strDate) {
      if (strDate === null) {
        return ''
      }

      const year = strDate.substring(0, 4)
      const month = strDate.substring(4, 6)
      const day = strDate.substring(6, 8)

      return year + '-' + month + '-' + day
    },
    showLicenseIssueView: function () {
      if (this.selectedProducts.length === 0) {
        this.showAlertView('발급 대상 제품을 하나 이상 선택하시기 바랍니다.')
        return false
      }

      this.isIssueViewed = true
      this.$modal.show('LicenseIssuePopup')
    },
    confirmLicenseIssueView: function () {
      this.selectedProducts = []

      this.isIssueViewed = false
      this.$modal.hide('LicenseIssuePopup')
      this.buildAllLicenseInfo()
    },
    closeLicenseIssueView: function () {
      this.isIssueViewed = false
      this.$modal.hide('LicenseIssuePopup')
      this.buildAllLicenseInfo()
    },
    showLicenseDiscardView: function () {
      if (this.selectedProducts.length === 0) {
        this.showAlertView('발급 대상 제품을 하나 이상 선택하시기 바랍니다.')
        return false
      }

      this.isDiscardViewed = true
      this.$modal.show('LicenseDiscardPopup')
    },
    closeLicenseDiscardView: function () {
      this.isDiscardViewed = false
      this.$modal.hide('LicenseDiscardPopup')
    },
    showLicenseDiscardExecuteView: function () {
      this.closeLicenseDiscardView()

      this.isDiscardExeViewed = true
      this.$modal.show('LicenseDiscardExecutePopup')
    },
    closeLicenseDiscardExecuteView: function () {
      this.isDiscardExeViewed = false
      this.$modal.hide('LicenseDiscardExecutePopup')
    },
    confirmLicenseDiscardExecute: function () {
      const jsonLicenseInfoList = []
      for (let i = 0; i < this.selectedProducts.length; i++) {
        const jsonLicenseInfo = {}
        jsonLicenseInfo.serialNo = this.selectedProducts[i].serialNo
        jsonLicenseInfo.status = 'UNUSED_LICENSE'

        jsonLicenseInfoList.push(jsonLicenseInfo)
      }

      axios.delete('/license-service/licenses', {
        headers: {
          'Content-Type': 'application/json'
        },
        data: jsonLicenseInfoList
      }).then(res => {
        if (res.data.result !== 0) {
          this.showAlertView('라이선스 폐기를 실패하였습니다.')
          return
        }

        this.selectedProducts = []
        this.buildAllLicenseInfo()
      }
      ).catch((err) => {
        alert(err)
      })

      this.closeLicenseDiscardExecuteView()
    },
    formatDate: function (date) {
      const d = new Date(date)
      let month = '' + (d.getMonth() + 1)
      let day = '' + d.getDate()
      const year = d.getFullYear()
      if (month.length < 2) month = '0' + month
      if (day.length < 2) day = '0' + day
      return [year, month, day].join('-')
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

.v-data-table thead th {
  font-size: 14px !important;
}
.v-data-table tbody td {
  font-size: 12px !important;
}
</style>
