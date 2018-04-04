import Vue from 'vue';
import _ from 'lodash';

export const cart = new Vue({
  data: {
    order: {
      totalAmt: 0,
      orderItems: []
    }
  },
  methods: {
    reset: function () {
      this.order.totalAmt = 0;
      this.order.orderItems = [];
    },
    init: function (orderItems) {
      if (!orderItems)
        return;

      this.order.orderItems = orderItems;
      for (let i = 0; i < this.order.orderItems.length; i++) {
        let item = this.order.orderItems[i];
        item.amt = item.unitPrice * item.count;
      }
      this.order.totalAmt = _.sumBy(this.order.orderItems, 'amt');
    },
    addToCart: function (product) {
      let item = _.find(this.order.orderItems, (item) => item.productId === product.id);
      if (item) {
        this.up(item);
      } else {
        this.order.orderItems.push({
          productId: product.id,
          productName: product.name,
          unitPrice: product.price,
          count: 1,
          amt: product.price
        });
        this.order.totalAmt = _.sumBy(this.order.orderItems, 'amt');
      }
    },
    up: function (item) {
      this.calcAmt(item, item.count + 1);
    },
    down: function (item) {
      if (item.count === 1)
        return;

      this.calcAmt(item, item.count - 1);
    },
    remove: function (item) {
      _.remove(this.order.orderItems, (i) => i.productId === item.productId);
      this.order.totalAmt = _.sumBy(this.order.orderItems, 'amt');
    },
    calcAmt: function (item, count) {
      item.count = Number(count);
      if (!item.count)
        item.count = 1;
      item.amt = item.unitPrice * item.count;
      this.order.totalAmt = _.sumBy(this.order.orderItems, 'amt');
    }
  }
});
