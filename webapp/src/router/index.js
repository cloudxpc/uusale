import Vue from 'vue';
import Router from 'vue-router';
import NotFound from '../components/NotFound';
import Login from '../components/Login';
import Register from '../components/Register';
import RegisterMch from '../components/RegisterMch';
import User from '../components/User';
import ProductList from '../components/ProductList';
import Merchant from '../components/Merchant';
import MchProductList from '../components/MchProductList';
import Product from '../components/Product';

Vue.use(Router);

export default new Router({
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', name: 'Login', component: Login },
    { path: '/register', name: 'Register', component: Register },
    { path: '/register-mch', name: 'RegisterMch', component: RegisterMch },
    { path: '/user', name: 'User', component: User, children: [
        { path: 'products', name: 'ProductList', component: ProductList },
        { path: '*', name: 'UserNotFound', component: NotFound }
      ] },
    { path: '/mch', name: 'Merchant', component: Merchant, children: [
        { path: 'products', name: 'MchProductList', component: MchProductList },
        { path: '*', name: 'MchNotFound', component: NotFound }
      ] },
    { path: '/product/:mode/:id', name: 'Product', component: Product, props: true },
    { path: '*', name: 'NotFound', component: NotFound }
  ]
})
