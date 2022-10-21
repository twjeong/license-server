<template>
  <div class="issue_modal">
    <div class="issue_modal-card">
      <h2 class="issue_modal-head">라이선스 발급</h2>
      <!-- 테이블 영역 -->
      <template>
      <div class="pl20 pr20 pt10">
      <v-card class="fixed_tbl_container mt10 mb10">
        <v-layout column style="width: 1970px;">
          <v-flex style="overflow: auto">
            <v-data-table
              :headers="headers"
              :items="modifyContractList"
              hide-default-header
              hide-default-footer
              :items-per-page=65535
              class="fixed_table W2600 fixed_table_contract_info"
              v-bind:class="{ 'fixed_table fixed_table_contract_info_ie' : isBrowserTypeIE }">
              <template v-slot:header="{ props: { headers } }">
                <thead>
                <tr>
                  <th v-for="h in headers" :key="h.serialNo" @click="sortContracts(h.value)" v-html="h.text">
                  </th>
                </tr>
              </thead>
              </template>
              <template v-slot:body="{ items }">
                <tbody class="h400">
                  <tr v-for="item in items" :key="item.id" @click="clickContractInfoTable(item)" :class="{'on': item.id === selectedId}">
                    <td>{{ item.custNm }}</td>
                    <td>{{ item.orderNum }}</td>
                    <td>{{ item.lisProNm }}</td>
                    <td>{{ item.lisTypeOcUp }}</td>
                    <td>{{ item.lisVerUp }}</td>
                    <td>{{ item.cpuCount }}</td>
                    <td>{{ item.instanceNm }}</td>
                    <td v-html="item.cloudParam" v-bind:style="item.cloudParam === '-' ? 'text-align: center;' : 'text-align: left;'">
                      {{ item.cloudParam }}</td>
                    <td v-html="item.ticketCount"></td>
                    <td>{{ item.startDate }}</td>
                    <td>{{ item.endDate }}</td>
                    <td v-html="item.scpFunction" v-bind:style="item.scpFunction === '-' ? 'text-align: center;' : 'text-align: left;'">
                      {{ item.scpFunction }}</td>
                  </tr>
                </tbody>
              </template>
            </v-data-table>
          </v-flex>
        </v-layout>
      </v-card>
      </div>
      </template>

      <div class="mt15 txt_center">
        <input type="button" value="적용" @click="acceptIssue()" class="btn_basic btn_cancel bg_bluegreen w80 mr10">
        <input type="button" value="취소" @click="cancelIssue()" class="btn_cancel btn_basic color_indigo btn_outline w80">
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  props: {
    selectedProducts: {
      type: Array
    }
  },
  data () {
    return {
      headers: [
        { text: '고객사명', align: 'center', sortable: true, value: 'custNm', width: '200px' },
        { text: '납품 계약 번호', align: 'center', sortable: true, value: 'orderNum', width: '200px' },
        { text: '제품명', align: 'center', sortable: true, value: 'lisProNm', width: '100px' },
        { text: '라이선스 타입', align: 'center', sortable: true, value: 'lisTypeOcUp', width: '200px' },
        { text: '라이선스 버전', align: 'center', sortable: true, value: 'lisVerUp', width: '100px' },
        { text: 'Core', align: 'center', sortable: true, value: 'cpuCount', width: '100px' },
        { text: '인스턴스명', align: 'center', sortable: true, value: 'instanceNm', width: '120px' },
        { text: '클라우드 파라미터', align: 'center', sortable: true, value: 'cloudCsi', width: '200px' },
        { text: '티켓 수량<br>(발급 가능 / 총)', align: 'center', sortable: true, value: 'cpuCount', width: '150px' },
        { text: '계약 시작일', align: 'center', sortable: true, value: 'startDate', width: '150px' },
        { text: '계약 만료일', align: 'center', sortable: true, value: 'endDate', width: '150px' },
        { text: '기능<br>라이선스', align: 'center', sortable: false, value: 'scpFunction', width: '300px' }
      ],
      selectedId: -1,
      selectedContractInfo: '',
      contractList: [],
      currentSortCol: '상태',
      currentSortDir: 'asc',
      isBrowserTypeIE: false
    }
  },
  created () {
    this.buildContractInfo()

    if ((navigator.appName === 'Netscape' && navigator.userAgent.search('Trident') !== -1) || (navigator.userAgent.toLowerCase().indexOf('msie') !== -1)) { // IE 일 경우
      this.isBrowserTypeIE = true
    }
  },
  computed: {
    modifyContractList: function () {
      let count = 1

      return this.contractList.map((obj) => Object.assign({}, obj, {
        id: count++,
        cloudParam: obj.lisTypeOcUp === 'CLOUD_INSTANCE' ? 'SP : ' + obj.cloudSp + '<br>' +
                    'CSI : ' + obj.cloudCsi : '-',
        ticketCount: obj.lisTypeOcUp === 'CLOUD_METERING' ? '-' : obj.availableCount + ' / ' + obj.allCount,
        scpFunction: obj.lisProNm === 'BA-SCP' ? '대칭키 암복호화 API : ' + this.changeScpOptionText(obj.symApiYn, true) + '<br>' +
                    '비대칭키 암복호화 API : ' + this.changeScpOptionText(obj.pubApiYn, false) + '<br>' +
                    'KE-WIN 복호화 API : ' + this.changeScpOptionText(obj.kewinApiYn, false) + '<br>' +
                    'KMS 외부키 연동 : ' + this.changeScpOptionText(obj.kmsYn, false) + '<br>' +
                    'OS 계정별 암복호화 권한 제어 : ' + this.changeScpOptionText(obj.osYn, false) : obj.lisProNm === 'SG-KMS' || obj.lisProNm === 'SG-KMS_SA' ? '암호화 및 BA-SCP 연동 : ' +
                    this.changeKmsOptionText(obj.kmsEnc) + '<br>' +
                    'SCP 외부키 연동 : ' + this.changeKmsOptionText(obj.kmsPubKey) + '<br>' +
                    '비공개키 연동 : ' + this.changeKmsOptionText(obj.kmsPriKey) : obj.lisProNm === 'WAPPLES' ? '보호 대상 웹서버 등록수 제한 : ' + obj.webCnt + '<br>' +
                    '라이선스 만료시 대응 설정 : ' + obj.lisExpAct + '<br>' : '-'
      }))
    }
  },
  methods: {
    sortContracts: function (col) {
      if (this.currentSortCol === col) {
        this.currentSortDir = this.currentSortDir === 'asc' ? 'desc' : 'asc'
      } else {
        this.currentSortCol = col
      }
      this.contractList.sort(this.sortBy(col, this.currentSortDir))
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
    buildContractInfo: function () {
      axios.get('/license-service/contracts/license/contract-register-group-info').then(
        res => {
          this.contractList = res.data.contractRegisterGroupInfoList
        })
    },
    clickContractInfoTable: function (value) {
      this.selectedId = value.id
      this.selectedContractInfo = value
    },
    acceptIssue: function () {
      const today = new Date()
      const dateTime = today.toISOString()
      const sessionLicenseIssueDate = this.formatDate(today).split('-').join('')

      for (let i = 0; i < this.selectedProducts.length; i++) {
        if (this.selectedProducts[i].componentName !== this.selectedContractInfo.lisProNm) {
          this.$emit('show-alert-modal', '발급하려는 라이선스와 대상 제품명이 일치하지 않습니다.')
          return false
        }
      }

      /* 라이선스 발급 수량 체크 */
      let remainTicketCount = 0
      for (let i = 0; i < this.contractList.length; i++) {
        if (this.contractList[i].orderNum === this.selectedContractInfo.orderNum &&
          this.contractList[i].custNm === this.selectedContractInfo.custNm &&
          this.contractList[i].lisProNm === this.selectedContractInfo.lisProNm &&
          this.contractList[i].lisTypeOcUp === this.selectedContractInfo.lisTypeOcUp) {

          remainTicketCount = this.contractList[i].availableCount
        }
      }

      if (this.selectedContractInfo.lisTypeOcUp !== 'CLOUD_METERING') {
        if (remainTicketCount < this.selectedProducts.length) {
          this.$emit('show-alert-modal', '발급 가능한 티켓 수가 부족합니다.')
          return
        }
      }

      const jsonLicenseInfoList = []
      for (let i = 0; i < this.selectedProducts.length; i++) {
        let jsonLicenseInfo = {}
        jsonLicenseInfo = JSON.parse(JSON.stringify(this.selectedContractInfo))
        jsonLicenseInfo.serialNo = this.selectedProducts[i].serialNo
        jsonLicenseInfo.firstUsageTime = dateTime
        jsonLicenseInfo.licenseIssueDate = sessionLicenseIssueDate
        jsonLicenseInfo.licenseStartDate = this.selectedContractInfo.startDate.replace(/-/gi, '')
        jsonLicenseInfo.licenseEndDate = this.selectedContractInfo.endDate.replace(/-/gi, '')
        jsonLicenseInfo.status = 'ISSUE_LICENSE'

        jsonLicenseInfoList.push(jsonLicenseInfo)
      }

      return axios.put('/license-service/licenses', jsonLicenseInfoList).then(
        res => {
          if (res.data.result !== 0) {
            this.$emit('show-alert-modal', '라이선스 발급을 실패하였습니다.')
            return
          }

          this.$emit('ok')
        }
      ).catch((err) => {
        alert(err)
      })
    },
    cancelIssue: function () {
      this.$emit('close-modal')
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
    formatDate: function (date) {
      const d = new Date(date)
      let month = '' + (d.getMonth() + 1)
      let day = '' + d.getDate()
      const year = d.getFullYear()
      if (month.length < 2) month = '0' + month
      if (day.length < 2) day = '0' + day
      return [year, month, day].join('-')
    }
  }
}
</script>

<style scoped>
.issue_modal {
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
}
.issue_modal-card {
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
  overflow-y: auto
}
.issue_modal-head {
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
.v-data-table thead th {
  font-size: 14px !important;
}
.v-data-table tbody td {
  font-size: 12px !important;
}
</style>
