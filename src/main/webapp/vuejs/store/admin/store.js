import Vue from 'vue'
import Vuex from 'vuex'
import Profile from 'vuejs/store/admin/module/profile.js'
import Product from 'vuejs/store/admin/module/product.js'
import App from 'vuejs/store/admin/module/app.js'

Vue.use(Vuex)

export const store = new Vuex.Store({
    modules: {
        App,
        Product,
        Profile
    }
})