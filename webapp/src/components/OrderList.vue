<template>
  <div :style="{paddingTop: ($eventBus.isMch ? 0 : 45) + 'px'}">
    <nav-bar v-if="!$eventBus.isMch"></nav-bar>
    <h1 class="page_header">订单列表</h1>
    <div class="weui-panel">
      <div class="weui-search-bar" id="searchBar">
        <form class="weui-search-bar__form">
          <div class="weui-search-bar__box">
            <i class="weui-icon-search"></i>
            <input type="search" class="weui-search-bar__input" id="searchInput" placeholder="搜索" required=""
                   v-model="searchText">
            <a href="javascript:" class="weui-icon-clear" id="searchClear" @click="searchText=''"></a>
          </div>
          <label class="weui-search-bar__label" id="searchText"
                 style="transform-origin: 0px 0px; opacity: 1; transform: scale(1, 1);">
            <i class="weui-icon-search"></i>
            <span>搜索</span>
          </label>
        </form>
        <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel" @click="searchText=''">取消</a>
      </div>
      <div class="weui-panel__bd">
        <router-link v-for="order in filteredOrders" :key="order.id" :to="'/main/order/' + order.id" class="weui-media-box weui-media-box_appmsg">
          <div class="weui-media-box__bd">
            <h4 class="weui-media-box__title">{{order.orderNo}}{{order.state === 'C' ? ' - 订单已取消' : ''}}</h4>
            <p class="weui-media-box__desc">{{order.comments}}</p>
            <ul class="weui-media-box__info">
              <li class="weui-media-box__info__meta">{{order.userDisplayName}}</li>
              <li class="weui-media-box__info__meta">{{order.totalAmt | currency}}</li>
              <li class="weui-media-box__info__meta weui-media-box__info__meta_extra">{{order.creationTime | date('yyyy-MM-dd')}}</li>
            </ul>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
  import weui from 'weui.js';

  export default {
    name: 'OrderList',
    data: function () {
      return {
        orders: [],
        searchText: ''
      };
    },
    computed: {
      filteredOrders: function () {
        return this.searchText ? this.$_.filter(this.orders, (p) => p.orderNo.indexOf(this.searchText) >= 0) : this.orders;
      }
    },
    created: function () {
      this.init();
    },
    mounted: function () {
      weui.searchBar('#searchBar');
    },
    methods: {
      init: function () {
        let url = this.$eventBus.isMch ? '/order/mch/list' : '/order/list';
        this.$axios.get(url).then(response => {
          if (response && response.data) {
            this.orders = response.data;
          }
        });
      }
    }
  }
</script>

<style scoped>
  .order-link {
    color: #000;
  }
</style>
