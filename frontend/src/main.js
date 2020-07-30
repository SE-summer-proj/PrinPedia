import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import {router} from "@/router";
import {store} from "@/store";
// import Mock from '@/../mock';

Vue.config.productionTip = false;
Vue.use(ElementUI);
// Vue.use(Mock);

new Vue({
  render: h => h(App),
  router: router,
  store: store
}).$mount('#app')
