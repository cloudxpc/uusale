<template>
  <div>
    <h1 class="page_header">商户注册</h1>
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
          <label class="weui-label">密码</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="password" placeholder="请输入密码" v-model="user.password">
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
      <div class="weui-cell weui-cell_vcode">
        <div class="weui-cell__hd">
          <label class="weui-label">验证码</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="text" placeholder="请输入验证码" v-model="user.captchaCode">
        </div>
        <div class="weui-cell__ft">
          <i v-if="validation.captchaCode" class="weui-icon-warn"></i>
          <img class="weui-vcode-img" :src="captchaCodeSrc" @click="refreshCaptchaCode">
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
    name: 'RegisterMch',
    data: function () {
      return {
        captchaCodeSrc: '',
        user: {
          username: null,
          password: null,
          passwordConfirm: null,
          displayName: null,
          phoneNumber: null,
          captchaCode: null
        },
        validation: {
          username: false,
          password: false,
          passwordConfirm: false,
          displayName: false,
          phoneNumber: false,
          captchaCode: false
        }
      };
    },
    methods: {
      validate: function () {
        this.validation.username = !this.user.username;
        this.validation.password = !this.user.password;
        this.validation.passwordConfirm = !this.user.passwordConfirm;
        this.validation.displayName = !this.user.displayName;
        this.validation.phoneNumber = !this.user.phoneNumber;
        this.validation.captchaCode = !this.user.captchaCode;

        if (this.user.password && this.user.passwordConfirm && this.user.password !== this.user.passwordConfirm) {
          this.$eventBus.alert('两次密码输入不一致');
          return false;
        }

        return !this.validation.username
          && !this.validation.password
          && !this.validation.passwordConfirm
          && !this.validation.displayName
          && !this.validation.phoneNumber
          && !this.validation.captchaCode;
      },
      submit: function () {
        if (!this.validate())
          return;

        this.$axios.post('/user/mch/register', this.user, {withCredentials: true}).then(response => {
          if (response && response.status === 200) {
            this.$eventBus.alert('注册成功', () => {
              this.$router.replace('/mch');
            });
          }
        });
      },
      refreshCaptchaCode: function () {
        this.$axios.get('/user/captcha', {withCredentials: true}).then(response => {
          if (response && response.status === 200) {
            this.captchaCodeSrc = response.data;
          }
        });
      },
    },
    created: function () {
      this.refreshCaptchaCode();
    }
  }
</script>
