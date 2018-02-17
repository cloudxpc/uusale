import axios from 'axios';
import {eventBus} from '../services/eventbus';

export default {
  install: (Vue, options) => {
    Vue.prototype.$axios = axios;

    axios.defaults.baseURL = eventBus.baseUrl + '/api';

    // Add a request interceptor
    axios.interceptors.request.use(config => {
      eventBus.start();
      return config;
    }, error => {
      eventBus.end();
      console.log('Global Request Error Handler: ', error);
      return Promise.reject(error);
    });

    // Add a response interceptor
    axios.interceptors.response.use(response => {
      eventBus.end();
      return response;
    }, error => {
      eventBus.end();
      // console.log('Global Response Error Handler: ', error);
      if (error.response && error.response.data) {
        let msg = error.response.data.errorMessage || error.response.data.error;

        if (error.response.data.errorType === 'TokenExpiredException') {
          msg = '登录超时,请重新登录';
        } else if (error.response.data.errorType === 'TokenNotFoundException') {
          msg = '无法识别身份信息, 请登录';
        } else if (error.response.data.errorType === 'InvalidTokenException') {
          msg = '无效的身份信息, 请重新登录';
        }

        setTimeout(() => {eventBus.alert(msg, '错误');}, 300);
      }
      return Promise.reject(error);
    });
  }
};
