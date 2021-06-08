<template>
  <div>
    <CardInfo v-if="isCardInfo" :showCardEdit="showCardEdit" :product="product"/>
    <CardEdit v-if="isCardEdit" :showCardInfo="showCardInfo" :product="product"/>
  </div>
</template>

<script>
import CardInfo from 'vuejs/admin/component/home/component/product/component/cardInfo.vue'
import CardEdit from 'vuejs/admin/component/home/component/product/component/cardEdit.vue'

export default {
  props: ['product'],
  components: {
    CardInfo,
    CardEdit
  },
  data() {
    return {
      text_page: text_page,
      isError: false,
      isCardInfo: true,
      isCardEdit: false,
    }
  },
  methods: {
    clearAllComponents() {
      this.isCardInfo = false
      this.isCardEdit = false
    },
    showCardInfo() {
      this.clearAllComponents()
      this.isCardInfo = true
    },
    showCardEdit() {
      this.clearAllComponents()
      this.isCardEdit = true
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