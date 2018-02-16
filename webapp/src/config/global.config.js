
import {accounting} from 'accounting/accounting.min';
import {eventBus} from '../services/eventbus';
import {cart} from '../services/cart';
import _ from 'lodash';

export default {
  install: function(Vue, options) {
    Vue.config.errorHandler = function (err, vm, info) {
      // handle error
      // `info` is a Vue-specific error info, e.g. which lifecycle hook
      // the error was found in. Only available in 2.2.0+
      console.log("Global Vue Error Handler: " + err);
    };

    Vue.prototype.$eventBus = eventBus;
    Vue.prototype.$cart = cart;
    Vue.prototype.$_ = _;

    Vue.filter('currency', function (value) {
      if (!value) value = 0;
      return accounting.formatMoney(value, '￥', 2);
    });
    Vue.prototype.$currency = function (value) {
      if (!value) value = 0;
      return accounting.formatMoney(value, '￥', 2);
    };
    Vue.prototype.$amount = function (value) {
      if (!value) value = 0;
      return accounting.unformat(accounting.formatMoney(value, '￥', 2));
    };

    Vue.filter('date', function (value, fmt) {
      let date = new Date(value);
      if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
      }
      let o = {
        'M+': date.getMonth() + 1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds()
      };
      for (let k in o) {
        if (new RegExp(`(${k})`).test(fmt)) {
          let str = o[k] + '';
          fmt = fmt.replace(RegExp.$1, RegExp.$1.length === 1 ? str : ('00' + str).substr(str.length));
        }
      }
      return fmt;
    });
  }
}
