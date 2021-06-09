export default {
    state: {
        isHome: false,
        IsHome_users: false,
        IsHome_products: false,
        IsHome_addProduct: false,
        IsHome_orders: false,

        isShoppingCart: false,
        isProfile: false,

        categories: [],
        selectCategory: undefined,

        users: [],
        products: [],
        shoppingCart: [],
        userOrders: [],

        search: ''
    },
    getters: {
        products: state => {
            return state.products
        }
    },
    mutations: {

        show_home(state) {
            this.commit('off_allComponents')
            state.isHome = true
        },
        show_shoppingCart(state) {
            this.commit('off_allComponents')
            state.isShoppingCart = true
        },
        show_profile(state) {
            this.commit('off_allComponents')
            state.isProfile = true
        },

        off_allComponents(state) {
            state.isHome = false
            state.isShoppingCart = false
            state.isProfile = false
        },

        set_userOrders(state, data) {
            state.userOrders = data
        },
        add_orderToUserOrders(state, data) {
            state.userOrders.push(data)
        },
        remove_orderFromUserOrders(state, data) {
            state.userOrders.splice(state.userOrders.indexOf(data), 1)
        },

        set_categories(state, data) {
            state.categories = data
        },
        set_selectCategory(state, data) {
            state.selectCategory = data
        },


        set_users(state, data) {
            state.users = data
        },
        add_users(state, data) {
            state.users.push(data)
        },
        remove_users(state, data) {
            state.users.splice(state.users.indexOf(data), 1)
        },

        set_products(state, data) {
            state.products = data
        },
        add_productToProducts(state, data) {
            state.products.push(data)
        },
        remove_productToProducts(state, data) {
            state.products.splice(state.products.indexOf(data), 1)
        },

        set_shoppingCart(state, data) {
            state.shoppingCart = data
        },
        add_productToShoppingCart(state, data) {
            state.shoppingCart.push(data)
        },
        remove_productToShoppingCart(state, data) {
            state.shoppingCart.splice(state.shoppingCart.indexOf(data), 1)
        },
    }
}