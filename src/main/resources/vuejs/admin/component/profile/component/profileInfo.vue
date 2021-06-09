<template>
  <v-container>
    <v-row>
      <v-col>
        <div align="left">
          <v-btn @click="handleFileDelete" :disabled="!$store.state.Profile.isAvatarExists" dark outlined
                 color="light-green accent-2" class="white--text" text>
            <v-icon>
              delete_outline
            </v-icon>
            {{ text_page.form_component.button.delete_avatar }}
          </v-btn>
        </div>
      </v-col>
      <v-col style=" margin-bottom: 1em">
        <div align="center" class="text-h5 center"
             style="text-align: center;font-family: 'Monoton', cursive;">
          <v-icon color="white">
            perm_identity
          </v-icon>
          {{ text_page.header.role }}
        </div>
      </v-col>
      <v-col>
        <div align="right">
          <v-btn @click="showProfileEdit" dark outlined color="light-green accent-2" class="white--text" text>
            {{ text_page.form_component.button.edit_profile_info }}&nbsp
            <v-icon>
              edit
            </v-icon>
          </v-btn>
        </div>
      </v-col>
    </v-row>

    <div style="color: red; padding-bottom: 1em">{{ error }}</div>

    <v-row>
      <v-col>
        <div align="center">
          <img v-if="$store.state.Profile.isAvatarExists" :src="$store.state.Profile.avatarUrl" class="profile-image"/>
          <div v-if="!$store.state.Profile.isAvatarExists" class="profile-standard-image"/>
          <v-file-input v-on:change="handleFileUpload()"
                        v-if="!spinnerVisible"
                        v-model="file"
                        dark
                        ref="file"
                        prepend-icon="add_a_photo">
          </v-file-input>
        </div>
        <v-progress-circular style=" margin-left: 1em; margin-top: 1em"
                             v-if="spinnerVisible"
                             indeterminate
                             color="#8C9EFF"
        ></v-progress-circular>
      </v-col>
      <v-col>
        <v-container>
          <v-row>
            <v-col>

              <v-row>
          <span style="color: darkgray">
            {{ text_page.profile_component.login.key }}:&nbsp
          </span>
                {{ text_page.profile_component.login.value }}
              </v-row>
              <br><br>
              <v-row>
          <span style="color: darkgray">
            {{ text_page.profile_component.first_name.key }}:&nbsp
          </span>
                {{ text_page.profile_component.first_name.value }}
              </v-row>
              <br><br>
              <v-row>
          <span style="color: darkgray">
            {{ text_page.profile_component.last_name.key }}:&nbsp
          </span>
                {{ text_page.profile_component.last_name.value }}
              </v-row>
              <br><br>
              <v-row>
          <span style="color: darkgray">
            {{ text_page.profile_component.telephone_number.key }}:&nbsp
          </span>
                {{ text_page.profile_component.telephone_number.value }}
              </v-row>
              <br><br>
              <v-row>
                <div style="color: darkgray">
                  {{ text_page.profile_component.email.key }}:&nbsp
                </div>
                {{ text_page.profile_component.email.value }}
              </v-row>
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  props: ['showProfileEdit'],
  data() {
    return {
      text_page: text_page,
      file: undefined,
      error: undefined,

      spinnerVisible: false
    }
  },
  methods: {
    handleFileDelete() {
      this.axios({
        method: 'get',
        url: '/ajax?command=remove_profile_image',
      }).then(response => {
        this.$store.commit('set_isAvatarExists', false)
        this.error = undefined
        this.$store.commit('change_avatarUrl', '')
      }, ex => {
        if (ex.response.status === 500) {
          window.location.href = '/jsp/error500.jsp'
        }
        if (ex.response.status === 403) {
          window.location.href = '/jsp/error403.jsp'
        }

        this.error = ex.response.data.error
      })
    },
    handleFileUpload() {
      let formData = new FormData();
      formData.append('file', this.file);
      this.spinnerVisible = true
      this.axios({
        method: 'post',
        url: '/ajax?command=upload_profile_image',
        headers: {
          'Content-Type': 'multipart/form-data'
        },
        data: formData
      }).then(response => {
        this.$store.commit('set_isAvatarExists', true)
        this.error = undefined
        this.$store.commit('change_avatarUrl', response.data.url)

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
.profile-image {
  background-color: black;
  height: 14em;
  width: 15em;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  position: relative;
}

.profile-standard-image {
  background-color: black;
  border: 1px solid;
  border-color: white;
  height: 14em;
  width: 15em;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  position: relative;
}
</style>