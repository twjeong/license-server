<template>
<div>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>D'Amo Policy Server</title>
    <link rel="icon" href="../static/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="../static/favicon.ico" type="image/x-icon" />
  </head>
  <!-- HEADER -->
  <header class="clearfix">
      <section class="layout-2-left">
          <img src="../static/img/damo_logo.png" class="logo_damo">
          <a href="/" id="productName" data-tooltip-location="right"><h1 class="product_name">Policy Server</h1></a>   <!-- 디아모 로고와 제품명 -->
          <a href="#" class="btn_hambuger" @click="menuToggle" data-tooltip="메뉴 접기/펼치기" data-tooltip-location="right"><img class="menu_hamburger" src="../static/img/icon_hamburger.png"></a> <!-- 햄버거 메뉴 아이콘 -->
      </section>
      <section class="layout-2-right clearfix">
        <div class="f_right ml20"><a @click="logout()" data-tooltip="로그아웃" data-tooltip-location="left"><img src="../static/img/icon_logout.png" class="icon_logout"></a>
        </div>   <!-- 로그 아웃 아이콘 -->
        <div class="f_right clearfix">      <!-- 계정 관련 영역 -->
            <div class="f_left"><img class="icon_user" src="../static/img/icon_user.png"></div>    <!-- 유저 아이콘 -->
            <div class="account f_left" data-tooltip="계정 설정" data-tooltip-location="left">
                <p class="h30" style="color: #ffffff;">{{ sessionedUser }}</p>
                <p class="h30" style="color: #7e8ea8;">Admin</p>  <!-- 권한 -->
            </div>
            <div class="f_left"><a href="/setting" class="inline_block w30"><span class="account_v">&rang;</span></a></div>  <!-- V 글자 -->
        </div>
      </section>
  </header>

  <!-- Main Menu Block -->
  <nav ref="nav">
    <ul>
      <!-- 대시보드 -->
      <li :class="{ on : selected == 1 }"><a @click="selectMenu(1)"><span :class="{ active: selected == 1 }">
        <img src="../static/img/icon_home.png"></span>대시보드</a></li>
      <!-- 계약 정보 현황 -->
      <li :class="{ on : selected == 2 }"><a @click="selectMenu(2)"><span :class="{ active: selected == 2 }">
        <img src="../static/img/icon_contract.png"></span>계약 정보 관리</a></li>
      <!-- 제품 라이선스 현황 -->
      <li :class="{ on : selected == 3 }"><a @click="selectMenu(3)"><span :class="{ active: selected == 3 }">
        <img src="../static/img/icon_license.png"></span>제품 라이선스 관리</a></li>
      <!-- 종량제 라이선스 사용량 현황 -->
      <li :class="{ on : selected == 4 }"><a @click="selectMenu(4)"><span :class="{ active: selected == 4 }">
        <img src="../static/img/icon_cloud.png"></span>종량제 라이선스 사용량 현황</a></li>
      <!-- 로그 -->
      <li :class="{ on : selected == 5 }"><a @click="selectMenu(5)"><span :class="{ active: selected == 5 }">
        <img src="../static/img/icon_log.png"></span>로그</a></li>
      <!-- 설정 -->
      <li :class="{ on : selected == 6 }"><a @click="selectMenu(6)"><span :class="{ active: selected == 6 }">
        <img src="../static/img/icon_setup.png"></span>설정</a></li>
    </ul>
  </nav>

  <!-- Cotents Block -->
  <div class="contents_wrap" ref="menuBar">
    <div id="child_app">
      <Dashboard v-if="this.selected == 1" />
      <ContractInfoStatus v-if="this.selected == 2" />
      <ProductLicenseStatus v-if="this.selected == 3" />
      <MeteringLicenseStatus v-if="this.selected == 4" />
      <Log v-if="this.selected == 5" />
      <Setup v-if="this.selected == 6" />
    </div>
  </div>

  <!-- FOOTER -->
  <footer ref="footer">
      <p id="copyright">ⓒ 2004 Penta Security Systems Inc. All rights reserved.</p>
  </footer>

  <!--  풀다운 메뉴 : 계정관련  -->
  <div class="account_menu">
      <ul>
          <li><a href="/settings">정보 수정</a></li>
      </ul>
  </div>
</div>
</template>

<script>
import { defineComponent } from '@vue/composition-api'
import axios from 'axios'
import VueCookies from 'vue-cookies'

var menuOpen = true

export default defineComponent({
  components: {
    Dashboard: () => import('../views/Dashboard.vue'),
    ContractInfoStatus: () => import('../views/ContractInfoStatus.vue'),
    ProductLicenseStatus: () => import('../views/ProductLicenseStatus.vue'),
    MeteringLicenseStatus: () => import('../views/MeteringLicenseStatus.vue'),
    Log: () => import('../views/Log.vue'),
    Setup: () => import('../views/Setup.vue')
  },
  data () {
    return {
      selected: 0,
      sessionedUser: VueCookies.get('ID')
    }
  },
  mounted () {
    this.checkToken()

    this.selected = 1
    axios.get('/license-service/version').then(
      res => {
        document.getElementById('productName').setAttribute('data-tooltip', res.data)
        return res.data
      })
  },
  destroyed () {
    clearInterval(this.intervalid)
  },
  methods: {
    checkToken: function () {
      this.intervalid = setInterval(() => {
        axios.post('/license-service/token', { token: VueCookies.get('PENTA-AUTH-TOKEN') }).then(
          res => {
            if (res.data === false) {
              return this.$router.push({ name: 'Login' })
            }
          })
      }, 60000)
    },
    refreshToken: function () {
      axios.get('/license-service/refresh', { token: VueCookies.get('PENTA-AUTH-TOKEN') }).then(
        res => {
          if (res.data === false) {
            return this.$router.push({ name: 'Login' })
          } else {
            VueCookies.set('PENTA-AUTH-TOKEN', res.headers['penta-auth-token'], '1h')
          }
        })
    },
    logout: function () {
      return axios.get('/license-service/logouts').then(
        res => {
          VueCookies.remove('ID')
          VueCookies.remove('PENTA-AUTH-TOKEN')
          this.$router.push({ name: 'Login' })
        })
    },
    selectMenu: function (sel) {
      this.selected = sel
      this.refreshToken()
    },
    menuToggle: function () {
      if (menuOpen === true) {
        this.$refs.nav.style.left = '-25rem'
        this.$refs.menuBar.style.left = 0
        this.$refs.menuBar.style.width = '100%'
        this.$refs.footer.style.left = 0
        this.$refs.footer.style.width = '100%'
      } else {
        this.$refs.nav.style.left = 0
        this.$refs.menuBar.style.left = '25rem'
        this.$refs.menuBar.style.width = 'calc(100% - 25rem)'
        this.$refs.footer.style.left = '25rem'
        this.$refs.footer.style.width = 'calc(100% - 25rem)'
      }
      menuOpen = !menuOpen
    }
  }
})
</script>

<style>
@import '../static/css/reset200802.css';
@import '../static/css/controls.css';
@import '../static/css/style.css';
@import '../static/css/style_billing.css';
@import '../static/css/jquery-ui-1.12.1.css';
</style>
