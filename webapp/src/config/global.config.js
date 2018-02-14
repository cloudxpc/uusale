
import {accounting} from 'accounting/accounting.min';
import {eventBus} from '../services/eventbus';

export default {
  install: function(Vue, options) {
    Vue.config.errorHandler = function (err, vm, info) {
      // handle error
      // `info` is a Vue-specific error info, e.g. which lifecycle hook
      // the error was found in. Only available in 2.2.0+
      console.log("Global Vue Error Handler: " + err);
    };


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

    Vue.prototype.$eventBus = eventBus;
  }
}
