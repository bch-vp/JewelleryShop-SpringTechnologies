<template>
  <div>
    <div v-if="isError" class="text-subtitle-1 font-weight-medium mb-2">
          <span style="color: orangered">{{ text_page.form_component.error.notification }}:&nbsp
          </span>{{ error }}
    </div>
    <div align="center" v-if="isSuccess && !isError" class="text-subtitle-1 font-weight-medium mb-2">
      <span style="color: greenyellow">{{ text_page.form_component.info.success }}</span>
    </div>
    <div v-if="!isError && !isSuccess">
      <v-form
          ref="form"
          v-model="valid"
      >
        <div>
          <v-text-field
              dark
              v-model="name"
              :counter="15"
              :rules="rules.name"
              v-bind:label=text_page.form_component.input.name.name
              required
          ></v-text-field>
        </div>
        <div>
          <v-text-field
              dark
              prefix="$"
              v-model="price"
              :rules="rules.price"
              v-bind:label=text_page.form_component.input.price.name
              required
          ></v-text-field>
        </div>
        <div>
          <v-textarea
              filled
              v-model="info"
              color="orange"
              :counter="100"
              rows="3"
              :rules="rules.info"
              v-bind:label=text_page.form_component.input.info.name
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
    </div>
  </div>
</template>

<script>
export default {
  props: ['product'],
  data() {
    return {
      valid: false,
      text_page: text_page,

      error: undefined,

      spinnerVisible: false,
      isError: false,
      isSuccess: false,

      name: this.product.name,
      info: this.product.info,
      price: String(this.product.price),

      rules: {
        name: [
          v => !!v || this.text_page.form_component.input.name.error.required,
          v => (v && v.length >= 3) || this.text_page.form_component.input.name.error.min_length,
          v => (v && v.length <= 15) || this.text_page.form_component.input.name.error.max_length,
        ],
        price: [
          v => !!v || this.text_page.form_component.input.price.error.required,
          v => (v && Number(v) >= 0.01) || this.text_page.form_component.input.price.error.min_length,
          v => (v && Number(v) <= 99999999.99) || this.text_page.form_component.input.price.error.max_length,
          v => /^[0-9]{1,10}(\.[0-9]{2})?$/.test(v) || this.text_page.form_component.input.price.error.pattern,
        ],
        info: [
          v => !!v || this.text_page.form_component.input.info.error.required,
          v => (v && v.length >= 3) || this.text_page.form_component.input.info.error.min_length,
          v => (v && v.length <= 100) || this.text_page.form_component.input.info.error.max_length,
          v => /^.{3,100}$/.test(v) || this.text_page.form_component.input.info.error.pattern
        ],
      }
    }
  },
  watch: {
    name: function () {
      this.checkChange()
    },
    info: function () {
      this.checkChange()
    },
    price: function () {
      this.checkChange()
    },
  },
  created() {
    this.valid = false
  },
  beforeUpdate() {
    this.checkChange()
  },
  methods: {
    submit() {
      if (this.$refs.form.validate()) {
        this.spinnerVisible = true
        this.axios({
          method: 'post',
          url: '/ajax?command=update_product_info',
          data: {
            id: String(this.product.id),
            name: this.name,
            info: this.info,
            price: this.price,
          }
        }).then(response => {
              this.product.name = this.name
              this.product.info = this.info
              this.product.price = this.price

              this.reset()

              this.isError = false
              this.isSuccess = true

              this.await1Seconds()

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
              } else{
                this.error = text_page.form_component.error.notification
              }

              this.reset()
              this.isSuccess = false
              this.isError = true

              this.spinnerVisible = false

              this.await1Seconds()
            })
      }
    },
    async await1Seconds() {
      await new Promise(resolve => setTimeout(resolve, 2000));
      this.isSuccess = false
      this.isError = false
    },
    reset() {
      this.name = this.product.name
      this.info = this.product.info
      this.price = String(this.product.price)
    },
    checkChange() {
      if (this.name === this.product.name
          && this.info === this.product.info
          && this.price === String(this.product.price)) {
        this.valid = false
      } else {
        if (this.$refs.form.validate()) {
          this.valid = true
        } else {
          this.valid = false
        }
      }
    }
  }
}
</script>

<style scoped>

</style>