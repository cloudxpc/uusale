<template>
  <div class="weui-tab">
    <div class="weui-tab__panel">
      <router-view></router-view>
    </div>
    <div class="weui-tabbar">
      <router-link v-for="menu in userMenus" :key="menu.id" :to="menu.to" class="weui-tabbar__item" active-class="weui-bar__item_on">
        <img :src="menu.icon" alt="" class="weui-tabbar__icon">
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
    },
    methods: {

    }
  }
</script>
