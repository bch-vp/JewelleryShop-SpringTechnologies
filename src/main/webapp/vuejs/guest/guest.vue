<template>
  <v-app>
    <div class="background-image">
      <v-container>
        <v-row style="height: 2em">
          <CustomHeader
              :showHome="showHome"
              :showSignIn="showSignIn"
              :showSignUp="showSignUp"
              :text_page="text_page"
              :isHome="isHome"
              :isSignUp="isSignUp"
              :isSignIn="isSignIn"/>
        </v-row>
        <v-row>
          <home v-if="isHome"
                :showSignIn="showSignIn"/>
          <info v-if="isInfo"
                :showHome="showHome"/>
          <sign-in v-if="isSignIn"
                   :error="text_page.form_component.input.error"
                   :showSignUp="showSignUp"
                   :showChangePassword="showChangePassword"/>
          <sign-up v-if="isSignUp"
                   :showSignIn="showSignIn"
                   :showNotification="showNotification"
                   :showChangePassword="showChangePassword"/>
          <notification v-if="isNotification"
                        :text_page="text_page"
                        :showHome="showHome"/>
          <change-password v-if="isChangePassword"/>
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
          <span class="light-green--text text--accent-2"
                style="font-size: 20px; font-family: 'Monoton', cursive; ">
             ILYA MURIN
        </span>
        </v-card-text>
      </v-card>
    </v-footer>
  </v-app>
</template>

<script>
import CustomHeader from 'vuejs/guest/header.vue'
import Info from 'vuejs/guest/component/home.vue'
import SignIn from 'vuejs/guest/component/signIn.vue'
import SignUp from 'vuejs/guest/component/signUp.vue'
import Notification from 'vuejs/guest/component/notification.vue'
import ChangePassword from 'vuejs/guest/component/changePassword.vue'
import Home from 'vuejs/guest/component/home/home.vue'

export default {
  components: {
    CustomHeader,
    Info,
    SignIn,
    SignUp,
    ChangePassword,
    Home,
    Notification
  },
  data() {
    return {
      text_page: text_page,
      isHome: false,
      isInfo: false,
      isSignIn: false,
      isSignUp: false,
      isNotification: false,
      isChangePassword: false
    }
  },
  created() {
    this.showIsInfo()
  },
  methods: {
    clearAllComponents() {
      this.isSignIn = false
      this.isSignUp = false
      this.isHome = false
      this.isNotification = false
      this.isChangePassword = false
      this.isInfo = false
      this.text_page.form_component.error.login_not_found = undefined
    },
    showIsInfo() {
      this.clearAllComponents()
      this.isInfo = true
    },
    showHome() {
      this.clearAllComponents()
      this.isHome = true
    },
    showSignUp() {
      this.clearAllComponents()
      this.isSignUp = true
    },
    showSignIn() {
      this.clearAllComponents()
      this.isSignIn = true
    },
    showNotification() {
      this.clearAllComponents()
      this.isNotification = true
    },
    showChangePassword() {
      this.clearAllComponents()
      this.isChangePassword = true
    }
  }
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