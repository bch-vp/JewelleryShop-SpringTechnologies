<template>
  <div class="notification-wrapper">
    <v-container>
      <v-row justilfy="center" row>
        <v-col sm="12" md="12" lg="12" xl="11">
          <div class="notification-background">
            <div class="notification">
              <v-container>
                <v-row justify="center" style="margin-top: 3em; margin-bottom: 3em">
                  <span class="text-h2 center" style="text-align: center; font-family: 'Monoton', cursive;">
                    {{ text_page.notification_component.title }}
                  </span>
                </v-row>
                <br>
                <v-row justify="center">
                  <span class="text-h6 font-weight-regular center" style="text-align: center; padding-bottom: 3em;">
                    {{ text_page.notification_component.text }}
                  </span>
                </v-row>
                <v-row justify="center">
                  <v-btn @click="showHome" class="light-green--text text--accent-2" style="margin-bottom: 3em" text
                         outlined rounded>
                    <span class="text-h6 center">
                      {{ text_page.notification_component.button }}
                    </span>
                  </v-btn>
                </v-row>
              </v-container>
            </div>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  props: ['showHome'],
  data() {
    return {
      text_page: text_page
    }
  }
}
</script>

<style scoped>

@media screen and (min-width: 400px) {
  .notification-wrapper {
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
  .notification-wrapper {
    width: 100%;
    position: relative;
    margin-right: -50%;
  }
}

.notification-background {
  box-shadow: 0 0 25px;
  background: rgba(0, 0, 0, 0.93);
  border-radius: 20px;
}


.notification {
  color: white;
  margin-top: 5em;
  padding: 2em;
}

</style>