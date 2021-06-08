<template>
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

      <div style="padding-top: 15px">
        <v-btn @click="showCardInfo"
               absolute color="black" class="light-green--text text--accent-2" fab right>
          <v-icon>close</v-icon>
        </v-btn>
      </div>

      <v-file-input v-on:change="handleFileUpload()"
                    v-if="!spinnerVisible"
                    style="width: 60%; padding-left: 1em"
                    v-model="file"
                    dark
                    ref="file"
                    prepend-icon="add_a_photo">
      </v-file-input>
      <div style="color: red; margin-left: 1em" v-if="error">{{ error }}</div>
      <v-progress-circular style=" margin-left: 1em"
                           v-if="spinnerVisible"
                           indeterminate
                           color="#8C9EFF"
      ></v-progress-circular>
    </v-img>
    <v-card-text class="pt-6" style="position: relative; background-color: grey">

      <v-btn @click="showEditStatusAndLocation"
             v-if="isEditInfo"
             absolute color="black" class="light-green--text text--accent-2" small fab right top>
        <v-icon>navigate_next</v-icon>
      </v-btn>

      <v-btn @click="showEditInfo"
             v-if="isEditStatusAndLocation"
             absolute color="black" class="light-green--text text--accent-2" small fab left top>
        <v-icon>navigate_before</v-icon>
      </v-btn>

      <EditInfo v-if="isEditInfo" :product="product"/>
      <EditStatusAndLocation v-if="isEditStatusAndLocation" :showCardInfo="showCardInfo" :product="product"/>

    </v-card-text>
  </v-card>
</template>

<script>
import EditInfo from 'vuejs/admin/component/home/component/product/component/component/editInfo.vue'
import EditStatusAndLocation
  from 'vuejs/admin/component/home/component/product/component/component/editStatusAndLocation.vue'

export default {
  props: ['showCardInfo', 'product'],
  components: {
    EditInfo,
    EditStatusAndLocation
  },
  data() {
    return {
      valid: false,
      text_page: text_page,

      file: undefined,
      error: undefined,

      isEditInfo: true,
      isEditStatusAndLocation: false,
      spinnerVisible: false
    }
  },
  methods: {
    clearAllComponents() {
      this.isEditStatusAndLocation = false
      this.isEditInfo = false
    },
    showEditInfo() {
      this.clearAllComponents()
      this.isEditInfo = true
    },
    showEditStatusAndLocation() {
      this.clearAllComponents()
      this.isEditStatusAndLocation = true
    },
    handleFileUpload() {
      let formData = new FormData();
      formData.append('file', this.file);

      this.spinnerVisible = true
      this.error = undefined
      this.axios({
        method: 'post',
        url: '/ajax?command=upload_product_image&name=' + this.product.name,
        headers: {
          'Content-Type': 'multipart/form-data'
        },
        data: formData
      }).then(response => {
        this.error = undefined
        this.product.imageURL = response.data.url

        this.spinnerVisible = false
      }, ex => {
        if (ex.response.status === 500) {
          window.location.href = '/jsp/error500.jsp'
        }
        if (ex.response.status === 403) {
          window.location.href = '/jsp/error403.jsp'
        }

        this.error = ex.response.data.error

        this.spinnerVisible = false
      })
    }
  }
}
</script>

<style scoped>

</style>