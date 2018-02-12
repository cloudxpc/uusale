import Vue from 'vue';
import Router from 'vue-router';
import NotFound from '../components/NotFound';
import Login from '../components/Login';

Vue.use(Router);

export default new Router({
  routes: [
    { path: '/login', name: 'Login', component: Login },
    { path: '*', name: 'NotFound', component: NotFound }
  ]
})
