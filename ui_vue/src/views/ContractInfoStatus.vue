<template>
<div class="bg_contents">
  <meta http-equiv="pragma" content="no-cache" />
  <div class="container">
    <!-- 페이지 타이틀  -->
    <section class="pagetitle clearfix">
        <div class="f_left">
            <h2 class="inline_block mr10">계약 정보 관리</h2>
            <p class="inline_block">계약 정보 추가/삭제 및 상세 계약 정보를 확인할 수 있습니다.</p>
        </div>
        <div class="section_title">
            <a @click="this.buildContractInfo"><img src="../static/img/icon_refresh.png" class="icon_refresh mt7" style="width:13px"></a>
        </div>
        <div class="f_right txt_right mr10">
            <p class="inline_block">Home / <span>계약 정보 관리</span></p>
        </div>
    </section>

    <!-- 계약 정보 관리  -->
    <section class="section_block">
      <div class="section_contents">
        <!-- 전체 컨텐츠 영역 -->
        <template>
        <v-card class="fixed_tbl_container mt10 mb10">
          <v-layout column style="width: 1570px;">
            <v-data-table
              :headers="headers"
              :items="modifyContractInfoList"
              hide-default-header
              hide-default-footer
              :items-per-page=65535
              item-key="key_idx"
              class="fixed_table fixed_table_contract_table"
              v-bind:class="{ 'fixed_table fixed_table_contract_table_ie' : isBrowserTypeIE }">
              <template v-slot:header="{ props: { headers } }">
                  <thead>
                  <tr>
                    <th v-for="h in headers" :key="h.key_idx" @click="sortContracts(h.value)" v-html="h.text">
                    </th>
                  </tr>
                </thead>
              </template>
              <template v-slot:body="{ items }">
                <tbody class="h500">
                  <tr v-for="item in items" :key="item.key_idx">
                    <td>{{ item.custNm }}</td>
                    <td>{{ item.orderNum }}</td>
                    <td>{{ item.lisProNm }}</td>
                    <td>{{ item.lisTypeOcUp }}</td>
                    <td>{{ item.lisVerUp }}</td>
                    <td>{{ item.cpuCount }}</td>
                    <td>{{ item.instanceNm }}</td>
                    <td v-html="item.cloudParam" v-bind:style="item.cloudParam === '-' ? 'text-align: center;' : 'text-align: left;'">
                      {{ item.cloudParam }}</td>
                    <td>{{ item.ticketCount }}</td>
                    <td>{{ item.startDate }}</td>
                    <td>{{ item.endDate }}</td>
                    <td v-html="item.scpFunction" v-bind:style="item.scpFunction === '-' ? 'text-align: center;' : 'text-align: left;'">
                      {{ item.scpFunction }}</td>
                  </tr>
                </tbody>
              </template>
            </v-data-table>
          </v-layout>
        </v-card>
        </template>

        <!--  팝업 창 : 계약 정보 삭제  -->
        <modal name="ContractDeletePopup"
          :draggable="true"
          :resizable="true"
          @closed="closeContractDeleteView()"
          :width="1000"
          :height="600"
          :max-width="1650"
          :max-height="600">
          <ContractDeletePopup
            v-if="isDeleteViewed"
            @show-alert-modal="showAlertView"
            @close-modal="closeContractDeleteView()">
          </ContractDeletePopup>
        </modal>

        <!--  팝업 창 : 계약 정보 불러오기 비밀번호  -->
        <modal name="ContractPasswordPopup"
          :draggable="true"
          @closed="closeContractPasswordView()"
          :width="250"
          :height="150">
          <ContractPasswordPopup
            v-if="isPasswordViewed"
            :encContractFile="encContractFile"
            :fileName="fileName"
            @ok="inputContractFilePassword($event)"
            @show-alert-modal="showAlertView"
            @close-modal="closeContractPasswordView()">
          </ContractPasswordPopup>
        </modal>

        <!--  팝업 창 : 계약 정보 파일 추가  -->
        <modal name="ContractFileLoadPopup"
          :draggable="true"
          :resizable="true"
          @closed="closeContractAddView()"
          :width="1000"
          :height="600"
          :max-width="1650"
          :max-height="600">
          <ContractFileLoadPopup
            v-if="isLoadContractFileViewed"
            :contractList="contractList"
            @show-alert-modal="showAlertView"
            @close-modal="closeContractAddView()">
          </ContractFileLoadPopup>
        </modal>

        <!-- 버튼 영역 -->
        <div class="txt_center pt20">
            <input type="button" value="추가" @click="addContractFile()" class="btn_contract_add btn_basic bg_bluegreen w110">
            <input type="button" value="삭제" @click="showContractDeleteView()" class="btn_basic color_indigo btn_outline w110 ml10">

            <input type="file" ref="fileSelector" @change="callLoadFile($event)" accept=".eci" style="display: none">
            <input type="hidden" ref="filePassword" class="pw_file_password">
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
import ContractDeletePopup from './Contract/ContractDeletePopup'
import ContractPasswordPopup from './Contract/ContractPasswordPopup'
import ContractFileLoadPopup from './Contract/ContractFileLoadPopup'
import AlertPopup from './Component/AlertPopup.vue'

