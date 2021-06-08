<template>
  <v-app>
    <div class="background-image">
      <v-container>
        <v-row style="height: 2em">
          <CustomHeader/>
        </v-row>
        <v-row>
          <profile v-if="$store.state.App.isProfile"/>
          <home v-if="$store.state.App.isHome"/>
          <shopping-cart v-if="$store.state.App.isShoppingCart"/>
        </v-row>
      </v-container>
    </div>
    <v-footer
        dark
        padless
    >
      <v-card
          flat
          tile
          width="100%"
          class="mx-auto light-green--text text--accent-2 text-center"
      >
        <v-divider></v-divider>
        <v-card-text class="light-green--text text--accent-2">
          Designed and Developed by&nbsp
          <span class=" light-green--text text--accent-2"
                style="font-size: 20px; font-family: 'Monoton', cursive; ">
             ILYA MURIN
        </span>
        </v-card-text>
      </v-card>
    </v-footer>
  </v-app>
</template>

<script>
import CustomHeader from 'vuejs/client/header.vue'
import Profile from 'vuejs/client/component/profile/profile.vue'
import Home from 'vuejs/client/component/home/home.vue'
import ShoppingCart from 'vuejs/client/component/shoppingcart/shoppingCart.vue'

export default {
  components: {
    CustomHeader,
    Profile,
    Home,
    ShoppingCart
  },
  data() {
    return {
      text_page: text_page,
    }
  },
  created() {
    this.$store.commit('show_home') // cahnge

    this.axios({
      method: 'get',
      url: '/ajax?command=load_profile_image',
    }).then(response => {
      this.$store.commit('set_isAvatarExists', true)
    }, ex => {
      if (ex.response.status === 500) {
        window.location.href = '/jsp/error500.jsp'
      }
      if (ex.response.status === 403) {
        window.location.href = '/jsp/error403.jsp'
      }

      this.$store.commit('set_isAvatarExists', false)
    })
  },
}
</script>

<style>
.background-image {
  background-image: url("/img/background.jpg");
  background-color: black;
  height: 100vh;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  position: relative;
}
</style>