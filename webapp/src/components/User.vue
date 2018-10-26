<template>
  <div>
    <h1 class="page_header">我的信息</h1>
    <div class="weui-panel">
      <div class="weui-panel__bd">
        <div class="weui-media-box weui-media-box_appmsg">
          <div class="weui-media-box__hd">
            <img id="qrcode">
          </div>
          <div class="weui-media-box__bd">
            <h4 class="weui-media-box__title">{{$eventBus.userInfo.userDisplayName}}</h4>
            <div style="display: flex;">
              <p class="weui-media-box__desc" style="flex: 1;">登录名: {{$eventBus.userInfo.username}}</p>
              <p class="weui-media-box__desc" style="flex: 1;">用户类型: {{$eventBus.isMch ? '商户' : '用户'}}</p>
            </div>
            <p class="weui-media-box__desc">手机号: {{$eventBus.userInfo.phoneNumber}}</p>
          </div>
        </div>
      </div>
    </div>
    <div class="weui-cells">
      <router-link to="/main/change-user-info" class="weui-cell weui-cell_access">
        <div class="weui-cell__bd">
          <p>修改个人信息</p>
        </div>
        <div class="weui-cell__ft"></div>
      </router-link>
      <router-link to="/main/change-password" class="weui-cell weui-cell_access">
        <div class="weui-cell__bd">
          <p>修改密码</p>
        </div>
        <div class="weui-cell__ft"></div>
      </router-link>
    </div>
    <div class="weui-cells" v-if="!$eventBus.isMch">
      <router-link to="/main/orders" class="weui-cell weui-cell_access">
        <div class="weui-cell__bd">
          <p>历史订单</p>
        </div>
        <div class="weui-cell__ft"></div>
      </router-link>
    </div>
    <br/>
    <div class="weui-btn-area">
      <button type="button" class="weui-btn weui-btn_warn" @click="logout">退出登录</button>
    </div>
  </div>
</template>

<script>
  import QRCode from 'qrcode';

  export default {
    name: 'User',
    mounted: function () {
      this.qrcode();
    },
    methods: {
      logout: function () {
        this.$eventBus.confirm('确认退出登录?', () => {
          this.$eventBus.resetUserInfo();
          this.$axios.defaults.headers.common['Authorization'] = null;
          this.$router.push('/login');
        });
      },
      qrcode: function () {
        QRCode.toDataURL(this.$eventBus.baseUrl + '/#/login?u=' + this.$eventBus.userInfo.username + '&p=' + this.$eventBus.userInfo.password, { margin: 1, width: 60 }, function (error, url) {
          document.getElementById('qrcode').src = url;
        });
      }
    }
  }
</script>

<style scoped>
  .weui-media-box__desc {
    line-height: 1.5;
  }
</style>
