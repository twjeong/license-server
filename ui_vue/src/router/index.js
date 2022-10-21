import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'
import VueCookies from 'vue-cookies'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'

Vue.use(VueRouter)

const requireAuth = () => (from, to, next) => {
  return axios.post('/license-service/token', { token: VueCookies.get('PENTA-AUTH-TOKEN') }).then(
    res => {
      if (res.data === true) {
        return next()
      } else {
        return router.push({ name: 'Login' })
      }
    })
}

const routes = [
  { path: '/', name: 'Home', component: Home, beforeEnter: requireAuth() },
  { path: '/dashboard', name: 'Dashboard', component: () => import('../views/Dashboard.vue') },
  { path: '/login', name: 'Login', component: () => import('../views/Login.vue') },
  { path: '/setup', name: 'Setup', component: () => import('../views/Setup.vue') },
  { path: '/setting', name: 'Setting', component: () => import('../views/Setting.vue') },
  {
    path: '/product-license-status',
    name: 'ProductLicenseStatus',
    component: () => import('../views/ProductLicenseStatus.vue')
  },
  {
    path: '/cloud-service-status',
    name: 'MeteringLicenseStatus',
    component: () => import('../views/MeteringLicenseStatus.vue')
  },
  {
    path: '/contract-info-status',
    name: 'ContractInfoStatus',
    component: () => import('../views/ContractInfoStatus.vue')
  },
  {
    path: '/log',
    name: 'Log',
    component: () => import('../views/Log.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
