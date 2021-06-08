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
      <v-list-item-title>
        <div align="left" style="padding-left: 1em">
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
                      {{ text_page.form_component.button.date_created }}:&nbsp
                      <strong> {{ order.dateCreatedAt }}</strong>
                    </v-row>
                    <v-row style="padding-top: 4px">
                      {{ text_page.form_component.input.address.name }}:&nbsp
                      <strong> {{ order.address }}</strong>
                    </v-row>
                  </v-container>
                </v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>

          <v-menu style="margin-left: 3em" offset-y>
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
                        {{ order.price }}$
                      </v-col>
                    </v-row>
                  </v-container>

                </v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </div>
      </v-list-item-title>
    </v-list-item-content>
    <v-list-item-action>
      <v-row>
        <v-col>
          <v-btn v-if="order.status === 'CONFIRMED'" :disabled="true" style="width: 13em" outlined text>
            {{ text_page.form_component.button.confirmed }}
          </v-btn>
          <v-btn v-if="order.status === 'NOT_CONFIRMED'" :disabled="true" style="width: 13em" outlined text>
            {{ text_page.form_component.button.not_confirmed }}
          </v-btn>
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
    }
  },
}
</script>

<style scoped>

</style>