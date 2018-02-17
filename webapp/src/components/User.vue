<template>
  <div>
    <h1 class="page_header">我的信息</h1>
    <div class="weui-panel">
      <div class="weui-panel__bd">
        <div class="weui-media-box weui-media-box_text">
          <h4 class="weui-media-box__title">{{$eventBus.userInfo.userDisplayName}}</h4>
          <div style="display: flex;">
            <p class="weui-media-box__desc" style="flex: 1;">登录名: {{$eventBus.userInfo.username}}</p>
            <p class="weui-media-box__desc" style="flex: 1;">用户类型: {{$eventBus.isMch ? '商户' : '用户'}}</p>
          </div>
          <p class="weui-media-box__desc">手机号: {{$eventBus.userInfo.phoneNumber}}</p>
        </div>
      </div>
    </div>
    <div class="weui-cells">
      <router-link to="" class="weui-cell weui-cell_access">
        <div class="weui-cell__bd">
          <p>修改个人信息</p>
        </div>
        <div class="weui-cell__ft"></div>
      </router-link>
    </div>
    <div class="weui-cells" v-if="!$eventBus.isMch">
      <router-link to="" class="weui-cell weui-cell_access">
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
  export default {
    name: 'User',
    methods: {
      logout: function () {
        this.$eventBus.confirm('确认退出登录?', () => {
          this.$eventBus.resetUserInfo();
          this.$axios.defaults.headers.common['Authorization'] = null;
          this.$router.push('/login');
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
