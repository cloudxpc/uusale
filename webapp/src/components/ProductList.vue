<template>
  <div>
    <h1 class="page_header">商品列表</h1>
    <div class="weui-panel">
      <div class="weui-search-bar" id="searchBar">
        <form class="weui-search-bar__form">
          <div class="weui-search-bar__box">
            <i class="weui-icon-search"></i>
            <input type="search" class="weui-search-bar__input" id="searchInput" placeholder="搜索" required="" v-model="searchText">
            <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
          </div>
          <label class="weui-search-bar__label" id="searchText" style="transform-origin: 0px 0px; opacity: 1; transform: scale(1, 1);">
            <i class="weui-icon-search"></i>
            <span>搜索</span>
          </label>
        </form>
        <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
      </div>
      <div v-if="$eventBus.isMch" class="weui-panel__hd">
        <router-link to="/main/product/edit/new" class="weui-btn weui-btn_mini weui-btn_primary">添加商品</router-link>
      </div>
      <div class="weui-panel__bd">
        <router-link v-for="item in filteredProducts" :key="item.id"
                     :to="'/main/product/' + ($eventBus.isMch ? 'edit' : 'view') + '/' + item.id"
                     class="weui-media-box weui-media-box_appmsg">
          <div class="weui-media-box__hd">
            <img class="weui-media-box__thumb" :src="getProductFirstImgSrc(item)" alt="">
          </div>
          <div class="weui-media-box__bd">
            <h4 class="weui-media-box__title">{{item.name}}</h4>
            <p class="weui-media-box__desc">{{item.description}}</p>
          </div>
          <div class="weui-media-box__ft">
            <span>{{item.price | currency}}</span>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
  import weui from 'weui.js';
  export default {
    name: 'ProductList',
    data: function () {
      return {
        products: [],
        searchText: ''
      };
    },
    computed: {
      filteredProducts: function () {
        return this.searchText ? this.$_.filter(this.products, (p) => p.name.indexOf(this.searchText) >= 0) : this.products;
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
        let url = this.$eventBus.isMch ? '/product/mch/list' : '/product/list';
        this.$axios.get(url).then(response => {
          if (response && response.data) {
            this.products = response.data;
          }
        });
      },
      getProductFirstImgSrc: function (product) {
        if (product && product.images && product.images.length) {
          return this.$eventBus.imgBaseUrl + this.$_.head(product.images);
        } else {
          return '';
        }
      }
    }
  }
</script>
