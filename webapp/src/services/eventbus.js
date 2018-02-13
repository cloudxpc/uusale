import Vue from 'vue';

export const eventBus = new Vue({
  methods: {
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

      this.$emit('alert', data);
    },
    start: function () {
      this.$emit('loading-start');
    },
    end: function () {
      this.$emit('loading-end');
    }
  }
});
