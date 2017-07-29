import FastClick from 'fastclick';
import Vue from 'vue'; // get vue
import ElementUI from 'element-ui';
import Axios from 'axios';
import VueProgressBar from 'vue-progressbar'; // get vue-progressbar
import InfiniteScroll from 'vue-infinite-scroll'; // get vue-infinite-scroll
import 'element-ui/lib/theme-default/index.css'

import App from './App.vue'; // get root module
import router from './router';
import store from './store'; // get vuex -> store

Vue.prototype.axios = Axios;
FastClick.attach(document.body); // init fastclick

const options = {
    color: '#fff',
    failedColor: '#874b4b',
    thickness: '3px',
    transition: {
        speed: '0.2s',
        opacity: '0.6s'
    },
    autoRevert: true,
    location: 'top',
    inverse: false
};
Vue.use(VueProgressBar, options);
Vue.use(InfiniteScroll);
Vue.use(ElementUI)

// init
const app = new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');
