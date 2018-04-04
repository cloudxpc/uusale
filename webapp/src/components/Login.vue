<template>
  <div>
    <h1 class="page_header">用户登录</h1>
    <div class="weui-cells">
      <div class="weui-cell">
        <div class="weui-cell__hd">
          <label class="weui-label">用户名</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="text" placeholder="请输入用户名或手机号" v-model="user.username">
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
    </div>
    <div class="weui-btn-area">
      <button type="button" class="weui-btn weui-btn_primary" @click.prevent="login">登录</button>
    </div>
    <div class="weui-agree" style="text-align: right;">
      <label class="weui-agree__text">
        <router-link to="/register">还没有用户? 请注册</router-link>
      </label>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Login',
    data: function () {
      return {
        user: {
          username: null,
          password: null
        },
        validation: {
          username: false,
          password: false
        }
      };
    },
    beforeRouteEnter(to, from, next) {
      next(vm => {
        if (vm.$route.query.u && vm.$route.query.p) {
          vm.user.username = vm.$route.query.u;
          vm.user.password = vm.$route.query.p;
          vm.login();
        }
      });
    },
    methods: {
      validate: function () {
        this.validation.username = !this.user.username;
        this.validation.password = !this.user.password;
        return !this.validation.username && !this.validation.password;
      },
      login: function () {
        if (!this.validate())
          return;

        this.$axios.post('/user/login', this.user).then(response => {
          if (response && response.status === 200) {
            if (response.data.token)
              this.$axios.defaults.headers.common['Authorization'] = 'Bearer ' + response.data.token;

            this.$eventBus.userInfo = response.data;
            this.$cart.init(this.$eventBus.userInfo.cart);
            this.$router.replace('/main/products');
          }
        });
      }
    }
  }
</script>
