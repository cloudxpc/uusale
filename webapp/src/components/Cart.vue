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
          <label class="weui-form-preview__label" style="max-width: 100px;">{{item.productName}}</label>
          <span class="weui-form-preview__value" style="display: flex; flex-direction: row; align-items: center; justify-content: flex-end;">
            <span style="min-width: 70px;flex-shrink: 0;">{{item.unitPrice | currency}}</span>
            <span style="width: 20px;text-align: center;flex-shrink: 0;">×</span>
            <input type="tel" class="count" :value="item.count" @input="$cart.calcAmt(item, $event.target.value)"/>
            <span style="width: 20px;text-align: center;flex-shrink: 0;">=</span>
            <span style="min-width: 70px;flex-shrink: 0;">{{item.amt | currency}}</span>
            <button style="margin-left: 10px;flex-shrink: 0;" type="button" class="num-btn" @click="removeItem(item)"></button>
          </span>
        </div>
      </div>
      <div class="weui-form-preview__ft">
        <button type="button" class="weui-form-preview__btn weui-form-preview__btn_default" @click="clear">清空</button>
        <button type="button" class="weui-form-preview__btn weui-form-preview__btn_primary" @click="saveCart">保存</button>
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
      },
      saveCart: function () {
        this.$axios.post('/cart/save', this.$cart.order).then(response => {
          if (response && response.status === 200) {
            this.$eventBus.toast('购物车已保存');
          }
        });
      },
      removeItem: function (item) {
        this.$eventBus.confirm('确认从购物车中移除该商品?', () => {
          this.$cart.remove(item);
        });
      }
    }
  }
</script>

<style scoped>
  .count {
    width: 40px;
    height: 20px;
    vertical-align: middle;
    /*text-align: center;*/
  }

  .num-btn {
    position: relative;
    height: 20px;
    width: 20px;
    padding: 0;
    border: 1px solid #888888;
    box-sizing: border-box;
    border-radius: 50%;
    line-height: 0;
    color: #888888;
    background-color: transparent;
    outline: none;
  }

  .num-btn:before {
    content: '';
    position: absolute;
    width: 10px;
    height: 2px;
    left: 50%;
    top: 50%;
    margin-left: -5px;
    margin-top: -1px;
    background-color: #888888;
  }

  .num-btn:active {
    background-color: #c0c0c0;
  }
</style>
