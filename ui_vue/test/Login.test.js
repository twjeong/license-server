import { createLocalVue, shallowMount } from '@vue/test-utils'
import Vue from 'vue'
import Vuetify from 'vuetify'
import Dashboard from '../src/views/Dashboard.vue'

Vue.use(Vuetify)

describe('대시보드', () => {
  it('유효성 검사', () => {
    const localVue = createLocalVue()
    const wrapper = shallowMount(Dashboard, {
      localVue
    })

    const today = new Date()
    const eDate = leadingZeros(today.getFullYear(), 4) + '-' + leadingZeros(today.getMonth() + 1, 2) + '-' + leadingZeros(today.getDate(), 2)

    const aYearAgoDay = new Date()
    const aYearAgoDate = aYearAgoDay.getTime() - (365 * 24 * 60 * 60 * 1000)
    aYearAgoDay.setTime(aYearAgoDate)
    const bDate = leadingZeros(aYearAgoDay.getFullYear(), 4) + '-' + leadingZeros(aYearAgoDay.getMonth() + 1, 2) + '-' + leadingZeros(aYearAgoDay.getDate(), 2)  

    expect(wrapper.vm.beginDate).toBe(bDate)
    expect(wrapper.vm.endDate).toBe(eDate)
  })
})

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
