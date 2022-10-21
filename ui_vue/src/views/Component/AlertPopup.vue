<template>
  <div class="alert_modal">
    <div class="alert_modal-card">
      <h2 class="alert_modal-head" >
          <img src="../../static/img/icon_error.png" class="icon_error">
      </h2>
      <slot />
      <div class="wrap_cont">
        {{ msg }}
      </div>
      <div class="mt20 mb20 txt_center">
        <input type="button" value="확인" @click="close()" class="btn_cancel btn_basic bg_bluegreen w80">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    msg: {
      type: String
    }
  },
  methods: {
    close: function () {
      this.$emit('close-modal')
    }
  }
}
</script>

<style scoped>
/* Modal */
.alert_modal {
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
}
.alert_modal-card {
  border-radius: 0.2rem 0.2rem;
  width: 320px !important;
  height: 200px !important;
  transition: all 0.3s;
  z-index: 999;
  opacity: 1;
  text-align: center;
}
.alert_modal-head {
  margin-top: 0;
  height: 5.5rem;
  line-height: 6rem;
  text-align: center;
}
.wrap_cont {
  width: 100%;
  padding: 2rem;
  overflow: visible;
  height: 70px;
  line-height: 2rem;
}
</style>
