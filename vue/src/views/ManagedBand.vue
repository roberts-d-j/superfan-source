<template>
  <div class="background">
    <!-- background image is stored  -->
    <title-bar class="header-managed" />
    <sidebar class="sidebar-managed" />
    <div class="content">
      <div id="managedBandsHeader">
        <h1 >Managed Bands</h1>
      </div>
      <div class="ownedBandCard" v-for="band in ownedBands" v-bind:key="band.bandId">
        <owned-band v-bind:band="band" />
      </div>
    </div>
  </div>
</template>

<script>
import TitleBar from "../components/TitleBar.vue";
import BandService from "../services/BandService";
import OwnedBand from "../components/SearchedItemNew.vue";
import sidebar from "../components/sidebar.vue";

export default {
  components: { TitleBar, OwnedBand, sidebar },
  data() {
    return {
      bandOwnerId: "",
      ownedBands: []
    };
  },
  created() {
    BandService.getBandsByOwnerId(this.$store.state.user.id).then( (response) => {
      this.ownedBands = response.data;
    })
  },
};
</script>

<style scoped>
.background {
  background: url("../images/concert-2.jpg");
  background-size: cover;
  background-position: cover;
  position: fixed;
  height: 100vh;
  width: 100vw;
  display: grid;
  grid-template-columns: 300px 1fr 100px;
  grid-template-rows: 100px 1fr 50px;
  grid-template-areas:
    "header header header"
    "navigation content . "
    ". . .";
  gap: 40px;
}

.header {
  grid-area: header;
}

.sidebar {
  grid-area: navigation;
}

.content {
  grid-area: content;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-y: auto;
}

.ownedBandCard {
  width: 60%;
  background-color: rgba(141, 54, 255, 0.9);
  color: rgb(248, 205, 85);
  text-align: center;
  border-radius: 50px;
  border-style: solid;
  border-color: rgb(255, 111, 231);
  margin-top: 10px;
  margin-bottom: 10px;
}

#managedBandsHeader {
  width: 60%;
  background-color: rgba(248, 205, 85);
  color: rgb(141, 54, 255, 0.9);
  text-align: center;
  border-radius: 50px;
  border-style: solid;
  border-color: rgb(255, 111, 231);
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>