import Vue from 'vue';
import Router from 'vue-router';
import NotFound from '../components/NotFound';

Vue.use(Router);

export default new Router({
  routes: [
    // { path: '/user', name: 'UserBinding', component: UserBinding },
    { path: '*', name: 'NotFound', component: NotFound }
  ]
})
