import Vue from 'vue';
import Router from 'vue-router';
import NotFound from '../components/NotFound';
import Login from '../components/Login';
import Register from '../components/Register';
import RegisterMch from '../components/RegisterMch';
import User from '../components/User';
import Merchant from '../components/Merchant';

Vue.use(Router);

export default new Router({
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', name: 'Login', component: Login },
    { path: '/register', name: 'Register', component: Register },
    { path: '/mch/register', name: 'RegisterMch', component: RegisterMch },
    { path: '/user', name: 'User', component: User },
    { path: '/mch', name: 'Merchant', component: Merchant },
    { path: '*', name: 'NotFound', component: NotFound }
  ]
})
