import Vue from 'vue';
import Router from 'vue-router';
import NotFound from '../components/NotFound';
import Login from '../components/Login';
import Register from '../components/Register';
import RegisterMch from '../components/RegisterMch';
import Main from '../components/Main';
import ProductList from '../components/ProductList';
import Product from '../components/Product';
import ProductPrice from '../components/ProductPrice';
import OrderList from '../components/OrderList';
import Order from '../components/Order';
import User from '../components/User';
import Cart from '../components/Cart';
import ChangeUserInfo from '../components/ChangeUserInfo';
import ChangePassword from '../components/ChangePassword';
import Export from '../components/Export';

Vue.use(Router);

export default new Router({
  routes: [
    {path: '/', redirect: '/login'},
    {path: '/login', name: 'Login', component: Login},
    {path: '/register', name: 'Register', component: Register},
    {path: '/register-mch', name: 'RegisterMch', component: RegisterMch},
    {path: '/main', name: 'Main', component: Main, children: [
      {path: 'products', name: 'ProductList', component: ProductList},
      {path: 'product/:mode/:id', name: 'Product', component: Product, props: true},
      {path: 'product-price/:id', name: 'ProductPrice', component: ProductPrice, props: true},
      {path: 'orders', name: 'OrderList', component: OrderList},
      {path: 'order/:id', name: 'Order', component: Order, props: true},
      {path: 'user', name: 'User', component: User},
      {path: 'cart', name: 'Cart', component: Cart},
      {path: 'change-user-info', name: 'ChangeUserInfo', component: ChangeUserInfo},
      {path: 'change-password', name: 'ChangePassword', component: ChangePassword},
      {path: 'export', name: 'Export', component: Export},
    ]},
    {path: '*', name: 'NotFound', component: NotFound}
  ]
})
