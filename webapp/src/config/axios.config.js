import axios from 'axios';
import {eventBus} from '../services/eventbus';

export default {
  install: (Vue, options) => {
    Vue.prototype.$axios = axios;

    axios.defaults.baseURL = eventBus.baseUrl + '/api';
    axios.defaults.headers.common['Authorization'] = 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX3R5cGUiOiJNIiwidXNlcl9pZCI6IjVhZDQ3Y2IzLThmYmYtNGFhMi1hNTE3LTEyYTFkNzNkMWRjZiIsImlzcyI6InV1c2FsZSJ9.20EgWC4fohtmU5jaTcGlyl4zzCU1olkiO_aDVvXdIaQ';

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

        eventBus.alert(msg, '错误');
      }
      return Promise.reject(error);
    });
  }
};
