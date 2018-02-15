<template>
  <div class="weui-tab">
    <div class="weui-tab__panel">
      <router-view></router-view>
    </div>
    <div class="weui-tabbar">
      <router-link v-for="menu in menus" :key="menu.id" :to="menu.to" class="weui-tabbar__item" active-class="weui-bar__item_on">
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
          { id: 1, name: '商品', to: '/main/products', icon: '/static/icon_nav_cell.png' },
          { id: 2, name: '订单', to: '/main/orders', icon: '/static/icon_nav_article.png' },
          { id: 3, name: '我的', to: '/main/user', icon: '/static/icon_nav_button.png' }
        ]
      };
    },
    methods: {

    }
  }
</script>
