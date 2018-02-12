<template>
  <div>
    <div class="weui-cells">
      <div class="weui-cell">
        <div class="weui-cell__hd">
          <label class="weui-label">手机号</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="tel" placeholder="请输入手机号" v-model="user.username">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd">
          <label class="weui-label">密码</label>
        </div>
        <div class="weui-cell__bd">
          <input class="weui-input" type="password" placeholder="请输入密码" v-model="user.password">
        </div>
      </div>
    </div>
    <div class="weui-btn-area">
      <button type="button" class="weui-btn weui-btn_primary" @click.prevent="login">登录</button>
    </div>
  </div>
</template>

<script>
  import weui from 'weui.js';

  export default {
    name: 'Login',
    data: function () {
      return {
        user: {
          username: null,
          password: null
        }
      };
    },
    methods: {
      login: function () {
        this.$axios.post('/user/login', this.user).then(response => {
          if (response) {
            weui.alert('OK');
          }
        });
      }
    }
  }
</script>
