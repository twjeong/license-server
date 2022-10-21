<template>
<div class="bg_contents">
  <div class="container">
    <!-- 페이지 타이틀  -->
    <section class="pagetitle clearfix">
      <div class="f_left">
        <h2 class="inline_block mr10">로그</h2>
        <p class="inline_block">로그 조회 및 로그 백업/복원을 진행할 수 있습니다.</p>
      </div>
      <div class="section_title">
        <a @click="searchLog(1)"><img src="../static/img/icon_refresh.png" class="icon_refresh mt7" style="width:13px"></a>
      </div>
      <div class="f_right txt_right mr10">
        <p class="inline_block">Home / <span>로그</span></p>
      </div>
    </section>

    <!-- 로그  -->
    <section class="section_block">
      <div class="section_title clearfix">
        <div class="layout-2-right txt_right pr20">
          <p class="inline_block mr10">
            <label ref="beginDateTime"></label>
            <label style="margin-left: 2px; margin-right: 2px;">~</label>
            <label ref="endDateTime"></label>
          </p>
          <a href="#" @click="showLogDateSettingView" data-tooltip="시간 설정"><img src="../static/img/icon_setup.png"></a>
        </div>
      </div>

      <div class="section_contents">
        <!-- 검색 결과 및 조건 영역 -->
        <div class="clearfix">
          <!-- 검색 결과  -->
          <div class="layout-2-left">
            <div class="pt10">
              <div class="rbtn mr10"><input type="radio" id="rvs" v-model="log_option" v-on:change="searchLog(1)" value="rvs"><label for="rvs">
                  시스템</label></div>
              <div class="rbtn mr10"><input type="radio" id="rve" v-model="log_option" v-on:change="searchLog(1)" value="rve"><label for="rve">
                  이벤트</label></div>
              <div class="rbtn mr10"><input type="radio" id="rvp" v-model="log_option" v-on:change="searchLog(1)" value="rvp"><label for="rvp">
                  정책</label></div>
            </div>
          </div>

          <!-- 검색 입력  -->
          <div class="layout-2-right txt_right">
            <div class="select_menu w100">
              <select id="logSearchOption" required>
                <option value="" selected>전체</option>
                <option value="level">레벨</option>
                <option value="msg">상세 설명</option>
              </select>
            </div>
            <input title="주의! > 두 단어 이상은 검색되지 않습니다."
                    type="text" id="logSearchText" placeholder="검색 조건을 입력하세요." @keyup.enter="searchLog(1)" class="w200 ml5">
            <input type="submit" @click="searchLog(1)" value="검색" class="btn_small bg_dark w60 ml5">
          </div>
        </div>

        <!-- 테이블 영역 -->
        <div class="mt10 mb20">
          <table class="table_basic">
            <thead style="border-top: 1mm solid #333;">
              <tr>
                <th @click="sortLog('facility');" style="width:10%">Manager</th>
                <th @click="sortLog('logLevel');" style="width:10%">레벨</th>
                <th @click="sortLog('createTime');" style="width:20%">시간</th>
                <th @click="sortLog('msg');" style="width:60%">상세 설명</th>
              </tr>
            </thead>
            <tbody id="logTable">
            </tbody>
          </table>
        </div>

        <!-- Page Navigatoin -->
        <div class="pageNavi">
          <div id="pageNum">
            <a href="#" @click="searchLog(1)"><img src="../static/img/icon_arrow_first_page.png" class="arr"></a>
            <a href="#" @click="searchLog(prevPageNum)"><img src="../static/img/icon_arrow_left.png" class="arr_pre">이전</a>
            <a href="#" v-for="page in pageList"
              :key="page.href"
              :class="{'on': page.number === curPageNum}"
              @click="searchLog(page.number)">
              {{ page.number }}
            </a>
            <a href="#" class="next" @click="searchLog(nextPageNum)">다음<img src="../static/img/icon_arrow_right.png" class="arr_next"></a>
            <a href="#" @click="searchLog(lastPageNum)"><img src="../static/img/icon_arrow_last_page.png" class="arr"></a>
          </div>
        </div>
      </div>

      <div class="wrap_cont">
        <div class="mb15 txt_center">
          <input type="button" value="주기적 로그 백업" @click="showLogBackupSettingView()" class="btn_basic btn_cancel bg_bluegreen w130 mr10">
          <input type="button" value="즉시 로그 백업" @click="showLogBackupNowExecuteView()" class="btn_cancel btn_basic bg_bluegreen w130 mr10">

          <input type="button" value="로그 복원" @click="showLogRestoreFileSelect()" class="btn_basic btn_cancel color_indigo btn_outline w130 mr10">
        </div>
      </div>
    </section>

    <!-- 팝업 창  : 시간 설정 -->
    <modal name="LogDateSettingPopup"
      :draggable="true"
      @closed="closeLogDateSettingView()"
      :width="360"
      :height="280">
      <LogDateSettingPopup
        v-if="isLogDateSettingViewed"
        :agoDate="agoDate"
        :agoTime="agoTime"
        :currentDate="currentDate"
        :currentTime="currentTime"
        @ok="searchLogDateSettingView($event)"
        @show-alert-modal="showAlertView"
        @close-modal="closeLogDateSettingView()">
      </LogDateSettingPopup>
    </modal>

    <!-- 팝업 창  : 주기적 로그 백업 설정 -->
    <modal name="LogBackupSettingPopup"
      :draggable="true"
      @closed="closeLogBackupSettingView()"
      :width="700"
      :height="340">
      <LogBackupSettingPopup
        v-if="isLogBackupSettingViewed"
        @show-alert-modal="showAlertView"
        @close-modal="closeLogBackupSettingView()">
      </LogBackupSettingPopup>
    </modal>

    <!-- 팝업 창  : 즉시 로그 백업 실행 여부 문의 -->
    <modal name="LogBackupNowExecutePopup"
      :draggable="true"
      @closed="closeLogBackupNowExecuteView()"
      :width="300"
      :height="150">
      <LogBackupNowExecutePopup
        v-if="isLogBackupNowExecuteViewed"
        @ok="execLogBackup()"
        @close-modal="closeLogBackupNowExecuteView()">
      </LogBackupNowExecutePopup>
    </modal>

    <!-- 팝업 창  : 즉시 로그 백업 -->
    <modal name="LogBackupNowPopup"
      :draggable="true"
      @closed="closeLogBackupNowView()"
      :width="250"
      :height="150">
      <LogBackupNowPopup
        v-if="isLogBackupNowViewed"
        @close-modal="closeLogBackupNowView()">
      </LogBackupNowPopup>
    </modal>

    <!-- 팝업 창  : 로그 복원 파일 선택 -->
    <modal name="LogRestoreFileSelectPopup"
      :draggable="true"
      @closed="closeLogRestoreFileSelect()"
      :width="400"
      :height="500">
      <LogRestoreFileSelectPopup
        v-if="isLogRestoreFileSelectViewed"
        @ok="showLogRestoreView()"
        @show-alert-modal="showAlertView"
        @close-modal="closeLogRestoreFileSelect()">
      </LogRestoreFileSelectPopup>
    </modal>

    <!-- 팝업 창  : 로그 복원 완료 -->
    <modal name="LogRestorePopup"
      :draggable="true"
      @closed="closeLogResotreView()"
      :width="250"
      :height="150">
      <LogRestorePopup
        v-if="isLogRestoreViewed"
        @close-modal="closeLogResotreView()">
      </LogRestorePopup>
    </modal>
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
import LogDateSettingPopup from './Log/LogDateSettingPopup'
import LogBackupSettingPopup from './Log/LogBackupSettingPopup'
import LogBackupNowPopup from './Log/LogBackupNowPopup'
import LogBackupNowExecutePopup from './Log/LogBackupNowExecutePopup'
import LogRestoreFileSelectPopup from './Log/LogRestoreFileSelectPopup'
import LogRestorePopup from './Log/LogRestorePopup'
import AlertPopup from './Component/AlertPopup.vue'

