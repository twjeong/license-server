<template>
  <div class="mail_info_delete_modal">
    <div class="lis_discard_modal-card">
      <h2 class="lis_discard_modal-head">{{ title }}</h2>
      <slot />
      <div class="wrap_cont">
        {{ msg }}
        <div class="mt20 mb20 txt_center">
          <input type="button" value="확인" @click="confirm()" class="btn_cancel btn_basic bg_bluegreen w80 mr10">
          <input type="button" value="취소" @click="cancel()" class="btn_cancel btn_basic color_indigo btn_outline w80">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      title: '메일 서버 정보 초기화',
      msg: '등록된 메일 서버 정보를 초기화하시겠습니까?'
    }
  },
  methods: {
    confirm: function () {
      this.$emit('ok')
    },
    cancel: function () {
      this.$emit('close-modal')
    }
  }
}
</script>

<style scoped>
/* Modal */
.lis_discard_modal {
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
}
.lis_discard_modal-card {
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  width: 400px !important;
  height: 150px !important;
  transition: all 0.3s;
  z-index: 999;
  opacity: 1;
}
.lis_discard_modal-head {
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
