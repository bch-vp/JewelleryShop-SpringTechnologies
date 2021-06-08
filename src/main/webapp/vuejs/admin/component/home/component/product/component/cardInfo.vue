<template>
  <v-hover v-slot="{ hover }">
    <v-card
        class="mx-auto"
        color="grey lighten-4"
        max-width="600"
    >
      <v-img
          style="background-color: darkgrey"
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

        <v-btn @click="showCardEdit"
               absolute color="black" class="light-green--text text--accent-2" fab right top>
          <v-icon>edit</v-icon>
        </v-btn>
        <div class="text-h5 font-weight-light white--text mb-2">
          {{ product.name }}
        </div>
        <div class="text-h5 font-weight-regular orange--text mb-2">
          {{ product.status }}
        </div>
        <div style="color: black" class="text-subtitle-1 font-weight-light title mb-2">
          {{ product.info }}
        </div>
      </v-card-text>
    </v-card>
  </v-hover>
</template>

<script>
export default {
  props: ['product', 'showCardEdit'],
  data() {
    return {
      text_page: text_page,
    }
  },
  methods: {
    addProductToShoppingCart() {
      this.axios({
        method: 'post',
        url: '/ajax?command=change_product_status',
        data: {
          name: this.product.name,
        }
      }).then(response => {
        this.$store.commit('add_productToShoppingCart', this.product)
        this.$store.commit('remove_productToProducts', this.product)
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