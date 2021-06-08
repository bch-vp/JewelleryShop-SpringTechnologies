import Vue from 'vue'
import Vuex from 'vuex'
import App from 'vuejs/store/guest/module/app.js'

Vue.use(Vuex)

export const store = new Vuex.Store({
    modules: {
        App,
    }
})