export default {
  components: {
    ContractDeletePopup,
    ContractPasswordPopup,
    ContractFileLoadPopup,
    AlertPopup
  },
  data () {
    return {
      headers: [
        { text: '고객사명', align: 'center', sortable: true, value: 'custNm', width: '10%' },
        { text: '납품 계약 번호', align: 'center', sortable: true, value: 'orderNum', width: '10%' },
        { text: '제품명', align: 'center', sortable: true, value: 'lisProNm', width: '5%' },
        { text: '라이선스 타입', align: 'center', sortable: true, value: 'lisTypeOcUp', width: '10%' },
        { text: '라이선스 버전', align: 'center', sortable: true, value: 'lisVerUp', width: '5%' },
        { text: 'Core', align: 'center', sortable: true, value: 'cpuCount', width: '5%' },
        { text: '인스턴스명', align: 'center', sortable: true, value: 'instanceNm', width: '5%' },
        { text: '클라우드 파라미터', align: 'center', sortable: true, width: '10%' },
        { text: '티켓 수량', align: 'center', sortable: true, width: '10%' },
        { text: '계약 시작일', align: 'center', sortable: true, value: 'startDate', width: '7%' },
        { text: '계약 만료일', align: 'center', sortable: true, value: 'endDate', width: '7%' },
        { text: '기능 라이선스', align: 'center', sortable: false, width: '30%' }
      ],
      selectedContractInfos: [],
      contractInfoList: [],
      currentSortCol: '상태',
      currentSortDir: 'asc',
      isDeleteViewed: false,
      isPasswordViewed: false,
      isLoadContractFileViewed: false,
      encContractList: '',
      encContractFile: '',
      contractList: [],
      fileName: '',
      isBrowserTypeIE : false,
      isAlertViewed: false
    }
  },
  created () {
    this.buildContractInfo()

    if ((navigator.appName === 'Netscape' && navigator.userAgent.search('Trident') !== -1) || (navigator.userAgent.toLowerCase().indexOf('msie') !== -1)) { // IE 일 경우
      this.isBrowserTypeIE = true
    }
  },
  computed: {
    modifyContractInfoList: function () {
      return this.contractInfoList.map((obj) => Object.assign({}, obj, {
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
      this.contractInfoList.sort(this.sortBy(col, this.currentSortDir))
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
      this.contractInfoList = []

      axios.get('/license-service/contracts/license/contract-register-group-info').then(res => {
        this.contractInfoList = res.data.contractRegisterGroupInfoList
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
    addContractFile: function () {
      this.$refs.fileSelector.click()
    },
    callLoadFile: function (event) {
      const reader = new FileReader()
      const file = event.target.files[0]

      reader.readAsText(file, 'UTF-8')
      reader.onload = (event) => {
        try {
          this.encContractList = reader.result
          this.fileName = file.name

          this.encContractFile = atob(this.encContractList)
        } catch (e) {
          this.showAlertView('eci 형식에 맞는 파일이 아닙니다.')
          this.$refs.fileSelector.value = null
          return
        }

        this.showContractPasswordView()
        this.$refs.fileSelector.value = null
      }
    },
    showContractAddView: function () {
      this.isLoadContractFileViewed = true
      this.$modal.show('ContractFileLoadPopup')
    },
    closeContractAddView: function () {
      this.isLoadContractFileViewed = false
      this.$modal.hide('ContractFileLoadPopup')
      this.buildContractInfo()
    },
    showContractPasswordView: function () {
      this.isPasswordViewed = true
      this.$modal.show('ContractPasswordPopup')
    },
    closeContractPasswordView: function () {
      this.isPasswordViewed = false
      this.$modal.hide('ContractPasswordPopup')
    },
    inputContractFilePassword: function (value) {
      this.contractList = value
      this.closeContractPasswordView()
      this.showContractAddView()
    },
    showContractDeleteView: function () {
      this.isDeleteViewed = true
      this.$modal.show('ContractDeletePopup')
    },
    closeContractDeleteView: function () {
      this.isDeleteViewed = false
      this.$modal.hide('ContractDeletePopup')
      this.buildContractInfo()
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
@import '../static/css/jquery-ui-1.12.1.css';

.v-data-table thead th {
  font-size: 14px !important;
}
.v-data-table tbody td {
  font-size: 12px !important;
}
</style>
