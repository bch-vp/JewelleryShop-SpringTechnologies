<template>
  <div style="color: white; ">
    <v-app-bar absolute flat fixed dark color="transparent" style="box-shadow: 0 0 50px black;  padding-top: 1em ">
      <v-toolbar-title class="font-weight-medium light-green--text text--accent-2"
                       style="margin-left: 1%; font-size: 40px; font-family: 'Monoton', cursive; ">
        JEWELRY
      </v-toolbar-title>

      <v-btn v-on:click="showHome" :disabled="isHome" text rounded small outlined fab
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
            :disabled="!isHome"
            persistent-hint
            return-object
            single-line
        ></v-select>
      </v-col>
      <v-col cols="3">
        <v-text-field
            :disabled="!isHome"
            v-model="$store.state.App.search"
            v-bind:label=text_page.form_component.button.search
            outlined
            dense
        ></v-text-field>
      </v-col>

      <v-spacer></v-spacer>


      <v-btn v-on:click="showSignIn" :disabled="isSignIn" rounded text>
        <div class="text-subtitle-1 font-weight-black"> {{ text_page.header.sing_in }}</div>
      </v-btn>
      |
      <v-btn v-on:click="showSignUp" :disabled="isSignUp" text rounded style="margin-right: 3%">
        <div class="text-subtitle-1 font-weight-black">{{ text_page.header.sing_up }}</div>
      </v-btn>

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
    </v-app-bar>
  </div>
</template>

<script>
export default {
  props: ['text_page', 'showHome', 'showSignIn', 'showSignUp', 'isHome', 'isSignUp', 'isSignIn'],
  data() {
    return {
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
  },
}
</script>

<style scoped>
.list-item {
  justify-content: center;
}
</style>