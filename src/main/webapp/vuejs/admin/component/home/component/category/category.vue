<template>
  <v-list-item>
    <v-list-item-action>
      <v-list-item-title>ID:
        <v-btn
            style="margin-left: 1em"
            fab
            small
            depressed
            color="black" class="light-green--text text--accent-2"
        >
          {{ category.id }}
        </v-btn>
      </v-list-item-title>
    </v-list-item-action>

    <div v-if="isError" class="text-subtitle-1 font-weight-medium mb-2">
          <span style="color: orangered">{{ text_page.form_component.error.notification }}:&nbsp
          </span>{{ error }}
    </div>

    <v-list-item-content v-if="!spinnerVisible && !isError && !isEdit">
      <v-list-item-title>
        <div align="left" style="padding-left: 1em">
          {{ text_page.form_component.input.name.name }}: <strong> {{ category.name }}</strong>
        </div>
      </v-list-item-title>
    </v-list-item-content>
    <v-list-item-content v-if="!spinnerVisible && !isError && isEdit">
      <v-list-item-title>
        <div align="left" style="padding-left: 1em">
          <v-form
              ref="form"
              v-model="valid"
          >
            <v-text-field
                dark
                style="width: 50%"
                v-model="name"
                :counter="15"
                :rules="rules.name"
                v-bind:label=text_page.form_component.input.name.name
                required
            ></v-text-field>
          </v-form>
        </div>
      </v-list-item-title>
    </v-list-item-content>


    <v-progress-circular style="margin-right: 15px"
                         v-if="spinnerVisible"
                         indeterminate
                         color="#8C9EFF"
    ></v-progress-circular>
    <v-list-item-action v-if="!isError && !spinnerVisible">
      <v-row v-if="isEdit">
        <v-btn v-if="!spinnerVisible"
               style="margin-right: 1em"
               @click="submitEdit"
               :disabled="!valid"
               outlined small fab color="#8C9EFF">
          <v-icon>
            add_task
          </v-icon>
        </v-btn>
        <v-btn @click="resetEdit()"
               style="margin-right: 3em"
               outlined small fab color="#8C9EFF">
          <v-icon>autorenew</v-icon>
        </v-btn>
        <v-btn @click="isEdit = !isEdit" style="margin-right: 2em" outlined text>
          <v-icon>
            close
          </v-icon>
        </v-btn>
      </v-row>
      <v-row v-if="!isEdit">
        <v-btn @click="isEdit = !isEdit" :disabled="category.name === 'others'" style="margin-right: 2em" outlined text>
          <v-icon>
            edit
          </v-icon>
        </v-btn>
        <v-btn @click="removeCategory" :disabled="category.name === 'others'" style="margin-right: 2em" outlined text>
          <v-icon>
            delete_outline
          </v-icon>
        </v-btn>
      </v-row>
    </v-list-item-action>
  </v-list-item>
</template>

<script>
export default {
  props: ['category'],
  data() {
    return {
      text_page: text_page,

      spinnerVisible: false,
      name: '',
      isError: false,
      isEdit: false,
      isSuccess: false,
      error: undefined,
      valid: false,

      rules: {
        name: [
          v => !!v || this.text_page.form_component.input.name.error.required,
          v => (v && v.length >= 3) || this.text_page.form_component.input.name.error.min_length,
          v => (v && v.length <= 15) || this.text_page.form_component.input.name.error.max_length,
          v => /^.{3,15}$/.test(v) || this.text_page.form_component.input.name.error.pattern,
        ],
      },
    }
  },
  methods: {
    submitEdit: function (id_user, id_status, status) {
      this.spinnerVisible = true
      this.axios({
        method: 'post',
        url: '/ajax?command=update_category_name',
        data: {
          id: String(this.category.id),
          name: this.name
        }
      }).then(response => {
            this.category.name = this.name
            this.isEdit = false

            this.spinnerVisible = false
          },
          ex => {
            if (ex.response.status === 500) {
              window.location.href = '/jsp/error500.jsp'
            }
            if (ex.response.status === 403) {
              window.location.href = '/jsp/error403.jsp'
            }
            if (ex.response.status === 400) {
              this.error = text_page.form_component.error.name_not_unique
            } else {
              this.error = text_page.form_component.error.need_reload_page
            }

            this.isError = true
            this.spinnerVisible = false

            this.await3Seconds()
          })
    },
    async await3Seconds() {
      await new Promise(resolve => setTimeout(resolve, 3000));
      this.isError = false
    },
    removeCategory() {
      this.axios({
        method: 'post',
        url: '/ajax?command=remove_category',
        data: {
          id: String(this.category.id)
        }
      }).then(response => {
            if (this.category.id === this.$store.state.App.selectCategory.id) {
              this.$store.state.App.products = []
            } else if (this.$store.state.App.selectCategory.id === 1) {
              this.spinnerVisible = true
              this.axios({
                method: 'post',
                url: '/ajax?command=load_all_products_by_category',
                data: {
                  name: this.$store.state.App.selectCategory.name
                }
              }).then(response => {
                    var array = response.data.data.sort((a, b) => (a.id < b.id) ? 1 : -1)
                    this.$store.commit('set_products', array)

                    this.spinnerVisible = false
                  },
                  ex => {
                    if (ex.response.status === 500) {
                      window.location.href = '/jsp/error500.jsp'
                    }
                    if (ex.response.status === 403) {
                      window.location.href = '/jsp/error403.jsp'
                    }

                    this.spinnerVisible = false
                  })
            }
            this.$store.state.App.categories.splice(this.$store.state.App.categories.indexOf(this.category), 1)
          },
          ex => {
            if (ex.response.status === 500) {
              window.location.href = '/jsp/error500.jsp'
            }
            if (ex.response.status === 403) {
              window.location.href = '/jsp/error403.jsp'
            }
            this.isError = true
          })
    },
    resetEdit() {
      this.$refs.form.reset()
    }
  }
}
</script>

<style scoped>

</style>