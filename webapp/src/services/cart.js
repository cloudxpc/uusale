import Vue from 'vue';
import _ from 'lodash';

export const cart = new Vue({
  data: {
    order: {
      comments: '',
      totalAmt: 0,
      orderItems: []
    }
  },
  methods: {
    reset: function () {
      this.order = {
        comments: '',
        totalAmt: 0,
        orderItems: []
      };
    },
    calcTotalAmt: function () {
      this.order.totalAmt = _.sumBy(this.order.orderItems, 'amt');
    },
    add: function (product) {
      let item = _.find(this.order.orderItems, (item) => item.productId === product.id);
      if (item) {
        item.count++;
        item.amt = item.unitPrice * item.count;
      } else {
        this.order.orderItems.push({
          productId: product.id,
          productName: product.name,
          unitPrice: product.price,
          count: 1,
          amt: product.price
        });
      }
      this.calcTotalAmt();
    },
    remove: function (product) {
      let item = _.find(this.order.orderItems, (item) => item.productId === product.id);
      if (item) {
        item.count--;
        item.amt = item.unitPrice * item.count;
        if (item.count <= 0) {
          _.remove(this.order.orderItems, (item) => item.productId === product.id);
        }
      }
      this.calcTotalAmt();
    },
    count: function (product) {
      let item = _.find(this.order.orderItems, (item) => item.productId === product.id);
      return item ? item.count : 0;
    }
  }
});
