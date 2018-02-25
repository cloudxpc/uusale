import Vue from 'vue';
import weui from 'weui.js';

export const eventBus = new Vue({
  data: {
    userInfo: {}
  },
  computed: {
    baseUrl: function () {
      return 'http://localhost:8080';
    },
    imgBaseUrl: function () {
      return 'http://oss-uusale.uutic.com/';
    },
    isMch: function () {
      return this.userInfo.userType === 'M';
    }
  },
  methods: {
    resetUserInfo: function () {
      this.userInfo = {};
    },
    alert: function (msg, titleOrCallback, callback) {
      let data = {};
      data.msg = msg;
      if (titleOrCallback) {
        if (typeof titleOrCallback === 'string')
          data.title = titleOrCallback;
        else
          data.callback = titleOrCallback;
      }
      if (callback)
        data.callback = callback;

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
    },
    confirm: function (msg, func) {
      weui.confirm(msg, func);
    },
    start: function () {
      this.$emit('loading-start');
    },
    end: function () {
      this.$emit('loading-end');
    },
    toast: function (msg) {
      weui.toast(msg, 2000);
    }
  }
});
