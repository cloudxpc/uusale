<template>
  <div class="weui-tab">
    <div class="weui-tab__panel">
      <router-view></router-view>
    </div>
    <div class="weui-tabbar">
      <router-link to="/mch/products" class="weui-tabbar__item" active-class="weui-bar__item_on">
        <img src="/static/icon_nav_cell.png" alt="" class="weui-tabbar__icon">
        <p class="weui-tabbar__label">商品</p>
      </router-link>
      <router-link to="/mch/b" class="weui-tabbar__item" active-class="weui-bar__item_on">
        <img src="/static/icon_nav_article.png" alt="" class="weui-tabbar__icon">
        <p class="weui-tabbar__label">订单</p>
      </router-link>
      <router-link to="/mch/c" class="weui-tabbar__item" active-class="weui-bar__item_on">
        <img src="/static/icon_nav_button.png" alt="" class="weui-tabbar__icon">
        <p class="weui-tabbar__label">我的</p>
      </router-link>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Merchant'
  }
</script>
