<template>
  <div class="profile-wrapper">
    <v-container>
      <v-row justify="center" row>
        <v-col sm="9" md="9" lg="8" xl="5">
          <div class="profile-background">
            <div class="profile">
              <profile-info v-if="isProfileInfo"
                            :showProfileEdit="showProfileEdit"/>
              <profile-edit v-if="isProfileEdit"
                            :showProfileInfo="showProfileInfo"/>
            </div>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>
<script>
import ProfileInfo from 'vuejs/client/component/profile/component/profileInfo.vue'
import ProfileEdit from 'vuejs/client/component/profile/component/profileEdit.vue'

export default {
  components: {ProfileInfo, ProfileEdit},
  data() {
    return {
      text_page: text_page,

      isProfileInfo: false,
      isProfileEdit: false
    }
  },
  created() {
    this.showProfileInfo()
  },
  methods: {
    clearAllComponents() {
      this.isProfileInfo = false
      this.isProfileEdit = false
    },
    showProfileInfo() {
      this.clearAllComponents()
      this.isProfileInfo = true
    },
    showProfileEdit() {
      this.clearAllComponents()
      this.isProfileEdit = true
    }
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
  box-shadow: 0 0 25px;
  background: rgba(0, 0, 0, 0.93);
  border-radius: 20px;
}


.profile {
  color: white;
  margin-top: 5em;

  padding-top: 1em;
  padding-right: 2em;
  padding-left: 2em;
  padding-bottom: 2em;
}

</style>