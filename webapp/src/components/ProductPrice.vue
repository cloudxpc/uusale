<template>
  <div>
    <h1 class="page_header">{{product.name}}的历史价格</h1>
    <div class="weui-cells">
      <div class="weui-cell" v-for="price in allPrices" :key="price.id">
        <div class="weui-cell__bd">
          <p>{{price.date | date('yyyy年MM月dd日')}}</p>
        </div>
        <div class="weui-cell__ft">{{price.price | currency}}</div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'ProductPrice',
    props: ['id'],
    data: function () {
      return {
        product: {}
      };
    },
    computed: {
      allPrices: function () {
        return this.product && this.product.productPrices ? this.$_.orderBy(this.product.productPrices, 'date', 'desc') : [];
      }
    },
    methods: {
      init: function () {
        this.$axios.get('/product/price?id=' + this.id).then(response => {
          if (response && response.data) {
            this.product = response.data;
          }
        });
      }
    },
    created: function () {
      this.init();
    }
  }
</script>
