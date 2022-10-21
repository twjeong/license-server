<template>
  <div class="metering_modal">
    <div class="metering_modal-card">
      <h2 class="metering_modal-head">{{ title }} ({{ custNm }})</h2>

      <section style="display: inline-block;">
        <div class="year_list">
          <ul>
            <li ref="thisYear">{{ thisYear }}</li>
          </ul>
        </div>
        <div class="month_list">
          <ul>
            <li v-for="month in thisMonthList"
              v-bind:key="month"
              style="cursor: pointer;"
              v-bind:class="{ circle: selectedMonth === month }"
              @click="setMonthAYear(thisYear, month)">
                {{ month }}월
            </li>
          </ul>
        </div>
      </section>

      <section style="display: inline-block;">
        <div class="year_list">
          <ul>
            <li ref="lastYear">{{ lastYear }}</li>
          </ul>
        </div>
        <div class="month_list">
          <ul>
            <li v-for="month in lastMonthList"
              v-bind:key="month"
              style="cursor: pointer;"
              v-bind:class="{ circle: selectedMonth === month }"
              @click="setMonthAYear(lastYear, month)">
                {{ month }}월
            </li>
          </ul>
        </div>
      </section>

      <section>
        <div class="use-date">
          <ul>
            <li><p>이용 기간  :   {{ usedMonthStartDate }} ~ {{ usedMonthEndDate }}</p></li>
          </ul>
        </div>

        <div class="month-container">
          <div class="card current">
            <p class="card-header current">{{ selectedMonth }}월 이용 요금</p>
            <div class="card-price current">
              <p class="price">{{ markPrice(price) }} 원</p>
              <span>(VAT 포함)</span>
            </div>
          </div>
        </div>
      </section>

      <div class="mt15 mb15 txt_center">
        <input type="button" value="상세 내역" @click="createReport()" class="btn_basic btn_cancel bg_bluegreen w120 mr10">
        <input type="button" value="닫기" @click="cancel()" class="btn_cancel btn_basic color_indigo btn_outline w120">
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import axios from 'axios'

export default {
  props: {
    custNm: { type: String }
  },
  data: function () {
    return {
      title: '종량제 빌링 보고서',
      selectedYear: '',
      selectedMonth: '',
      usedYear: '',
      usedMonth: '',
      usedMonthStartDate: '',
      usedMonthEndDate: '',
      thisMonthList: [],
      thisYearList: [],
      lastMonthList: [],
      lastYearList: [],
      thisYear: '',
      lastYear: '',
      price: '0'
    }
  },
  mounted () {
    this.getMonthAYear()
    this.setMonthAYear(this.thisYear, this.thisMonthList[0])
  },
  methods: {
    setMonthAYear: function (year, month) {
      if (year === this.thisYear) {
        this.$refs.thisYear.style.color = '#c0392b'
        this.$refs.lastYear.style.color = '#666'
      } else {
        this.$refs.thisYear.style.color = '#666'
        this.$refs.lastYear.style.color = '#c0392b'
      }

      this.getUseMonthStartEndDate(year, month - 1)
      this.selectedYear = year
      this.selectedMonth = month

      if (this.custNm.length !== 0) {
        axios.get('/license-service/licenses/billing/amount', {
          params: {
            year: this.usedYear,
            month: this.usedMonth,
            custNm: this.custNm
          }
        }).then(res => {
          this.price = res.data.totalAmount
        })
      }
    },
    getMonthAYear: function () {
      const currentDate = moment(new Date())
      this.thisYear = currentDate.format('YYYY')
      this.lastYear = moment(currentDate).subtract(1, 'years').format('YYYY')
      this.selectedYear = this.thisYear

      this.selectedMonth = currentDate.format('M')
      this.getUseMonthStartEndDate(this.thisYear, this.selectedMonth - 1)
      this.$refs.thisYear.style.color = '#c0392b'

      let thisMonthListIndex = 0
      let lastMonthListIndex = 0

      for (let i = 0; i < 12; i++) {
        const localMonth = moment(currentDate).subtract(i, 'month').format('M')
        const localYear = moment(currentDate).subtract(i, 'month').format('YYYY')

        if (this.thisYear === localYear) {
          this.thisMonthList[thisMonthListIndex] = localMonth
          this.thisYearList[thisMonthListIndex] = localYear
          thisMonthListIndex++
        } else {
          this.lastMonthList[lastMonthListIndex] = localMonth
          this.lastYearList[lastMonthListIndex] = localYear
          lastMonthListIndex++
        }
      }
    },
    getUseMonthStartEndDate: function (year, month) {
      if (month === 0) {
        month = 12
        year = year - 1
      }

      this.usedYear = year
      this.usedMonth = month

      if (month < 10) {
        month = '0' + month
      }

      const date = moment(year + '-' + month + '-' + '01')
      this.usedMonthStartDate = date.startOf('month').format('YYYY-MM-DD')
      this.usedMonthEndDate = date.endOf('month').format('YYYY-MM-DD')
    },
    createReport: function () {
      this.$emit('ok',
        {
          selectedYear: this.selectedYear,
          selectedMonth: this.selectedMonth,
          usedMonthStartDate: this.usedMonthStartDate,
          usedMonthEndDate: this.usedMonthEndDate,
          usedYear: this.usedYear,
          usedMonth: this.usedMonth
        })
    },
    cancel: function () {
      this.$emit('close-modal')
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
.metering_modal {
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
  overflow-y: auto;
  height: 400px;
}
.metering_modal-card {
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
  text-align: center;
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
.use-date {
  margin-top: 20px;
  font-weight: 300;
}
.month-container {
  margin-top: 10px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.year_list {
  display: block;
}
.year_list ul li {
  padding-top: 10px;
  padding-left: 10px;
  text-align: left;
  font-size: 2rem;
  font-weight: 500;
  color: #666;
}
.month_list {
  display: block;
  text-align: center;
}
.month_list ul li {
  float: left;
  margin: 10px;
  width: 30px;
  line-height: 30px;
  font-size: 1.5rem;
  font-weight: 300;
}
.circle {
  width: 30px;
  height: 30px;
  line-height: 30px;
  border-radius: 50%;
  color: #fff;
  text-align: center;
  background: #c0392b;
}
</style>
