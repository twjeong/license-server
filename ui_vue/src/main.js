import 'babel-polyfill'
import Vue from 'vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import '@mdi/font/css/materialdesignicons.css'
import App from './App.vue'
import router from './router'
import store from './store'
import VueCryptojs from 'vue-cryptojs'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/index.css'
import 'url-search-params-polyfill'
import VModal from 'vue-js-modal/dist/ssr.nocss'
import 'vue-js-modal/dist/styles.css'

Vue.config.productionTip = false

Vue.use(Vuetify)
Vue.use(VueCryptojs)
Vue.use(VModal)
Vue.use(DatePicker)

new Vue({
  vuetify: new Vuetify(),
  router,
  store,
  render: h => h(App)
}).$mount('#app')
