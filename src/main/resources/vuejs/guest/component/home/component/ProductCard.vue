<template>
  <v-hover v-slot="{ hover }">
    <v-card
        class="mx-auto"
        color="grey lighten-1"
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

        <v-btn @click="showSignIn"
               :disabled="product.status === 'INACTIVE'"
               absolute color="black" class="light-green--text text--accent-1" fab right top>
          <v-icon>shopping_cart</v-icon>
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
  props: ['product', 'showSignIn'],
  data() {
    return {
      text_page: text_page,
      isError: false
    }
  },
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