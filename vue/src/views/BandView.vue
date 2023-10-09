<template>
  <div
    class="background"
    v-bind:class="{
      'background-pop': bandGenre == 1,
      'background-rock': bandGenre == 2,
      'background-country': bandGenre == 3,
      'background-jazz': bandGenre == 4,
      'background-electronic': bandGenre == 5,
      'background-hiphop': bandGenre == 6,
      'background-world': bandGenre == 7,
      'background-experimental': bandGenre == 8,
      'background-latin': bandGenre == 9,
      'background-metal': bandGenre == 10,
    }"
  >
    <!-- background image is stored  -->
    <title-bar class="header" />
    <sidebar class="sidebar" />
    <band-page class="content" v-show="true"/> 
    <!-- ^^^ONLY FOR VISIBILITY OF THEMES DISABLE VSHOW FALSE WHEN DONE-->
    <Footer/>
  </div>
</template>

<script>
import BandPage from "../components/BandPage.vue";
import sidebar from "../components/sidebar.vue";
import TitleBar from "../components/TitleBar.vue";
import Footer from "../components/Footer.vue";

export default {
  components: { BandPage, TitleBar, sidebar, Footer },
  computed: {
    bandGenre() {
      return this.$store.state.currentBand.genreId ? this.$store.state.currentBand.genreId : {};
    },
  },
};
</script>

<style scoped>
.background {
  background-size: cover;
  background-position: cover;
  position: fixed;
  height: 100vh;
  width: 100vw;
  display: grid;
  grid-template-columns: 300px 1fr 100px;
  grid-template-rows: 100px 1fr 15px;
  grid-template-areas:
    "header header header"
    "navigation content . "
    ". . .";
  gap: 20px;
}

.background-pop {
   background: url("../images/jukebox.jpg");
   background-repeat: no-repeat;
   background-size: cover;
}

.background-generic {
  background: url("../images/login-background.jpg");
  background-repeat: no-repeat;
  background-size: cover;
}

.background-rock {
   background: url("../images/rock-2.jpg");
   background-repeat: no-repeat;
   background-size: cover;
}

.background-country {
   background: url("../images/country-2.jpg");
   background-repeat: no-repeat;
   background-size: cover;
}

.background-jazz {
   background: url("../images/Jazzy.jpg");
   background-repeat: no-repeat;
   background-size: cover;
}

.background-electronic {
   background: url("../images/dj-2.jpg");
   background-repeat: no-repeat;
   background-size: cover;
}

.background-hiphop {
   background: url("../images/hip-hop.jpg");
   background-repeat: no-repeat;
   background-size: cover;
}

.background-world {
   background: url("../images/world.jpg");
   background-repeat: no-repeat;
   background-size: cover;
} 

.background-experimental {
   background: url("../images/experimental.jpg");
   background-repeat: no-repeat;
   background-size: cover;
}

.background-latin {
   background: url("../images/latin-2.jpg");
   background-repeat: no-repeat;
   background-size: cover;
}

.background-metal {
   background: url("../images/metal.jpg");
   background-repeat: no-repeat;
   background-size: cover;
}
/* .body {
    
    align-items: center;
    justify-content: center;
    grid-template-columns: 1fr 5fr 1fr;
    gap: 2px;
    grid-template-areas: 
    "sidebar content .";
  } */

.header {
  grid-area: header;
  /* position: fixed; */
}

.sidebar {
  /* position: fixed; */
  grid-area: navigation;
}

.content {
  grid-area: content;
}

.footer {
  background-color: rgba(248, 249, 250, 0.0);
  text-shadow: -1px 0 rgb(0, 0, 0), 0 1px rgb(0, 0, 0), 1px 0 rgb(0, 0, 0), 0 -1px rgb(0, 0, 0);
  color: white;
  text-align: center;
  position: fixed;
  margin-top: 20px;
  width: 100%;
  height: 60px;
  z-index: 1000; 
}
</style>