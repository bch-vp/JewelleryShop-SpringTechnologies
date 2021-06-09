<template>
  <div class="sign-in-wrapper">
    <v-container>
      <v-row justify="center" row>
        <v-col sm="8" md="6" lg="5" xl="4">
          <div class="sign-in-background">
            <div class="sign-in">
              <div class="title font-weight-regular" style="color: white; text-align: center">
                <span class="text-h6 font-weight-regular center ">
                  {{ text_page.form_component.title.sign_in }}...
                </span><br></div>
              <v-form
                  ref="formSignIn"
                  v-model="valid"
              >
                <div style="color: red">{{ error }}</div>
                <v-text-field
                    dark
                    name="login"
                    v-model="login"
                    :counter="15"
                    :rules="rules.login"
                    v-bind:label=text_page.form_component.input.login.name
                    required
                >
                </v-text-field>

                <v-text-field
                    dark
                    name="password"
                    v-model="password"
                    :counter="20"
                    :rules="rules.password"
                    :append-icon="value ? 'visibility' : 'visibility_off'"
                    @click:append="() => (value = !value)"
                    :type="value ? 'password' : 'text'"
                    v-bind:label=text_page.form_component.input.password.name
                    required
                ></v-text-field>
                <br>
                <v-row>
                  <v-btn @click="showChangePassword" style="color: red" text outlined rounded>
                    <span style="">{{ text_page.form_component.button.go_to_component.change_password_by_email }}</span>
                  </v-btn>
                </v-row>
                <br>
                <v-row>
                  <v-btn @click="showSignUp" class="light-green--text text--accent-2" text outlined rounded>
                    <span style="">{{ text_page.form_component.button.go_to_component.sign_up }}</span>
                  </v-btn>
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
                  <v-btn @click="reset" outlined small fab color="#8C9EFF">
                    <v-icon>autorenew</v-icon>
                  </v-btn>
                </div>
              </v-form>
            </div>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  props: ['showSignUp', 'showChangePassword'],
  data() {
    return {
      text_page: text_page,

      spinnerVisible: false,
      error: undefined,

      value: String,
      valid: false,
      login: '',
      password: '',
      rules: {
        login: [
          v => !!v || this.text_page.form_component.input.login.error.required,
          v => /^[a-zA-Z0-9_.-]+$/.test(v) || this.text_page.form_component.input.login.error.valid_characters,
          v => (v && v.length >= 3) || this.text_page.form_component.input.login.error.min_length,
          v => (v && v.length <= 15) || this.text_page.form_component.input.login.error.max_length,
          v => /^\S*$/.test(v) || this.text_page.form_component.input.login.error.spaces_prohibited,
        ],
        password: [
          v => !!v || this.text_page.form_component.input.password.error.required,
          v => (v && v.length >= 5) || this.text_page.form_component.input.password.error.min_length,
          v => (v && v.length <= 20) || this.text_page.form_component.input.password.error.max_length,
          v => /^\S*$/.test(v) || this.text_page.form_component.input.password.error.spaces_prohibited,
          v => /^[A-Za-z0-9]+$/.test(v) || this.text_page.form_component.input.password.error.valid_characters,
          v => /(?=.*?[a-z])/.test(v) || this.text_page.form_component.input.password.error.one_lower_case_letter,
          v => /(?=.*?[A-Z])/.test(v) || this.text_page.form_component.input.password.error.one_upper_case_letter,
          v => /(?=.*?[0-9])/.test(v) || this.text_page.form_component.input.password.error.one_digit,
        ],
      },
    }
  },
  methods: {
    showSpinner() {
      this.spinnerVisible = true;
    },
    hideSpinner() {
      this.spinnerVisible = false;
    },
    submit: function () {
      if (this.$refs.formSignIn.validate()) {
        this.axios.interceptors.request.use(
            conf => {
              this.showSpinner()
              return conf;
            },
            error => {
              this.hideSpinner()
              return Promise.reject(error);
            }
        );
        this.axios.interceptors.response.use(
            response => {
              this.hideSpinner()
              return response;
            },
            error => {
              this.hideSpinner()
              return Promise.reject(error);
            }
        );
        this.axios({
          method: 'post',
          url: '/ajax?command=sign_in',
          data: {
            login: this.login,
            password: this.password,
            first_name: this.first_name,
            last_name: this.last_name,
            telephone_number: this.telephone_number,
            email: this.email
          }
        }).then(response => {
              console.log('200')
              window.location.href = '/do?command=' + response.data.info;
            },
            ex => {
              if (ex.response.status === 500) {
                window.location.href = '/jsp/error500.jsp'
              }
              if (ex.response.status === 403) {
                window.location.href = '/jsp/error403.jsp'
              }

              if (ex.response.status === 400) {
                this.$refs.formSignIn.reset()
                this.error = ex.response.data.error
              } else {
                this.$refs.formSignIn.reset()
                this.error = this.text_page.form_component.error.not_found
              }
            })
      }
    },
    reset: function () {
      this.$refs.formSignIn.reset()
      this.text_page.form_component.error.login_not_found = undefined
      this.error = undefined
    },
  }
}
</script>

<style scoped>

.sign-in-wrapper {
  width: 100%;
  margin: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  margin-right: -50%;
  transform: translate(-50%, -50%);
}


.sign-in-background {
  box-shadow: 0 0 25px;
  background: rgba(0, 0, 0, 0.93);
  border-radius: 20px;
}

@media screen and (max-width: 400px) {
  .sign-in {
    padding: 2em;
  }
}

@media screen and (min-width: 400px) {
  .sign-in {
    padding-left: 45px;
    padding-right: 45px;
    padding-top: 30px;
    padding-bottom: 30px;
  }
}

</style>