import Vue from 'vue'
import Vuex from 'vuex'
import Profile from 'vuejs/store/client/module/profile.js'
import App from 'vuejs/store/client/module/app.js'

Vue.use(Vuex)

export const store = new Vuex.Store({
    modules: {
        App,
        Profile
    }
})