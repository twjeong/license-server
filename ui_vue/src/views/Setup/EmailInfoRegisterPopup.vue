<template>
  <div class="alarm-modal">
    <div class="overlay"></div>
    <div class="alarm-modal-card">
      <h2 class="alarm-modal-head">{{ title }}</h2>
        <div class="alarm-modal-body">
        {{ msg }}
        <div class="mt20 mb10 txt_center">
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
      title: '알림 메일 정보',
      msg: '알림 메일 정보가 설정되었습니다.'
    }
  },
  methods: {
    confirm: function () {
      this.$emit('close-modal')
    }
  }
}
</script>

<style scoped>
/* Modal */
.alarm-modal {
  left: 0;
  top: 0;
}
.alarm-modal-card {
  text-align: center;
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  width: 250px !important;
  height: 150px !important;
  transition: all 0.3s;
  z-index: 999;
  opacity: 1;
}
.alarm-modal-head {
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
.alarm-modal-body {
  margin-top: 20px;
  text-align: center;
}
</style>
