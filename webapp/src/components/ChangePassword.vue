<template>
  <div>
    <h1 class="page_header">修改密码</h1>
    <div class="weui-cells">
      <div class="weui-cell">
        <div class="weui-cell__hd">
          <label class="weui-label">旧密码</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="password" placeholder="请输入旧密码" v-model="user.oldPassword">
        </div>
        <div v-if="validation.oldPassword" class="weui-cell__ft">
          <i class="weui-icon-warn"></i>
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd">
          <label class="weui-label">新密码</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="password" placeholder="请输入新密码" v-model="user.password">
        </div>
        <div v-if="validation.password" class="weui-cell__ft">
          <i class="weui-icon-warn"></i>
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd">
          <label class="weui-label">确认密码</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="password" placeholder="请再次输入密码" v-model="user.passwordConfirm">
        </div>
        <div v-if="validation.passwordConfirm" class="weui-cell__ft">
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
    name: 'ChangePassword',
    data: function () {
      return {
        user: {
          oldPassword: null,
          password: null,
          passwordConfirm: null
        },
        validation: {
          oldPassword: false,
          password: false,
          passwordConfirm: false
        }
      };
    },
    methods: {
      validate: function () {
        this.validation.oldPassword = !this.user.oldPassword;
        this.validation.password = !this.user.password;
        this.validation.passwordConfirm = !this.user.passwordConfirm;

        if (this.user.password && this.user.passwordConfirm && this.user.password !== this.user.passwordConfirm) {
          this.$eventBus.alert('两次密码输入不一致');
          return false;
        }

        return !this.validation.oldPassword
          && !this.validation.password
          && !this.validation.passwordConfirm;
      },
      submit: function () {
        if (!this.validate())
          return;

        this.$axios.post('/change/changepwd', this.user).then(response => {
          if (response && response.status === 200) {
            this.$eventBus.toast('密码修改成功');
            this.$router.go(-1);
          }
        });
      }
    }
  }
</script>
