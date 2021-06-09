import Vue from 'vue'
import Axios from 'axios'
import VueAxios from 'vue-axios'
import Guest from 'vuejs/guest/guest.vue'
import vuetify from 'vuejs/plugin/vuetify' // path to vuetify export
import {store} from 'vuejs/store/guest/store.js'

Vue.use(VueAxios, Axios)

new Vue({
    el: '#app',
    render: h => h(Guest),
    store,
    vuetify
})