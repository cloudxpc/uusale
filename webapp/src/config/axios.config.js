
import axios from 'axios';
import {eventBus} from '../services/eventbus';
import weui from 'weui.js';

export default {
  install: (Vue, options) => {
    Vue.prototype.$axios = axios;

    axios.defaults.baseURL = 'http://localhost:8080/api';
    axios.defaults.headers.common['Authorization'] = 'Bearer ';

    // Add a request interceptor
    axios.interceptors.request.use(config => {
      eventBus.$emit('loading-start');
      return config;
    }, error => {
      eventBus.$emit('loading-end');
      console.log('Global Request Error Handler: ', error);
      return Promise.reject(error);
    });

    // Add a response interceptor
    axios.interceptors.response.use(response => {
      eventBus.$emit('loading-end');
      return response;
    }, error => {
      eventBus.$emit('loading-end');
      // console.log('Global Response Error Handler: ', error);
      if (error.response && error.response.data) {
        if (error.response.data.errorType === 'TokenExpiredException') {
          weui.alert('登录超时,请重新登录', { title: '错误' });
        } else {
          weui.alert(error.response.data.errorMessage, { title: '错误' });
        }
      }
      return Promise.reject(error);
    });
  }
};
