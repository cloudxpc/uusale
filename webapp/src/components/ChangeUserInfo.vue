<template>
  <div style="padding-top: 45px;">
    <nav-bar></nav-bar>
    <h1 class="page_header">修改个人信息</h1>
    <div class="weui-cells">
      <div class="weui-cell">
        <div class="weui-cell__hd">
          <label class="weui-label">用户名</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="text" placeholder="请输入用户名" v-model="user.username">
        </div>
        <div v-if="validation.username" class="weui-cell__ft">
          <i class="weui-icon-warn"></i>
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd">
          <label class="weui-label">昵称</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="text" placeholder="请输入昵称" v-model="user.displayName">
        </div>
        <div v-if="validation.displayName" class="weui-cell__ft">
          <i class="weui-icon-warn"></i>
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd">
          <label class="weui-label">手机号</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="tel" placeholder="请输入手机号" v-model="user.phoneNumber">
        </div>
        <div v-if="validation.phoneNumber" class="weui-cell__ft">
          <i class="weui-icon-warn"></i>
        </div>
      </div>
    </div>
    <div class="weui-btn-area">
      <button type="button" class="weui-btn weui-btn_primary" @click.prevent="submit">提交</button>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'ChangeUserInfo',
    data: function () {
      return {
        user: {
          username: this.$eventBus.userInfo.username,
          displayName: this.$eventBus.userInfo.userDisplayName,
          phoneNumber: this.$eventBus.userInfo.phoneNumber
        },
        validation: {
          username: false,
          displayName: false,
          phoneNumber: false
        }
      };
    },
    methods: {
      validate: function () {
        this.validation.username = !this.user.username;
        this.validation.displayName = !this.user.displayName;
        this.validation.phoneNumber = !this.user.phoneNumber;

        return !this.validation.username
          && !this.validation.displayName
          && !this.validation.phoneNumber;
      },
      submit: function () {
        if (!this.validate())
          return;

        this.$axios.post('/change/update', this.user).then(response => {
          if (response && response.status === 200) {
            this.$eventBus.userInfo.username = this.user.username;
            this.$eventBus.userInfo.phoneNumber = this.user.phoneNumber;
            this.$eventBus.userInfo.userDisplayName = this.user.displayName;
            this.$eventBus.toast('修改成功');
            this.$router.go(-1);
          }
        });
      },
    }
  }
</script>