export default {
  components: {
    LogDateSettingPopup,
    LogBackupSettingPopup,
    LogBackupNowPopup,
    LogBackupNowExecutePopup,
    LogRestoreFileSelectPopup,
    LogRestorePopup,
    AlertPopup
  },
  data () {
    return {
      headers: [
        { text: 'Manager', align: 'center', sortable: true, value: 'facility', width: '10%' },
        { text: '레벨', align: 'center', sortable: true, value: 'logLevel', width: '10%' },
        { text: '시간', align: 'center', sortable: true, value: 'createTime', width: '20%' },
        { text: '상세 설명', align: 'center', sortable: true, value: 'msg', width: '60%' }
      ],
      isLogDateSettingViewed: false,
      isLogBackupSettingViewed: false,
      isLogBackupNowExecuteViewed: false,
      isLogBackupNowViewed: false,
      isLogRestoreFileSelectViewed: false,
      isLogRestoreViewed: false,
      isAlertViewed: false,
      searchCurrentPageNum: 1,
      searchCreateTimeBegin: '',
      searchCreateTimeEnd: '',
      searchLogOption: '',
      searchLogText: '',
      currentSortColumn: 'createTime',
      sortColumn: 'createTime',
      flagSortAsc: 'false',
      currentDate: '',
      currentTime: '',
      agoDate: '',
      agoTime: '',
      sortManagerAsc: 'false',
      sortLevelAsc: 'false',
      sortMsgAsc: 'false',
      sortCreateTimeAsc: 'false',
      prevPageNum: 1,
      curPageNum: 1,
      nextPageNum: 2,
      lastPageNum: 1,
      pageList: [],
      log_option: 'rve'
    }
  },
  mounted () {
    const currentToday = new Date()

    this.currentDate = this.leadingZeros(currentToday.getFullYear(), 4) + '-' + this.leadingZeros(currentToday.getMonth() + 1, 2) + '-' +
     this.leadingZeros(currentToday.getDate(), 2)
    this.currentTime = this.leadingZeros(currentToday.getHours(), 2) + ':' + this.leadingZeros(currentToday.getMinutes(), 2) + ':' +
     this.leadingZeros(currentToday.getSeconds(), 2)

    const aYearAgoDay = new Date()
    const aYearAgoDate = aYearAgoDay.getTime() - (365 * 24 * 60 * 60 * 1000)
    aYearAgoDay.setTime(aYearAgoDate)
    this.agoDate = this.leadingZeros(aYearAgoDay.getFullYear(), 4) + '-' + this.leadingZeros(aYearAgoDay.getMonth() + 1, 2) + '-' +
     this.leadingZeros(aYearAgoDay.getDate(), 2)
    this.agoTime = this.leadingZeros(aYearAgoDay.getHours(), 2) + ':' + this.leadingZeros(aYearAgoDay.getMinutes(), 2) + ':' +
     this.leadingZeros(aYearAgoDay.getSeconds(), 2)

    this.searchCreateTimeBegin = this.agoDate + ' ' + this.agoTime
    this.searchCreateTimeEnd = this.currentDate + ' ' + this.currentTime

    this.$refs.beginDateTime.innerText = this.searchCreateTimeBegin
    this.$refs.endDateTime.innerText = this.searchCreateTimeEnd

    this.searchLogOption = document.getElementById('logSearchOption').value
    this.searchLogText = document.getElementById('logSearchText').value

    this.searchLog(1)
  },
  watch: {
    searchCreateTimeBegin: function () {
      this.$refs.beginDateTime.innerText = this.searchCreateTimeBegin
    },
    searchCreateTimeEnd: function () {
      this.$refs.endDateTime.innerText = this.searchCreateTimeEnd
    }
  },
  methods: {
    searchLog: function (pageNum, sortColumn) {
      this.searchCurrentPageNum = pageNum
      if (this.sortColumn === undefined) {
        this.sortColumn = this.currentSortColumn
      }

      switch (sortColumn) {
        case 'facility':
          this.flagSortAsc = this.sortManagerAsc
          break

        case 'logLevel':
          this.flagSortAsc = this.sortLevelAsc
          break

        case 'msg':
          this.flagSortAsc = this.sortMsgAsc
          break

        default:
          this.flagSortAsc = this.sortCreateTimeAsc
          break
      }

      const axiosParams = new URLSearchParams()
      axiosParams.append('page', this.searchCurrentPageNum)
      axiosParams.append('create-time-begin', this.searchCreateTimeBegin)
      axiosParams.append('create-time-end', this.searchCreateTimeEnd)
      axiosParams.append('sort-column', this.sortColumn)
      axiosParams.append('flag-sort-asc', this.flagSortAsc)

      if (this.log_option === 'rvs') {
        axiosParams.append('log-type', 'system')
      } else if (this.log_option === 'rvp') {
        axiosParams.append('log-type', 'policy')
      } else {
        axiosParams.append('log-type', 'event')
      }

      const searchLogOption = document.getElementById('logSearchOption').value
      const searchLogText = document.getElementById('logSearchText').value

      if (searchLogText.length > 256) {
        this.showAlertView('검색 최대 입력 길이를 초과하였습니다.')
        return
      }

      if (searchLogOption === 'level') {
        if (searchLogText.length !== 0) {
          if (searchLogText === 'INFO' || searchLogText === 'WARNING' || searchLogText === 'ERROR') {
            axiosParams.append('log-level', searchLogText)
          } else {
            const emptyLogList = {
              logList: []
            }

            document.getElementById('logTable').innerHTML = ''
            return this.buildHtmlTable('#logTable', emptyLogList)
          }
        }
      } else if (searchLogOption === 'msg') {
        if (searchLogText.length !== 0) {
          axiosParams.append('msg', searchLogText)
        }
      } else {
        if (searchLogText.length !== 0) {
          axiosParams.append('log-level', searchLogText)
          axiosParams.append('msg', searchLogText)
        }
      }

      axios.get('/license-service/logs', { params: axiosParams }).then(
        res => {
          document.getElementById('logTable').innerHTML = ''

          this.pageList = []
          this.prevPageNum = res.data.prevPageNum
          this.curPageNum = res.data.curPageNum
          this.nextPageNum = res.data.nextPageNum
          this.lastPageNum = res.data.lastPageNum

          for (let i = 0; i < res.data.pageList.length; i++) {
            this.pageList.push({
              number: res.data.pageList[i]
            })
          }

          if (res.data.logList !== null) {
            this.buildHtmlTable('#logTable', res.data)
          }
        })
    },
    sortLog: function (sortColumn) {
      this.currentSortColumn = sortColumn

      switch (sortColumn) {
        case 'facility':
          if (this.sortManagerAsc) {
            this.sortManagerAsc = false
          } else {
            this.sortManagerAsc = true
          }

          this.searchLog(this.searchCurrentPageNum, sortColumn)
          break

        case 'logLevel':
          if (this.sortLevelAsc) {
            this.sortLevelAsc = false
          } else {
            this.sortLevelAsc = true
          }
          this.searchLog(this.searchCurrentPageNum, sortColumn)
          break

        case 'createTime':
          if (this.sortCreateTimeAsc) {
            this.sortCreateTimeAsc = false
          } else {
            this.sortCreateTimeAsc = true
          }
          this.searchLog(this.searchCurrentPageNum, sortColumn)
          break

        case 'msg':
          if (this.sortMsgAsc) {
            this.sortMsgAsc = false
          } else {
            this.sortMsgAsc = true
          }
          this.searchLog(this.searchCurrentPageNum, sortColumn)
          break

        default:
          break
      }
    },
    buildHtmlTable: function (selector, logData) {
      for (let i = 0; i < logData.logList.length; i++) {
        const row = $('<tr/>')

        row.append($('<td style="font-size: 12px;"/>').html(logData.logList[i].facility))
        row.append($('<td style="font-size: 12px;"/>').html(logData.logList[i].logLevel))
        row.append($('<td style="font-size: 12px;"/>').html(logData.logList[i].createTime.slice(0, 19).replace(/T/g, ' ')))
        row.append($('<td style="font-size: 12px;"/>').html(logData.logList[i].msg))

        $(selector).append(row)
      }
    },
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
    execLogBackup: function () {
      this.closeLogBackupNowExecuteView()

      return axios.put('/license-service/logs-backup', {
        minute: 0,
        hour: 0,
        day: 1,
        enable: true,
        month: '3,6,9,12',
        force: true
      }).then(
        res => {
          if (res.data.result === 0) {
            this.searchLog(1)

            this.isLogBackupNowViewed = true
            this.$modal.show('LogBackupNowPopup')
          } else {
            this.showAlertView('즉시 로그 백업을 실패하였습니다.')
            return
          }
        }
      ).catch((err) => {
        alert(err)
      })
    },
    showLogDateSettingView: function () {
      this.isLogDateSettingViewed = true
      this.$modal.show('LogDateSettingPopup')
    },
    searchLogDateSettingView: function (value) {
      this.closeLogDateSettingView()

      this.currentDate = value.currentDate
      this.currentTime = value.currentTime
      this.agoDate = value.agoDate
      this.agoTime = value.agoTime
      this.searchCreateTimeBegin = value.searchCreateTimeBegin
      this.searchCreateTimeEnd = value.searchCreateTimeEnd

      this.searchLog(1)
    },
    closeLogDateSettingView: function () {
      this.isLogDateSettingViewed = false
      this.$modal.hide('LogDateSettingPopup')
    },
    showLogBackupSettingView: function () {
      this.isLogBackupSettingViewed = true
      this.$modal.show('LogBackupSettingPopup')
    },
    closeLogBackupSettingView: function () {
      this.isLogBackupSettingViewed = false
      this.$modal.hide('LogBackupSettingPopup')
    },
    showLogBackupNowExecuteView: function () {
      this.isLogBackupNowExecuteViewed = true
      this.$modal.show('LogBackupNowExecutePopup')
    },
    closeLogBackupNowExecuteView: function () {
      this.isLogBackupNowExecuteViewed = false
      this.$modal.hide('LogBackupNowExecutePopup')
    },
    closeLogBackupNowView: function () {
      this.isLogBackupNowViewed = false
      this.$modal.hide('LogBackupNowPopup')
    },
    showLogRestoreFileSelect: function () {
      this.isLogRestoreFileSelectViewed = true
      this.$modal.show('LogRestoreFileSelectPopup')
    },
    closeLogRestoreFileSelect: function () {
      this.isLogRestoreFileSelectViewed = false
      this.$modal.hide('LogRestoreFileSelectPopup')
    },
    showLogRestoreView: function () {
      this.isLogRestoreViewed = true
      this.$modal.show('LogRestorePopup')
    },
    closeLogResotreView: function () {
      this.isLogRestoreViewed = false
      this.$modal.hide('LogRestorePopup')

      this.searchLog(1)
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

.button_center {
  position: relative;
  float: center;
}

.button_right {
  position: relative;
  float: right;
}
</style>
