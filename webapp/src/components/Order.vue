<template>
  <div>
    <h1 class="page_header">订单详情</h1>
    <div class="weui-form-preview">
      <div class="weui-form-preview__hd">
        <label class="weui-form-preview__label">总计</label>
        <em class="weui-form-preview__value">{{order.totalAmt | currency}}</em>
      </div>
      <div class="weui-form-preview__bd">
        <div class="weui-form-preview__item">
          <label class="weui-form-preview__label">订单号</label>
          <span class="weui-form-preview__value">{{order.orderNo}}</span>
        </div>
        <div v-for="item in order.orderItems" :key="item.productId" class="weui-form-preview__item">
          <label class="weui-form-preview__label">{{item.productName}}</label>
          <span class="weui-form-preview__value">{{item.unitPrice | currency}} × {{item.count}} = {{item.unitPrice * item.count | currency}}</span>
        </div>
        <div class="weui-form-preview__item">
          <label class="weui-form-preview__label">来自</label>
          <span class="weui-form-preview__value">{{order.userDisplayName}}</span>
        </div>
        <div class="weui-form-preview__item">
          <label class="weui-form-preview__label">时间</label>
          <span class="weui-form-preview__value">{{order.creationTime | date('yyyy-MM-dd hh:mm:ss')}}</span>
        </div>
        <div class="weui-form-preview__item" v-if="order.state === 'C'">
          <label class="weui-form-preview__label">订单状态</label>
          <span class="weui-form-preview__value">已取消</span>
        </div>
      </div>
      <div class="weui-form-preview__ft" v-if="$eventBus.isMch">
        <button type="button" class="weui-form-preview__btn weui-form-preview__btn_default" @click="unread">标记为未读</button>
        <button type="button" class="weui-form-preview__btn weui-form-preview__btn_primary" @click="remove">删除订单</button>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Order',
    props: ['id'],
    data: function () {
      return {
        order: {}
      };
    },
    created: function () {
      this.init();
    },
    methods: {
      init: function () {
        this.$axios.get('order?id=' + this.id).then(response => {
          if (response && response.data) {
            this.order = response.data;
            if (this.$eventBus.isMch)
              this.read();
          }
        });
      },
      remove: function () {
        this.$eventBus.confirm('确认删除订单?', () => {
          this.$axios.get('/order/delete?id=' + this.id).then(response => {
            if(response && response.status === 200) {
              this.$router.go(-1);
              this.$eventBus.toast('订单已删除');
            }
          });
        });
      },
      unread: function () {
        this.$axios.get('/order/unread?id=' + this.id).then(response => {
          if(response && response.status === 200) {
            this.$eventBus.toast('成功标记未读');
          }
        });
      },
      read: function () {
        this.$axios.get('/order/read?id=' + this.id);
      }
    }
  }
</script>

<style scoped>
  .weui-form-preview__ft {
    padding: 0 15px;
  }

  .weui-media-box__info {
    padding: 0;
    margin-top: 15px;
    margin-bottom: 15px;
  }
</style>
