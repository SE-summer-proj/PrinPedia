import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {router} from "@/router";
import {store} from "@/store";
// import Mock from '@/../mock';
import axios from 'axios';

axios.defaults.withCredentials=true;//让ajax携带cookie
Vue.prototype.$axios = axios;

Vue.config.productionTip = false;
Vue.use(ElementUI);
// Vue.use(Mock);
Vue.prototype.$axios = axios;

new Vue({
  render: h => h(App),
  router: router,
  store: store
}).$mount('#app')


