import Vue from 'vue'
import Axios from 'axios'
import VueAxios from 'vue-axios'
import Client from 'vuejs/admin/admin.vue'
import vuetify from 'vuejs/plugin/vuetify' // path to vuetify export
import {store} from 'vuejs/store/admin/store.js'

Vue.use(VueAxios, Axios)

new Vue({
    el: '#app',
    render: h => h(Client),
    vuetify,
    store
})