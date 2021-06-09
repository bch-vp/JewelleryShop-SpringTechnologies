import Vue from 'vue'
import Error404 from 'vuejs/error/error.vue'
import vuetify from 'vuejs/plugin/vuetify' // path to vuetify export

new Vue({
    el: '#app',
    render: h => h(Error404),
    vuetify
})