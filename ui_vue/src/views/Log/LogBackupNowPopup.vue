<template>
  <div class="log_backup_modal">
    <div class="log_backup_modal-card">
      <h2 class="log_backup_modal-head">{{ title }}</h2>
      <slot />
      <div class="wrap_cont">
          {{ msg }}
          <div class="mt20 txt_center">
              <input type="button" value="확인" @click="confirm" class="btn_basic btn_cancel bg_bluegreen w80 mr10">
          </div>
        </div>
    </div>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      title: '즉시 로그 백업',
      msg: '즉시 로그 백업이 완료되었습니다.'
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
.log_backup_modal {
  transition: translateY(0);
  z-index: 999;
  opacity: 1;
}
.log_backup_modal-card {
  text-align: center;
  background-color: white;
  border-radius: 0.2rem 0.2rem;
  width: 250px !important;
  height: 150px !important;
  transition: all 0.3s;
  z-index: 999;
  opacity: 1;
}
.log_backup_modal-head {
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
