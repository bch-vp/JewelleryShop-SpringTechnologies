<template>
  <div>
    <v-app-bar absolute flat fixed dark color="transparent" style="box-shadow: 0 0 50px black; padding-top: 1em ">
      <v-toolbar-title class="font-weight-medium light-green--text text--accent-2"
                       style="margin-left: 1%; font-size: 40px; font-family: 'Monoton', cursive; ">
        JEWELRY
      </v-toolbar-title>

      <v-btn v-on:click="$store.commit('show_home')" :disabled="$store.state.App.isHome" text rounded small outlined fab
             style="margin-left: 4%; margin-right: 1em ">
        <v-icon>home</v-icon>
      </v-btn>
      |
      <v-col cols="2">
        <v-select
            style="padding-left: 1em"
            v-model="selectCategory"
            v-bind:hint=text_page.form_component.button.choose_category
            :items="$store.state.App.categories"
            item-text="name"
            :disabled="!$store.state.App.isHome"
            persistent-hint
            return-object
            single-line
        ></v-select>
      </v-col>
      <v-col cols="3">
        <v-text-field
            :disabled="!$store.state.App.isHome"
            v-model="$store.state.App.search"
            v-bind:label=text_page.form_component.button.search
            outlined
            dense
        ></v-text-field>
      </v-col>


      <v-spacer></v-spacer>

      <v-btn v-on:click="$store.commit('show_profile')" :disabled="$store.state.App.isProfile" rounded text>
        <img v-if="$store.state.Profile.isAvatarExists" :src="$store.state.Profile.avatarUrl" class="avatar"/>
        <v-icon v-if="!$store.state.Profile.isAvatarExists">
          perm_identity
        </v-icon>
        &nbsp
        {{ text_page.header.role }}
      </v-btn>
      |
      <v-btn v-on:click="$store.commit('show_shoppingCart')" :disabled="$store.state.App.isShoppingCart" rounded
             text>
        <v-icon>
          shopping_cart
        </v-icon>
        &nbsp{{ $store.state.App.shoppingCart.length }}
      </v-btn>
      |
      <v-menu offset-y style="margin-left: 3%; margin-right: 3%">
        <template v-slot:activator="{ on, attrs }">
          <v-btn style="margin-right: 1%" v-bind="attrs" v-on="on" text rounded>
            <v-icon>language</v-icon>
            <span v-if="text_page.language">&nbsp;{{ text_page.language }}</span>
            <span v-else>&nbsp;EN</span>
          </v-btn>
        </template>
        <v-list dark>
          <v-list-item class="list-item">
            <v-btn href="do?command=change_language&language=ru" v-if="text_page.language !== 'ru'">
              ru
            </v-btn>
            <div v-else>
              RU
            </div>
          </v-list-item>
          <v-list-item class="list-item">
            <v-btn href="do?command=change_language&language=en" v-if="text_page.language !== 'en'
                                                                                && text_page.language !== ''">
              en
            </v-btn>
            <div v-else>
              EN
            </div>
          </v-list-item>
        </v-list>
      </v-menu>

      <v-btn rounded text href="do?command=sign_out" style="padding-left: 2em">
        <v-icon>exit_to_app</v-icon>
      </v-btn>
    </v-app-bar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      text_page: text_page,
      isAvatarExists: undefined,

      selectCategory: undefined,
    }
  },
  watch: {
    selectCategory() {
      this.$store.state.App.search = ''
      this.$store.commit('set_selectCategory', this.selectCategory)
      this.axios({
        method: 'post',
        url: '/ajax?command=load_all_products_by_category',
        data: this.selectCategory
      }).then(response => {
            var array = response.data.data.sort((a, b) => (a.id < b.id) ? 1 : -1)
            this.$store.commit('set_products', array)
          },
          ex => {
            if (ex.response.status === 500) {
              window.location.href = '/jsp/error500.jsp'
            }
            if (ex.response.status === 403) {
              window.location.href = '/jsp/error403.jsp'
            }
          })
    }
  },
  created() {
    this.axios({
      method: 'get',
      url: '/ajax?command=load_all_orders',
    }).then(resp => {
      var array = resp.data.sort((a, b) => (a.id < b.id) ? 1 : -1)
      array.forEach(order => {
        order.dateCreatedAt = new Date(order.dateCreatedAt).toLocaleDateString()
      })
      this.$store.commit('set_userOrders', array)
    }, ex => {
      if (ex.response.status === 500) {
        window.location.href = '/jsp/error500.jsp'
      }
      if (ex.response.status === 403) {
        window.location.href = '/jsp/error403.jsp'
      }
    })

    this.axios({
      method: 'get',
      url: '/ajax?command=load_profile_image',
    }).then(resp => {
      this.$store.commit('set_isAvatarExists', true)
      this.$store.commit('change_avatarUrl', resp.data.url)
    }, ex => {
      if (ex.response.status === 500) {
        window.location.href = '/jsp/error500.jsp'
      }
      if (ex.response.status === 403) {
        window.location.href = '/jsp/error403.jsp'
      }

      console.log(ex.response.data.url);
      this.$store.commit('set_isAvatarExists', false)
      this.$store.commit('change_avatarUrl', '')
    })

    this.axios({
      method: 'get',
      url: '/ajax?command=load_all_categories'
    }).then(response => {
          var array = response.data.data.sort((a, b) => (a.id > b.id) ? 1 : -1)
          this.$store.commit('set_categories', array)
          this.selectCategory = response.data.data[0]
        },
        ex => {
          if (ex.response.status === 500) {
            window.location.href = '/jsp/error500.jsp'
          }
          if (ex.response.status === 403) {
            window.location.href = '/jsp/error403.jsp'
          }
        })

    this.axios({
      method: 'get',
      url: '/ajax?command=load_shopping_cart'
    }).then(response => {
          this.$store.commit('set_shoppingCart', response.data.data)
        },
        ex => {
          if (ex.response.status === 500) {
            window.location.href = '/jsp/error500.jsp'
          }
          if (ex.response.status === 403) {
            window.location.href = '/jsp/error403.jsp'
          }
        })
  },
  methods: {}
}
</script>

<style scoped>
.list-item {
  justify-content: center;
}

.avatar {
  vertical-align: middle;
  width: 50px;
  height: 50px;
  border-radius: 50%;
}
</style>