<template>
  <v-hover v-slot="{ hover }">
    <v-card
        class="mx-auto"
        color="grey lighten-4"
        max-width="600"
    >
      <v-img
          :aspect-ratio="16/9"
          :src="product.imageURL"
      >
        <v-expand-transition>
          <div v-if="hover"
               class="d-flex transition-fast-in-fast-out light-green accent-2 v-card--reveal display-3 black--text"
               style="height: 100%;">
            ${{ product.price }}
          </div>
        </v-expand-transition>
      </v-img>
      <v-card-text class="pt-6" style="position: relative; background-color: grey">
        <v-btn @click="removeProductToShoppingCart" absolute color="black" class="white--text" fab right top>
          <v-icon>close</v-icon>
        </v-btn>

        <div v-if="isError" class="text-subtitle-1 font-weight-medium mb-2">
          <span style="color: orangered">{{ text_page.form_component.error.notification }}:&nbsp
          </span>{{ text_page.form_component.error.need_reload_page }}
        </div>
        <div v-else>
          <div class="text-h5 font-weight-light white--text mb-2">
            {{ product.name }}
          </div>
          <div class="text-h5 font-weight-regular orange--text mb-2">
            {{ product.status }}
          </div>
          <div style="color: black" class="text-subtitle-1 font-weight-light title mb-2">
            {{ product.info }}
          </div>
        </div>
      </v-card-text>
    </v-card>
  </v-hover>
</template>

<script>
export default {
  props: ['product'],
  data() {
    return {
      text_page: text_page,
      isError: false
    }
  },
  methods: {
    removeProductToShoppingCart() {
      this.axios({
        method: 'post',
        url: '/ajax?command=remove_product_from_shopping_cart',
        data: {
          name: this.product.name
        }
      }).then(response => {
        this.$store.commit('remove_productToShoppingCart', this.product)

        this.$store.commit('add_productToProducts', this.product)
      }, ex => {
        if (ex.response.status === 500) {
          window.location.href = '/jsp/error500.jsp'
        }
        if (ex.response.status === 403) {
          window.location.href = '/jsp/error403.jsp'
        }

        this.isError = true
      })

    }
  }
}
</script>

<style scoped>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: .5;
  position: absolute;
  width: 100%;
}
</style>