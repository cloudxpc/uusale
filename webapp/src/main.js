// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import router from './router';
import 'weui';
import weui from 'weui.js';
import {eventBus} from './services/eventbus';
import AxiosConfig from './config/axios.config';
import GlobalConfig from './config/global.config';
import './main.css';

Vue.use(AxiosConfig);
Vue.use(GlobalConfig);

Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  indicator: null,
  router,
  components: {App},
  template: '<App/>',
  created() {
    eventBus.$on('loading-start', () => {
      if (!this.indicator)
        this.indicator = weui.loading('加载中');
    });
    eventBus.$on('loading-end', () => {
      if (this.indicator) {
        this.indicator.hide();
        this.indicator = null;
      }
    });
    eventBus.$on('alert', (data) => {
      if (data.callback && data.title) {
        weui.alert(data.msg, data.callback, {title: data.title});
      } else {
        if (data.callback)
          weui.alert(data.msg, data.callback);
        else if (data.title)
          weui.alert(data.msg, {title: data.title});
        else
          weui.alert(data.msg);
      }
    });
  },
  destroyed() {
    eventBus.$off('loading-start');
    eventBus.$off('loading-end');
  }
});
