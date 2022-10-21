<template>
  <div class="delete_modal">
    <div class="delete_modal-card">
      <h2 class="delete_modal-head">계약 정보 삭제</h2>
      <!-- 테이블 영역 -->
      <template>
      <div class="pl20 pr20 pt10">
      <v-card class="fixed_tbl_container mt10 mb10">
        <v-layout column style="width: 1600px;">
          <v-flex style="overflow: auto">
            <v-data-table
              :headers="headers"
              :items="modifyContractList"
              hide-default-header
              hide-default-footer
              :items-per-page=65535
              class="fixed_table w1600 fixed_table_contract_remove"
              v-bind:class="{ 'fixed_table fixed_table_contract_remove_ie' : isBrowserTypeIE }">
              <template v-slot:header="{ props: { headers } }">
                <thead>
                <tr>
                  <th><input type="checkbox" v-model="allContractsSelect"></th>
                  <th v-for="h in headers" :key="h.key_idx" @click="sortContracts(h.value)" v-html="h.text">
                  </th>
                </tr>
              </thead>
              </template>
              <template v-slot:body="{ items }">
                <tbody class="h400">
                  <tr v-for="item in items" :key="item.key_idx">
                  <td><input type="checkbox" v-model="selectedContractInfos" :value="item" ref="CbxComponentInfo" class="cbx"></td>
                    <td>{{ item.cust_nm }}</td>
                    <td>{{ item.order_num }}</td>
                    <td>{{ item.lis_pro_nm }}</td>
                    <td>{{ item.lis_type_oc_up }}</td>
                    <td>{{ item.lis_ver_up }}</td>
                    <td>{{ item.lo_core }}</td>
                    <td>{{ item.instance_nm }}</td>
                    <td>{{ item.cloud_sp }}</td>
                    <td>{{ item.cloud_csi }}</td>
                    <td>{{ item.licenseStartDate }}</td>
                    <td>{{ item.licenseEndDate }}</td>
                    <td v-html="item.scpFunction"
                      v-bind:style="item.scpFunction === '-' ? 'text-align: center; width: 300px;' : 'text-align: left; width: 300px;'">
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

      <div class="mt20 txt_center">
        <input type="button" value="삭제" @click="acceptDelete()" class="btn_basic btn_cancel bg_bluegreen w80 mr10">
        <input type="button" value="닫기" @click="cancelDelete()" class="btn_cancel btn_basic color_indigo btn_outline w80">
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      headers: [
        { text: '고객사명', align: 'center', sortable: true, value: 'cust_nm', width: '150px' },
        { text: '납품 계약 번호', align: 'center', sortable: true, value: 'order_num', width: '150px' },
        { text: '제품명', align: 'center', sortable: true, value: 'lis_pro_nm', width: '100px' },
        { text: '라이선스 타입', align: 'center', sortable: true, value: 'lis_type_oc_up', width: '200px' },
        { text: '라이선스<br>버전', align: 'center', sortable: true, value: 'lis_ver_up', width: '100px' },
        { text: 'Core', align: 'center', sortable: true, value: 'lo_core', width: '100px' },
        { text: '인스턴스명', align: 'center', sortable: true, value: 'instance_nm', width: '100px' },
        { text: 'SP', align: 'center', sortable: true, value: 'cloud_sp', width: '100px' },
        { text: 'CSI', align: 'center', sortable: true, value: 'cloud_csi', width: '100px' },
        { text: '계약 시작일', align: 'center', sortable: true, width: '110px' },
        { text: '계약 만료일', align: 'center', sortable: true, width: '110px' },
        { text: '기능 라이선스', align: 'center', sortable: false, value: 'scpFunction', width: '316px' }
      ],
      selectedContractInfos: [],
      contractList: [],
      currentSortCol: '상태',
      currentSortDir: 'asc',
      isBrowserTypeIE : false
    }
  },
  created () {
    this.buildContractInfo()
    
    if ((navigator.appName === 'Netscape' && navigator.userAgent.search('Trident') !== -1) || (navigator.userAgent.toLowerCase().indexOf('msie') !== -1)) { // IE 일 경우
      this.isBrowserTypeIE = true
    }
  },
  computed: {
    allContractsSelect: {
      get: function () {
        if (this.modifyContractList.length === 0) {
          return false
        }

        return this.modifyContractList.length === this.selectedContractInfos.length
      },
      set: function (e) {
        this.selectedContractInfos = []

        if (e) {
          this.modifyContractList.forEach((contract) => {
            this.selectedContractInfos.push(contract)
          })
        }
      }
    },
    modifyContractList: function () {
      return this.contractList.map((obj) => Object.assign({}, obj, {
        licenseStartDate: obj.esti_type === '10' ? obj.lis_s_date : obj.ins_s_dt,
        licenseEndDate: obj.esti_type === '10' ? obj.lis_e_date : obj.ins_e_dt,
        scpFunction: obj.lis_pro_nm === 'BA-SCP' ? '대칭키 암복호화 API : ' + this.changeScpOptionText(obj.sym_api_yn, true) + '<br>' +
                    '비대칭키 암복호화 API : ' + this.changeScpOptionText(obj.pub_api_yn, false) + '<br>' +
                    'KE-WIN 복호화 API : ' + this.changeScpOptionText(obj.kewin_api_yn, false) + '<br>' +
                    'KMS 외부키 연동 : ' + this.changeScpOptionText(obj.kms_yn, false) + '<br>' +
                    'OS 계정별 암복호화 권한 제어 : ' + this.changeScpOptionText(obj.os_yn, false) : obj.lis_pro_nm === 'SG-KMS' || obj.lis_pro_nm === 'SG-KMS_SA' ? '암호화 및 BA-SCP 연동 : ' +
                    this.changeKmsOptionText(obj.kms_enc) + '<br>' +
                    'SCP 외부키 연동 : ' + this.changeKmsOptionText(obj.kms_pub_key) + '<br>' +
                    '비공개키 연동 : ' + this.changeKmsOptionText(obj.kms_pri_key) : obj.lis_pro_nm === 'WAPPLES' ? '보호 대상 웹서버 등록수 제한 : ' + obj.web_cnt + '<br>' +
                    '라이선스 만료시 대응 설정 : ' + obj.lis_exp_act + '<br>' : '-'
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
      axios.get('/license-service/contracts', {
        params: {
          'current-page-no': 1, 'records-per-page': 65535
        }
      }).then(
        res => {
          this.contractList = res.data.contractInfoList
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
    acceptDelete: function () {
      if (this.selectedContractInfos.length === 0) {
        this.$emit('show-alert-modal', '삭제 대상 계약 정보를 하나 이상 선택하시기 바랍니다.')
        return
      }

      const jsonContractInfoList = []
      for (let i = 0; i < this.selectedContractInfos.length; i++) {
        const jsonContractInfo = {}
        jsonContractInfo.key_idx = this.selectedContractInfos[i].key_idx
        jsonContractInfo.cust_nm = this.selectedContractInfos[i].cust_nm
        jsonContractInfo.lis_pro_nm = this.selectedContractInfos[i].lis_pro_nm
        jsonContractInfo.order_num = this.selectedContractInfos[i].order_num

        jsonContractInfoList.push(jsonContractInfo)
      }

      axios.delete('/license-service/contracts', {
        headers: {
          'Content-Type': 'application/json'
        },
        data: jsonContractInfoList
      }).then(res => {
        if (res.data.result !== 0) {
          this.$emit('show-alert-modal', '계약 정보 삭제를 실패하였습니다. 사용 중인 제품이 있는지 확인하세요.')
          return
        }

        this.selectedContractInfos = []
        this.$emit('close-modal')
      }
      ).catch((err) => {
        alert(err)
      })
    },
    cancelDelete: function () {
      this.$emit('close-modal')
    }
  }
}
</script>

<style scoped>
.delete_modal {
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
}
.delete_modal-card {
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
  overflow-y: auto
}
.delete_modal-head {
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
