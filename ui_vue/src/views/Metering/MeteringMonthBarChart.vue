<div>
  <bar-chart></bar-chart>
</div>

<script>
import { Bar } from 'vue-chartjs'
import axios from 'axios'

export default {
  extends: Bar,
  props: {
    custNm: { type: String },
    selectedYear: { type: String },
    selectedMonth: { type: Number }
  },
  components: {
    'bar-chart': Bar
  },
  data () {
    return {
      halfYearAmountList: []
    }
  },
  mounted () {
    const options = {
      scales: {
        yAxes: [{
          ticks: {
            callback: function (value) {
              return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
            },
            beginAtZero: true
          },
          gridLines: {
            display: true
          }
        }],
        xAxes: [{
          ticks: {
            beginAtZero: true
          },
          gridLines: {
            display: false
          }
        }]
      },
      legend: {
        display: false
      },
      tooltips: {
        enabled: true,
        mode: 'single',
        callbacks: {
          label: function (tooltipItems) {
            return tooltipItems.yLabel.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
          }
        }
      },
      responsive: true,
      maintainAspectRatio: false
    }

    const dateList = []
    const priceList = []

    axios.get('/license-service/licenses/billing/amount-half-year', {
      params: {
        year: this.selectedYear,
        month: this.selectedMonth,
        custNm: this.custNm
      }
    }).then(res => {
      this.halfYearAmountList = res.data.halfYearAmountList

      for (let i = 0; i < this.halfYearAmountList.length; i++) {
        dateList.push(this.halfYearAmountList[i].date)
        priceList.push(this.halfYearAmountList[i].amount)
      }

      const datacollection = {
        labels: dateList,
        datasets: [
          {
            label: '사용 요금',
            barPercentage: 0.2,
            backgroundColor: 'rgba(69, 138, 235, 1)',
            pointBackgroundColor: 'white',
            borderWidth: 1,
            pointBorderColor: '#0447aa',
            data: priceList
          }
        ]
      }

      this.renderChart(datacollection, options)
    })
  }
}
</script>
