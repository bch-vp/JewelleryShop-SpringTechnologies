import Vue from 'vue'
import Axios from 'axios'
import VueAxios from 'vue-axios'
import Client from 'vuejs/client/client.vue'
import vuetify from 'vuejs/plugin/vuetify' // path to vuetify export
import {store} from 'vuejs/store/client/store.js'

Vue.use(VueAxios, Axios)

new Vue({
    el: '#app',
    render: h => h(Client),
    vuetify,
    store
})