<template>
  <div class="weui-tab">
    <div class="weui-navbar" v-if="!isRootPage">
      <a class="weui-navbar__item" @click.prevent="$router.go(-1)">返回</a>
    </div>
    <div class="weui-tab__panel">
      <router-view></router-view>
    </div>
    <div class="weui-tabbar">
      <router-link v-for="menu in userMenus" :key="menu.id" :to="menu.to" class="weui-tabbar__item" active-class="weui-bar__item_on">
        <span style="display: inline-block;position: relative;">
          <img :src="menu.icon" alt="" class="weui-tabbar__icon">
          <span v-if="menu.id === 2 && $eventBus.userInfo.unreadCount" class="weui-badge" style="position: absolute;top: -2px;right: -13px;">{{$eventBus.userInfo.unreadCount}}</span>
        </span>
        <p class="weui-tabbar__label">{{menu.name}}</p>
      </router-link>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Main',
    data: function () {
      return {
        menus: [
          { id: 1, name: '商品', to: '/main/products', icon: '/uusale/static/icon_nav_cell.png' },
          { id: 2, name: '订单', to: '/main/orders', icon: '/uusale/static/icon_nav_article.png' },
          { id: 3, name: '购物车', to: '/main/cart', icon: '/uusale/static/icon_nav_article.png' },
          { id: 4, name: '我的', to: '/main/user', icon: '/uusale/static/icon_nav_button.png' }
        ]
      };
    },
    computed: {
      userMenus: function () {
        return this.$_.filter(this.menus, (m) => (this.$eventBus.isMch ? [1,2,4] : [1,3,4]).indexOf(m.id) >= 0);
      },
      isRootPage: function () {
        let fullPath = this.$route.fullPath;
        return fullPath === '/main/products'
          || (fullPath === '/main/orders' && this.$eventBus.isMch)
          || fullPath === '/main/user'
          || fullPath === '/main/cart';
      }
    },
    methods: {

    }
  }
</script>

<style scoped>
  .weui-navbar__item {
    color: #999999;
  }

  .weui-navbar__item:before {
    content: "";
    position: absolute;
    top: 20px;
    left: 15px;
    margin: 0px;
    border-left: 2px solid #999999;
    border-bottom: 2px solid #999999;
    width: 8px;
    height: 8px;
    transform: rotate(45deg);
    -o-transform: rotate(45deg);
    -webkit-transform: rotate(45deg);
    -moz-transform: rotate(45deg);
    -ms-transform: rotate(45deg);
  }

  .weui-tab__panel {
    padding-bottom: 50px !important;
  }
</style>
