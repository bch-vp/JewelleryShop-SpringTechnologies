<template>
  <div class="sign-up-wrapper">
    <v-container>
      <v-row justify="center" row>
        <v-col sm="8" md="6" lg="5" xl="4">
          <div class="sign-up-background">
            <div class="sign-up">
              <v-form
                  ref="formSignUp"
                  v-model="valid"
              >
                <div class="title font-weight-regular" style="color: white; text-align: center;">
                  <span class="text-h6 font-weight-regular center">{{
                      text_page.form_component.title.sign_up
                    }}...</span><br>
                </div>
                <div style="color: red">{{ error.login_not_unique }}</div>
                <div style="color: red">{{ error.telephone_number_not_unique }}</div>
                <div style="color: red">{{ error.email_not_unique }}</div>
                <v-text-field
                    dark
                    name="login"
                    v-model="login"
                    :counter="15"
                    :rules="rules.login"
                    v-bind:label=text_page.form_component.input.login.name
                    required
                ></v-text-field>
                <v-row>
                  <v-col>
                    <v-text-field
                        dark
                        name="password"
                        v-model="password"
                        :counter="20"
                        :rules="rules.password"
                        :append-icon="valuePassword ? 'visibility' : 'visibility_off'"
                        @click:append="() => (valuePassword = !valuePassword)"
                        :type="valuePassword ? 'password' : 'text'"
                        v-bind:label=text_page.form_component.input.password.name
                        required
                    ></v-text-field>
                  </v-col>
                  <v-col>
                    <v-text-field
                        dark
                        v-model="passwordRepeat"
                        :counter="20"
                        :rules="rules.passwordRepeat"
                        :append-icon="valuePasswordRepeat ? 'visibility' : 'visibility_off'"
                        @click:append="() => (valuePasswordRepeat = !valuePasswordRepeat)"
                        :type="valuePasswordRepeat ? 'password' : 'text'"
                        v-bind:label=text_page.form_component.input.password_repeat.name
                        required
                    ></v-text-field>
                  </v-col>
                </v-row>

                <v-text-field
                    dark
                    name="first_name"
                    v-model="first_name"
                    :counter="15"
                    :rules="rules.first_name"
                    v-bind:label=text_page.form_component.input.first_name.name
                    required
                ></v-text-field>

                <v-text-field
                    dark
                    name="last_name"
                    v-model="last_name"
                    :counter="15"
                    :rules="rules.last_name"
                    v-bind:label=text_page.form_component.input.last_name.name
                    required
                ></v-text-field>

                <v-text-field
                    dark
                    name="telephone_number"
                    v-model="telephone_number"
                    :counter="17"
                    :rules="rules.telephone_number"
                    v-bind:label=text_page.form_component.input.telephone_number.name
                    required
                ></v-text-field>

                <v-text-field
                    dark
                    name="email"
                    v-model="email"
                    :rules="rules.email"
                    :counter="35"
                    v-bind:label=text_page.form_component.input.email.name
                    required
                ></v-text-field>
                <br>
                <v-row>
                  <v-btn @click="showSignIn" class="light-green--text text--accent-2" text outlined rounded>
                    <span style="">{{ text_page.form_component.button.go_to_component.sign_in }}</span>
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
                  <v-btn @click="reset"
                         outlined small fab color="#8C9EFF">
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
  props: ['showSignIn', 'showNotification'],
  data() {
    return {
      text_page: text_page,
      error: {
        login_not_unique: "",
        telephone_number_not_unique: "",
        email_not_unique: "",
        database_connection_not_received: ""
      },
      valuePassword: String,
      valuePasswordRepeat: String,
      valid: false,
      login: '',
      password: '',
      passwordRepeat: '',
      first_name: '',
      last_name: '',
      telephone_number: '',
      email: '',

      spinnerVisible: false,

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
        passwordRepeat: [
          v => !!v || this.text_page.form_component.input.password_repeat.error.required,
          v => (v && this.passwordRepeat === this.password) ||
              this.text_page.form_component.input.password_repeat.error.not_equal,
        ],
        first_name: [
          v => !!v || this.text_page.form_component.input.first_name.error.required,
          v => (v && v.length >= 3) || this.text_page.form_component.input.first_name.error.min_length,
          v => (v && v.length <= 15) || this.text_page.form_component.input.first_name.error.max_length,
          v => /^\S*$/.test(v) || this.text_page.form_component.input.first_name.error.spaces_prohibited,
          v => /^[a-zA-Z]+$/.test(v) || this.text_page.form_component.input.first_name.error.only_letters
        ],
        last_name: [
          v => !!v || this.text_page.form_component.input.last_name.error.required,
          v => (v && v.length >= 3) || this.text_page.form_component.input.last_name.error.min_length,
          v => (v && v.length <= 15) || this.text_page.form_component.input.last_name.error.max_length,
          v => /^\S*$/.test(v) || this.text_page.form_component.input.last_name.error.spaces_prohibited,
          v => /^[a-zA-Z]+$/.test(v)
              || this.text_page.form_component.input.last_name.error.only_letters
        ],
        telephone_number: [
          v => !!v || this.text_page.form_component.input.telephone_number.error.required,
          v => /^\S*$/.test(v) || this.text_page.form_component.input.telephone_number.error.spaces_prohibited,
          v => /^(\+375\([\d]{2}\)[\d]{3}\-[\d]{2}\-[\d]{2})$/.test(v) ||
              this.text_page.form_component.input.telephone_number.error.pattern
        ],
        email: [
          v => !!v || this.text_page.form_component.input.email.error.required,
          v => /^\S*$/.test(v) || this.text_page.form_component.input.email.error.spaces_prohibited,
          v => (v && v.length <= 55) || this.text_page.form_component.input.email.error.max_length,
          v => /^[a-zA-z0-9_.-]{1,35}@[a-zA-z0-9_-]{2,15}\.[a-z]{2,5}$/.test(v)
              || this.text_page.form_component.input.email.error.pattern,
        ]
      }
    }
  },
  created() {
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
  },
  methods: {
    showSpinner() {
      this.spinnerVisible = true;
    },
    hideSpinner() {
      this.spinnerVisible = false;
    },
    submit: function () {
      if (this.$refs.formSignUp.validate()) {
        this.axios({
          method: 'post',
          url: '/ajax?command=sign_up',
          data: {
            login: this.login,
            password: this.password,
            first_name: this.first_name,
            last_name: this.last_name,
            telephone_number: this.telephone_number,
            email: this.email
          }
        }).then(response => {
              this.showNotification()
            },
            ex => {
              if (ex.response.status === 500) {
                window.location.href = '/jsp/error500.jsp'
              }
              if (ex.response.status === 403) {
                window.location.href = '/jsp/error403.jsp'
              }

              if (ex.response.status === 400) {
                this.$refs.formSignUp.reset()
                this.error.login_not_unique = ex.response.data.error.login_not_unique
                this.error.telephone_number_not_unique = ex.response.data.error.telephone_number_not_unique
                this.error.email_not_unique = ex.response.data.error.email_not_unique
              }
            })
      }
    },
    reset: function () {
      this.$refs.formSignUp.reset()
      this.error.login_not_unique = undefined
      this.error.telephone_number_not_unique = undefined
      this.error.email_not_unique = undefined
    },
  }
}
</script>

<style>
@media screen and (min-width: 400px) {
  .sign-up-wrapper {
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
  .sign-up-wrapper {
    width: 100%;
    position: relative;
    margin-right: -50%;
  }
}

.sign-up-background {
  box-shadow: 0 0 25px;
  background: rgba(0, 0, 0, 0.93);
  border-radius: 20px;
}

@media screen and (max-width: 400px) {
  .sign-up {
    margin-top: 4em;
    padding: 2em;
  }
}

@media screen and (min-width: 400px) {
  .sign-up {
    padding-left: 45px;
    padding-right: 45px;
    padding-top: 30px;
    padding-bottom: 30px;
  }
}
</style>