
import axios from 'axios';
import {eventBus} from '../services/eventbus';

export default {
  install: (Vue, options) => {
    Vue.prototype.$axios = axios;

    axios.defaults.baseURL = 'http://localhost:8080/api';
    axios.defaults.headers.common['Authorization'] = 'Bearer ';

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
        if (error.response.data.errorType === 'TokenExpiredException') {
          eventBus.alert('登录超时,请重新登录', '错误');
        } else {
          eventBus.alert(error.response.data.errorMessage, '错误');
        }
      }
      return Promise.reject(error);
    });
  }
};
