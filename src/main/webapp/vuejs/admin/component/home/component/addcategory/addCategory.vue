<template>
  <div class="profile-wrapper">
    <v-container>
      <v-row justify="center" row>
        <v-col sm="9" md="9" lg="8" xl="5">
          <div class="profile-background">
            <div class="profile">
              <v-card-text class="pt-6" style="position: relative;">
                <div v-if="isSuccess" align="center" class="text-h6 font-weight-regular center" style="color: green">
                  {{ text_page.form_component.info.success }}
                </div>
                <div v-if="isError" style="color: red">
                  {{ error }}
                </div>
                <v-form ref="form" v-model="valid">
                  <v-row class="mx-auto" style="width: 70%">
                    <v-col>
                      <v-text-field
                          dark
                          v-model="name"
                          :counter="15"
                          :rules="rules.name"
                          v-bind:label=text_page.form_component.input.name.name
                          required
                      ></v-text-field>
                    </v-col>
                  </v-row>
                  <br>
                  <div align="center">
                    <v-progress-circular style="margin-right: 15px"
                                         v-if="spinnerVisible"
                                         indeterminate
                                         color="#8C9EFF"
                    ></v-progress-circular>
                    <v-btn v-if="!spinnerVisible"
                           @click="submit"
                           :disabled="!valid"
                           dark small text rounded color="#8C9EFF">
                      {{ text_page.form_component.button.submit }}
                    </v-btn>
                    <v-btn @click="reset"
                           outlined small fab color="#8C9EFF">
                      <v-icon>autorenew</v-icon>
                    </v-btn>
                  </div>
                </v-form>
              </v-card-text>
            </div>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  props: ['product'],
  data() {
    return {
      valid: false,
      text_page: text_page,

      spinnerVisible: false,
      isError: false,
      isSuccess: false,

      name: '',
      selectCategory: undefined,
      file: undefined,

      rules: {
        fileRules: [
          v => !!v || 'File is required',
        ],
        name: [
          v => !!v || this.text_page.form_component.input.name.error.required,
          v => (v && v.length >= 3) || this.text_page.form_component.input.name.error.min_length,
          v => (v && v.length <= 15) || this.text_page.form_component.input.name.error.max_length,
          v => /^.{3,15}$/.test(v) || this.text_page.form_component.input.name.error.pattern,
        ],
      }
    }
  },
  created() {
    //info
    this.valid = false
  },

  methods: {
    submit() {
      if (this.$refs.form.validate()) {
        this.spinnerVisible = true
        this.axios({
          method: 'post',
          url: '/ajax?command=create_category',
          data: {
            name: this.name,
          }
        }).then(response => {
              this.isSuccess = true
              var newCategory = {
                id: response.data.id,
                name: response.data.name
              }
              this.$store.state.App.categories.push(newCategory)
              this.await3Seconds()
              this.reset()

              this.spinnerVisible = false
            },
            ex => {
              if (ex.response.status === 500) {
                window.location.href = '/jsp/error500.jsp'
              }
              if (ex.response.status === 403) {
                window.location.href = '/jsp/error403.jsp'
              }

              this.error = ex.response.data.error
              this.reset()
              this.isError = true
              this.await3Seconds()

              this.spinnerVisible = false
            })
      }
    },
    async await3Seconds() {
      await new Promise(resolve => setTimeout(resolve, 3000));
      this.isSuccess = false
      this.isError = false
    },
    reset() {
      this.$refs.form.reset()
    }
  }
}
</script>

<style scoped>
@media screen and (min-width: 400px) {
  .profile-wrapper {
    width: 100%;
    margin: 0;
    position: absolute;
    top: 50%;
    left: 50%;
    margin-right: -50%;
    transform: translate(-50%, -50%);
  }
}

@media screen and (max-width: 400px) {
  .profile-wrapper {
    width: 100%;
    position: relative;
    margin-right: -50%;
  }
}

.profile-background {
  box-shadow: 0 0 2px;
  background: rgba(0, 0, 0, 0.93);
  border-radius: 20px;
}


.profile {
  color: white;

  padding-top: 1em;
  padding-right: 2em;
  padding-left: 2em;
  padding-bottom: 2em;
}

</style>