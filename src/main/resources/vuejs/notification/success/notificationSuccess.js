import Vue from 'vue'
import Axios from 'axios'
import VueAxios from 'vue-axios'
import NotificationSuccess from 'vuejs/notification/success/notificationSuccess.vue'
import vuetify from 'vuejs/plugin/vuetify' // path to vuetify export

Vue.use(VueAxios, Axios)

new Vue({
    el: '#app',
    render: h => h(NotificationSuccess),
    vuetify
})