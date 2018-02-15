<template>
  <div>
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
        <div v-for="order in filteredOrders" :key="order.id" class="weui-media-box weui-media-box_text">
          <h4 class="weui-media-box__title">标题一</h4>
          <p class="weui-media-box__desc">由各种物质组成的巨型球状天体，叫做星球。星球有一定的形状，有自己的运行轨道。</p>
          <ul class="weui-media-box__info">
            <li class="weui-media-box__info__meta">文字来源</li>
            <li class="weui-media-box__info__meta">时间</li>
            <li class="weui-media-box__info__meta weui-media-box__info__meta_extra">其它信息</li>
          </ul>
        </div>
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
        return this.searchText ? this.$_.filter(this.orders, (p) => p.name.indexOf(this.searchText) >= 0) : this.orders;
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
</style>
