import Vue from 'vue'
import App from './App.vue'
import axios from 'axios';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {router} from "@/utils/Router";
import {store} from "@/utils/Store";

Vue.config.productionTip = false;
Vue.prototype.$axios = axios;
Vue.use(ElementUI);

new Vue({
  render: h => h(App),
  router: router,
  store: store
}).$mount('#app')
