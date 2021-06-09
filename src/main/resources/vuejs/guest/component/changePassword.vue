<template>
  <div class="change-password-wrapper">
    <v-container>
      <v-row justify="center" row>
        <v-col sm="8" md="6" lg="5" xl="4">
          <div class="change-password-background">
            <div class="change-password">
              <div class="title font-weight-regular" style="color: white; text-align: center;">
                <span class="text-h6 font-weight-regular center">
                  {{ text_page.form_component.title.change_password }}...
                </span><br>
                <div style="color: green">{{ success }}</div>
              </div>
              <div style="color: yellow">{{ info }}</div>
              <div style="color: red">{{ error }}</div>
              <v-form
                  ref="formFirstStepLogic"
                  v-model="validFormFirstStepLogic"
              >
                <v-text-field
                    dark
                    :disabled="!isFirstStepLogic"
                    v-model="login"
                    :counter="15"
                    :rules="rules.login"
                    v-bind:label=text_page.form_component.input.login.name
                    required
                ></v-text-field>
              </v-form>
              <v-form
                  ref="formSecondStepLogic"
                  v-model="validFormSecondStepLogic"
              >
                <v-text-field
                    dark
                    :disabled="!isSecondStepLogic"
                    v-model="email"
                    :counter="55"
                    :rules="rules.email"
                    v-bind:label=text_page.form_component.input.email.name
                    required
                ></v-text-field>
                <v-row>
                  <v-col>
                    <v-text-field
                        dark
                        :disabled="!isSecondStepLogic"
                        v-model="newPassword"
                        :counter="20"
                        :rules="rules.newPassword"
                        :append-icon="valueNewPassword ? 'visibility' : 'visibility_off'"
                        @click:append="() => (valueNewPassword = !valueNewPassword)"
                        :type="valueNewPassword ? 'password' : 'text'"
                        v-bind:label=text_page.form_component.input.password.name
                        required
                    ></v-text-field>
                  </v-col>
                  <v-col>
                    <v-text-field
                        dark
                        :disabled="!isSecondStepLogic"
                        v-model="newPasswordRepeat"
                        :counter="20"
                        :rules="rules.newPasswordRepeat"
                        :append-icon="valueNewPasswordRepeat ? 'visibility' : 'visibility_off'"
                        @click:append="() => (valueNewPasswordRepeat = !valueNewPasswordRepeat)"
                        :type="valueNewPasswordRepeat ? 'password' : 'text'"
                        v-bind:label=text_page.form_component.input.password_repeat.name
                        required
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-form>

              <v-form
                  ref="formThirdStepLogic"
                  v-model="validFormThirdStepLogic"
              >
                <div v-if="isThirdStepLogic">
                  <v-text-field
                      dark
                      :disabled="!isThirdStepLogic"
                      v-model="uniqueKey"
                      :rules="rules.uniqueKey"
                      :counter="20"
                      v-bind:label=text_page.form_component.input.unique_key.name
                      required
                  ></v-text-field>
                </div>
              </v-form>

              <br>
              <div align="center">
                <v-progress-circular x
                                     style="margin-right: 15px"
                                     v-if="spinnerVisible"
                                     indeterminate
                                     color="#8C9EFF"
                ></v-progress-circular>

                <v-btn v-if="isFirstStepLogic && !spinnerVisible"
                       @click="submitFormFirstStepLogic"
                       :disabled="!validFormFirstStepLogic"
                       dark small text rounded
                       color="#8C9EFF">
                  Сheck the existence of login
                </v-btn>
                <v-btn v-if="isSecondStepLogic && !spinnerVisible"
                       @click="submitFormSecondStepLogic"
                       :disabled="!validFormSecondStepLogic"
                       dark small text rounded
                       color="#8C9EFF">
                  submit
                </v-btn>
                <v-btn v-if="isThirdStepLogic && !spinnerVisible"
                       @click="submitFormThirdStepLogic"
                       :disabled="!validFormThirdStepLogic"
                       dark small text rounded
                       color="#8C9EFF">
                  submit
                </v-btn>
                <v-btn @click="allFormsReset" outlined small fab color="#8C9EFF">
                  <v-icon>autorenew</v-icon>
                </v-btn>
              </div>
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
      isFormLogin: true,
      isFormLoginAndPassword: false,

      success: undefined,
      info: undefined,
      error: undefined,

      isFirstStepLogic: true,
      isSecondStepLogic: false,
      isThirdStepLogic: false,

      validFormFirstStepLogic: false,
      validFormSecondStepLogic: false,
      validFormThirdStepLogic: false,

      spinnerVisible: false,

      valueNewPassword: String,
      valueNewPasswordRepeat: String,
      valueEmail: String,
      login: '',
      email: '',
      newPassword: '',
      newPasswordRepeat: '',
      uniqueKey: '',
      rules: {
        login: [
          v => !!v || this.text_page.form_component.input.login.error.required,
          v => /^[a-zA-Z0-9_.-]+$/.test(v) || this.text_page.form_component.input.login.error.valid_characters,
          v => (v && v.length >= 3) || this.text_page.form_component.input.login.error.min_length,
          v => (v && v.length <= 15) || this.text_page.form_component.input.login.error.max_length,
          v => /^\S*$/.test(v) || this.text_page.form_component.input.login.error.spaces_prohibited,
        ],
        email: [
          v => !!v || this.text_page.form_component.input.email.error.required,
          v => /^\S*$/.test(v) || this.text_page.form_component.input.email.error.spaces_prohibited,
          v => (v && v.length <= 55) || this.text_page.form_component.input.email.error.max_length,
          v => /^[a-zA-z0-9_.-]{1,35}@[a-zA-z0-9_-]{2,15}\.[a-z]{2,5}$/.test(v)
              || this.text_page.form_component.input.email.error.pattern,
        ],
        newPassword: [
          v => !!v || this.text_page.form_component.input.password.error.required,
          v => (v && v.length >= 5) || this.text_page.form_component.input.password.error.min_length,
          v => (v && v.length <= 20) || this.text_page.form_component.input.password.error.max_length,
          v => /^\S*$/.test(v) || this.text_page.form_component.input.password.error.spaces_prohibited,
          v => /^[A-Za-z0-9]+$/.test(v) || this.text_page.form_component.input.password.error.valid_characters,
          v => /(?=.*?[a-z])/.test(v) || this.text_page.form_component.input.password.error.one_lower_case_letter,
          v => /(?=.*?[A-Z])/.test(v) || this.text_page.form_component.input.password.error.one_upper_case_letter,
          v => /(?=.*?[0-9])/.test(v) || this.text_page.form_component.input.password.error.one_digit,
        ],
        newPasswordRepeat: [
          v => !!v || this.text_page.form_component.input.password_repeat.error.required,
          v => (v && this.newPassword === this.newPasswordRepeat) ||
              this.text_page.form_component.input.password_repeat.error.not_equal,
        ],
        uniqueKey: [
          v => !!v || this.text_page.form_component.input.unique_key.error.not_empty,
          v => (v && v.length === 6) || this.text_page.form_component.input.unique_key.error.length,
          v => /^\d+$/.test(v) || this.text_page.form_component.input.unique_key.error.only_digits,
        ]
      }
    }
  },
  created() {
    this.showFirstStepLogic()
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
    clearAllStepsLogic: function () {
      this.isFirstStepLogic = false
      this.isSecondStepLogic = false
      this.isThirdStepLogic = false

      this.success = undefined
      this.info = undefined
      this.error = undefined
    },
    showFirstStepLogic: function () {
      this.clearAllStepsLogic()
      this.isFirstStepLogic = true
    },
    showSecondStepLogic: function () {
      this.clearAllStepsLogic()
      this.isSecondStepLogic = true
    },
    showThirdStepLogic: function () {
      this.clearAllStepsLogic()
      this.isThirdStepLogic = true
    },
    submitFormFirstStepLogic: function () {
      if (this.$refs.formFirstStepLogic.validate()) {
        this.axios({
          method: 'post',
          url: '/ajax?command=check_login_existence',
          data: {
            login: this.login,
          }
        }).then(response => {
              this.showSecondStepLogic()
            },
            ex => {
              if (ex.response.status === 500) {
                window.location.href = '/jsp/error500.jsp'
              }
              if (ex.response.status === 403) {
                window.location.href = '/jsp/error403.jsp'
              }

              this.error = this.text_page.form_component.error.not_found
            })
      }
    },
    showSpinner() {
      this.spinnerVisible = true;
    },
    hideSpinner() {
      this.spinnerVisible = false;
    },
    submitFormSecondStepLogic: function () {
      if (this.$refs.formSecondStepLogic.validate()) {
        this.axios({
          method: 'post',
          url: '/ajax?command=change_password_by_email',
          data: {
            login: this.login,
            email: this.email,
            new_password: this.newPassword,
          }
        }).then(resp => {
            },
            ex => {
              if (ex.response.status === 500) {
                window.location.href = '/jsp/error500.jsp'
              }
              if (ex.response.status === 403) {
                window.location.href = '/jsp/error403.jsp'
              }

              if (ex.response.status === 401) {
                this.showThirdStepLogic()
                this.info = ex.response.data.error
              } else if (ex.response.status === 400) {
                this.error = ex.response.data.error
              }
            })
      }
    },


    submitFormThirdStepLogic: function () {
      if (this.$refs.formThirdStepLogic.validate()) {
        this.axios({
          method: 'post',
          url: '/ajax?command=change_password_by_email',
          data: {
            login: this.login,
            email: this.email,
            new_password: this.newPassword,
            unique_key: this.uniqueKey
          }
        }).then(resp => {
              this.allFormsReset()
              this.success = this.text_page.form_component.info.success
            },
            ex => {
              if (ex.response.status === 500) {
                window.location.href = '/jsp/error500.jsp'
              }
              if (ex.response.status === 403) {
                window.location.href = '/jsp/error403.jsp'
              }

              if (ex.response.status === 400) {
                this.error = ex.response.data.error
              }
            })
      }
    },
    allFormsReset: function () {
      this.$refs.formFirstStepLogic.reset()
      this.$refs.formSecondStepLogic.reset()
      this.$refs.formThirdStepLogic.reset()
      this.clearAllStepsLogic()
      this.showFirstStepLogic()
    },
  }
}
</script>

<style>
@media screen and (min-width: 400px) {
  .change-password-wrapper {
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
  .change-password-wrapper {
    width: 100%;
    position: relative;
    margin-right: -50%;
  }
}

.change-password-background {
  box-shadow: 0 0 25px;
  background: rgba(0, 0, 0, 0.93);
  border-radius: 20px;
}

@media screen and (max-width: 400px) {
  .change-password {
    margin-top: 4em;
    padding: 2em;
  }
}

@media screen and (min-width: 400px) {
  .change-password {
    padding-left: 45px;
    padding-right: 45px;
    padding-top: 30px;
    padding-bottom: 30px;
  }
}
</style>