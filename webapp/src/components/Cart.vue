<template>
  <div>
    <h1 class="page_header">购物车</h1>
    <div class="weui-form-preview" v-if="$cart.order.orderItems && $cart.order.orderItems.length">
      <div class="weui-form-preview__hd">
        <label class="weui-form-preview__label">总计</label>
        <em class="weui-form-preview__value">{{this.$cart.order.totalAmt | currency}}</em>
      </div>
      <div class="weui-form-preview__bd">
        <div v-for="item in $cart.order.orderItems" :key="item.productId" class="weui-form-preview__item">
          <label class="weui-form-preview__label">{{item.productName}}</label>
          <span class="weui-form-preview__value">{{item.unitPrice | currency}} × {{item.count}} = {{item.amt | currency}}</span>
        </div>
      </div>
      <div class="weui-form-preview__ft">
        <button type="button" class="weui-form-preview__btn weui-form-preview__btn_default" @click="clear">清空</button>
        <button type="button" class="weui-form-preview__btn weui-form-preview__btn_primary" @click="submit">确认订单</button>
      </div>
    </div>
    <div class="weui-loadmore weui-loadmore_line" v-else>
      <span class="weui-loadmore__tips" style="background-color: transparent;">暂无数据</span>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Cart',
    methods: {
      clear: function () {
        this.$eventBus.confirm('确认清空?', () => {
          this.$cart.reset();
        });
      },
      submit: function () {
        this.$eventBus.confirm('确认下单? 订单确认后不可修改, 请仔细核对', () => {
          this.$axios.post('/order/save', this.$cart.order).then(response => {
            if (response && response.status === 200) {
              this.$cart.reset();
              this.$eventBus.toast('下单成功');
            }
          });
        });
      }
    }
  }
</script>
