<template>
  <div class="pwd_modal">
    <div class="overlay"></div>
    <div class="pwd_modal-card">
      <h2 class="pwd_modal-head">{{ title }}</h2>
      <slot />
      <div class="wrap_cont">
        {{ msg }}
        <div class="mt20 mb20 txt_center">
          <input type="button" value="확인" @click="confirm()"
                class="btn_cancel btn_basic color_indigo btn_outline w80">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      title: '비밀번호 변경',
      msg: '비밀번호가 변경되었습니다.'
    }
  },
  methods: {
    confirm: function () {
      this.$emit('close-modal')
      this.$router.push({ name: 'Home' })
    }
  }
}
</script>

<style scoped>
/* Modal */
.pwd_modal,
.overlay {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
}
.overlay {
  opacity: 0.5;
  background-color: black;
}
.pwd_modal-card {
  top: 10%;
  left: 30%;
  position: fixed;
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  width: 400px !important;
  height: 150px !important;
  transition: all 0.3s;
  z-index: 999;
  opacity: 1;
}
.pwd_modal-head {
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
.wrap_cont {
  width: 100%;
  padding: 2rem;
  overflow: visible;
  height: 100px;
}

</style>
