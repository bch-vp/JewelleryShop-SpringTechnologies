<template>
  <v-list-item>
    <v-list-item-action>
      <v-list-item-title style="width: 5em">
        <span style="color: white;" class="text-h6">
                {{ order.totalPrice }}
                <span class="light-green--text text--accent-2">
                  $
                </span>
              </span>
      </v-list-item-title>
    </v-list-item-action>
    <v-list-item-content>
    </v-list-item-content>
    <v-list-item-action>
      <div v-if="isError" class="text-subtitle-1 font-weight-medium mb-2">
          <span style="color: orangered">{{ text_page.form_component.error.notification }}:&nbsp
          </span>{{ text_page.form_component.error.need_reload_page }}
      </div>
      <v-row v-if="!isError">
        <v-col>
          <v-menu offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  dark
                  outlined text
                  v-bind="attrs"
                  v-on="on"
              >
                {{ text_page.form_component.button.info }}
              </v-btn>
            </template>
            <v-list>
              <v-list-item>
                <v-list-item-title>
                  <v-container>
                    <v-row>
                      {{ text_page.form_component.input.login.name }}:&nbsp
                      <strong> {{ order.login }}</strong>
                    </v-row>
                    <v-row style="padding-top: 4px">
                      {{ text_page.form_component.input.email.name }}:&nbsp
                      <strong> {{ order.email }}</strong>
                    </v-row>
                    <v-row style="padding-top: 4px">
                      {{ text_page.form_component.input.telephone_number.name }}:&nbsp
                      <strong> {{ order.telephone_number }}</strong>
                    </v-row>
                    <v-row style="padding-top: 4px">
                      {{ text_page.form_component.input.address.name }}:&nbsp
                      <strong> {{ order.address }}</strong>
                    </v-row>
                    <v-row style="padding-top: 4px">
                      {{ text_page.form_component.input.comment.name }}:&nbsp
                      <strong> {{ order.comment }}</strong>
                    </v-row>
                  </v-container>
                </v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-col>
        <v-col style="padding-right: 4em">
          <v-menu offset-y>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  dark
                  outlined text
                  v-bind="attrs"
                  v-on="on"
              >
                {{ text_page.form_component.button.show_products }}
              </v-btn>
            </template>
            <v-list>
              <v-list-item
                  v-for="order in order.products"
                  :key="order.id"
              >
                <v-list-item-title>
                  <v-container>
                    <v-row>
                      <v-col>
                        {{ order.name }}
                      </v-col>
                      <v-col>
                        <strong> {{ order.price }}&nbsp$</strong>

                      </v-col>
                    </v-row>
                  </v-container>

                </v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-col>
        <v-col>
          <v-btn v-if="order.status === 'CONFIRMED' && !spinnerVisible" :disabled="true" style="width: 13em" outlined
                 text>
            {{ text_page.form_component.button.confirmed }}
          </v-btn>
          <v-btn @click="submit" color="red" text outlined v-if="order.status === 'NOT_CONFIRMED' && !spinnerVisible"
                 style="width: 13em">
            {{ text_page.form_component.button.not_confirmed }}
          </v-btn>
          <v-progress-circular style="margin-right: 15px"
                               v-if="spinnerVisible"
                               indeterminate
                               color="#8C9EFF"
          ></v-progress-circular>
        </v-col>
      </v-row>
    </v-list-item-action>
  </v-list-item>
</template>

<script>
export default {
  props: ['order'],
  data() {
    return {
      text_page: text_page,
      spinnerVisible: false,
      isError: false,
    }
  },
  methods: {
    submit() {
      this.spinnerVisible = true
      this.axios({
        method: 'post',
        url: '/ajax?command=update_order_status',
        data: {
          id_status: '1',
          id_order: String(this.order.id),
        }
      }).then(response => {
            this.order.status = 'CONFIRMED'
            var array = this.$store.state.App.userOrders.sort((a, b) => (a.status < b.status) ? 1 : -1)
            array.forEach(order => {
              order.dateCreatedAt = new Date(order.dateCreatedAt).toLocaleDateString()
            })

            var arrayResult = []
            var arrayConcat = array.filter(function (order) {
              return order.status === 'NOT_CONFIRMED';
            })
            arrayConcat = arrayConcat.sort((a, b) => (a.id < b.id) ? 1 : -1)
            arrayResult = arrayResult.concat(arrayConcat)

            arrayConcat = array.filter(function (order) {
              return order.status === 'CONFIRMED';
            })
            arrayConcat = arrayConcat.sort((a, b) => (a.id < b.id) ? 1 : -1)
            arrayResult = arrayResult.concat(arrayConcat)

            this.$store.commit('set_userOrders', arrayResult)

            this.spinnerVisible = false
          },
          ex => {
            if (ex.response.status === 500) {
              window.location.href = '/jsp/error500.jsp'
            }
            if (ex.response.status === 403) {
              window.location.href = '/jsp/error403.jsp'
            }

            this.isError = true
            this.spinnerVisible = false
          })
    }
  }
}
</script>

<style scoped>

</style>