<template>
  <div class="log_restore_file_select_modal">
    <div class="log_restore_file_select_modal-card">
      <h2 class="log_restore_file_select_modal-head">로그 백업 파일 선택</h2>
      <!-- 테이블 영역 -->
      <template>
      <div class="pl25 pr25 pt10">
      <v-card class="mt10 mb10">
        <v-layout column style="width: 350px;">
          <v-flex>
            <v-data-table
              :headers="headers"
              :items="modifyFileList"
              hide-default-footer
              :items-per-page=65535
              style="overflow: hidden;"
              class="fixed_table fixed_table_log_restore w350">
              <template v-slot:body="{ items }">
                <tbody class="h300">
                  <tr v-for="item in items" :key="item.id" @click="clickLogFileTable(item)" :class="{'on': item.id === selectedId}">
                    <td>{{ item.fileName }}</td>
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
        <input type="button" value="복원" @click="restoreLogFile()" class="btn_basic btn_cancel bg_bluegreen w80 mr10">
        <input type="button" value="닫기" @click="closeRestoreLogFile()" class="btn_cancel btn_basic color_indigo btn_outline w80">
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
        { text: '로그 백업 파일', align: 'center', sortable: true, value: 'fileName', width: '350px' },
      ],
      logFileList: [],
      selectedId: -1,
      selectedLogFileName: ''
    }
  },
  computed: {
    modifyFileList: function () {
      let count = 1

      return this.logFileList.map((obj) => Object.assign({}, obj, {
        id: count++
      }))
    }
  },
  created () {
    this.buildLogRestoreFileList()
  },
  methods: {
    buildLogRestoreFileList: function () {
      return axios.get('/license-service/logs-restore').then(
        res => {
          this.logFileList = res.data.fileList
        })
    },
    clickLogFileTable: function (value) {
      this.selectedId = value.id
      this.selectedLogFileName = value.fileName
    },
    restoreLogFile: function () {
      if (this.selectedLogFileName.length === 0) {
        this.$emit('show-alert-modal', '선택된 파일이 없습니다.')
        return
      }

      return axios.put('/license-service/logs-restore', {
        fileName: this.selectedLogFileName
      }).then(
        res => {
          if (res.data.result !== 0) {
            this.$emit('show-alert-modal', '로그 복원을 실패하였습니다.')
            return
          }

          this.selectedLogFileName = ''
          this.$emit('ok')
        }
      ).catch((err) => {
        this.selectedLogFileName = ''
        alert(err)
      })
    },
    closeRestoreLogFile: function () {
      this.selectedLogFileName = ''
      this.$emit('close-modal')
    }
  }
}
</script>

<style scoped>
.log_restore_file_select_modal {
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
}
.log_restore_file_select_modal-card {
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
}
.log_restore_file_select_modal-head {
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
