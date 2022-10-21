<template>
  <div class="modal">
    <div class="overlay"></div>
    <div class="modal-card">
      <h2 class="modal-head">{{ title }}</h2>
        <div class="modal-body">
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
      title: '서버 SSL 인증서 업로드',
      msg: '서버에 파일 업로드가 완료되었습니다.'
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
.modal {
  width: 100%;
  height: 100%;
}
.modal-card {
  text-align: center;
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  width: 300px !important;
  height: 150px !important;
  transition: all 0.3s;
  z-index: 999;
  opacity: 1;
}
.modal-head {
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
.modal-body {
  margin-top: 20px;
}
</style>
