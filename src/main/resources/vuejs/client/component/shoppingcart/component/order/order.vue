<template>
  <div>
    <div class="profile-wrapper">
      <v-container>
        <v-row justify="center" row>
          <v-col sm="9" md="9" lg="8" xl="5">

            <div class="profile-background">
              <div class="profile">
                <div v-if="isSuccess" align="center" class="text-h6" style="color: green">
                  {{ text_page.form_component.info.success }}
                </div>
                <div v-if="isError">
                      <span style="color: red; padding-left: 5px" class="text-h6">
                        Please refresh page some products aren't ACTIVE
                      </span> <br>
                </div>

                <v-row style="padding-top: 1em;">

                  <v-col>
                       <span style="color: white;" class="text-h5">
                          {{ text_page.page_info.total_price }}:&nbsp {{ calculateOrderPrice }}
                       <span class="light-green--text text--accent-2">
                         $
                       </span>
                       </span>
                  </v-col>
                  <v-col>
                    <div align="right">
                      <v-btn
                          @click="show_isShoppingCart"
                          outlined small fab color="white">
                        <v-icon>close</v-icon>
                      </v-btn>
                    </div>
                  </v-col>
                </v-row>

                <v-card-text class="pt-6" style="position: relative;">
                  <v-form
                      ref="form"
                      v-model="valid"
                  >

                    <v-text-field
                        dark
                        v-model="address"
                        :counter="50"
                        :rules="rules.address"
                        v-bind:label=text_page.form_component.input.address.name
                        required
                    ></v-text-field>

                    <div style="margin-top: 1em">
                      <v-textarea
                          filled
                          v-model="comment"
                          color="orange"
                          :counter="100"
                          rows="3"
                          :rules="rules.comment"
                          v-bind:label=text_page.form_component.input.comment.name
                      ></v-textarea>
                    </div>
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
  </div>
</template>

<script>
export default {
  props: ['product', 'show_isShoppingCart'],
  data() {
    return {
      valid: false,
      text_page: text_page,

      spinnerVisible: false,
      isError: false,
      isSuccess: false,

      comment: '',
      address: '',

      rules: {
        address: [
          v => !!v || this.text_page.form_component.input.address.error.required,
          v => (v && v.length >= 3) || this.text_page.form_component.input.address.error.min_length,
          v => (v && v.length <= 50) || this.text_page.form_component.input.address.error.max_length,
          v => /^.{3,50}$/.test(v) || this.text_page.form_component.input.address.error.pattern,
        ],
        comment: [
          v => !!v || this.text_page.form_component.input.comment.error.required,
          v => (v && v.length >= 3) || this.text_page.form_component.input.comment.error.min_length,
          v => (v && v.length <= 100) || this.text_page.form_component.input.comment.error.max_length,
          v => /^.{3,100}$/.test(v) || this.text_page.form_component.input.comment.error.pattern
        ],
      }
    }
  },
  created() {
    //info
    this.valid = false
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
  computed: {
    calculateOrderPrice() {
      var orderPrice = 0
      this.$store.state.App.shoppingCart.forEach(function (product) {
        orderPrice += product.price
      })
      return orderPrice
    }
  },
  methods: {
    submit() {
      if (this.$refs.form.validate()) {
        var newOrder = {
          comment: this.comment,
          address: this.address,
          dateCreatedAt: new Date().toLocaleDateString(),
          totalPrice: this.calculateOrderPrice,
          products: this.$store.state.App.shoppingCart,
          status: 'NOT_CONFIRMED'
        }
        this.axios({
          method: 'post',
          url: '/ajax?command=create_order',
          data: {
            comment: this.comment,
            address: this.address,
          }
        }).then(response => {
              this.reset()
              this.isSuccess = true

              this.$store.commit('add_orderToUserOrders', newOrder)

              this.axios({
                method: 'post',
                url: '/ajax?command=load_all_products_by_category',
                data: {
                  name: this.$store.state.App.selectCategory.name
                }
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
              this.$store.state.App.shoppingCart = []
              this.await3Seconds()
            },
            ex => {
              if (ex.response.status === 500) {
                window.location.href = '/jsp/error500.jsp'
              }
              if (ex.response.status === 403) {
                window.location.href = '/jsp/error403.jsp'
              }

              this.reset()
              this.isError = true

              this.await3Seconds()
            })
      }
    },
    async await3Seconds() {
      await new Promise(resolve => setTimeout(resolve, 3000));
      this.isSuccess = false
      this.isError = false
      this.$store.commit('show_home')
    },
    reset() {
      this.$refs.form.reset()
    },
    showSpinner() {
      this.spinnerVisible = true;
    },
    hideSpinner() {
      this.spinnerVisible = false;
    },
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
  background: rgba(0, 0, 0, 0.100);
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