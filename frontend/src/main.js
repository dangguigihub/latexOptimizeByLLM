import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

axios.interceptors.request.use(function (config) {
  const token = localStorage.getItem("token")
  console.log(token);
  // 当token存在时，添加Authorization头
  if (token) {
    config.headers.Authorization = token
  }
  return config;
}, error => {
  return Promise.reject(error)
})
// main.js
Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
