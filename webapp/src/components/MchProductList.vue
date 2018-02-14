<template>
  <div>
    <h1 class="page_header">商品列表</h1>
    <div class="weui-panel">
      <div class="weui-panel__hd">
        <button type="button" class="weui-btn weui-btn_mini weui-btn_primary">添加商品</button>
      </div>
      <div class="weui-panel__bd">
        <router-link class="weui-media-box weui-media-box_appmsg" v-for="item in products" :key="item.id" :to="'/product/edit/' + item.id">
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
  export default {
    name: 'MchProductList',
    data: function () {
      return {
        products: []
      };
    },
    created: function () {
      this.init();
    },
    methods: {
      init: function () {
        this.$axios.get('/product/mch/list').then(response => {
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
