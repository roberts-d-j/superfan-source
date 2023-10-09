<template>
  <div class="background">
    <!-- background image is stored  -->
    <title-bar class="header" />
    <sidebar class="sidebar" />
    <div class="content">
    <div id="searchHeader">
      <h1> Search Results </h1>
      <label for="genre">Filter By Genre</label>
      <select class="selectDropdown" name="genre" id="genrePicker" v-model="filterGenreId">
        <option value="">--Select A Genre--</option>
        <option value="1">Pop</option>
        <option value="2">Rock</option>
        <option value="3">Country</option>
        <option value="4">Jazz</option>
        <option value="5">Electronic</option>
        <option value="6">Hip-Hop</option>
        <option value="7">World</option>
        <option value="8">Experimental</option>
        <option value="9">Latin</option>
        <option value="10">Metal</option>
      </select>
    </div>
      <div class="searchResult" v-for="result in filteredResults" v-bind:key="result.bandId">
        <searched-item v-bind:band="result" />
      </div>
    </div>
  </div>
</template>

<script>
import TitleBar from "../components/TitleBar.vue";
import BandService from "../services/BandService";
import SearchedItem from "../components/SearchedItemNew.vue";
import sidebar from "../components/sidebar.vue";

export default {
  components: { TitleBar, SearchedItem, sidebar },
  data() {
    return {
      searchResults: "",
      filterGenreId:''
    };
  },
  computed: {
    filteredResults() {
      if (this.filterGenreId) {
        return this.searchResults.filter( (band) => {
          return (band.genreId == this.filterGenreId);
        })
      }else {
        return this.searchResults;
      }
    }
  },
  created() {
    BandService.searchBands(this.$route.params.searchTerms)
    .then((results) => {
      this.searchResults = results.data;
    });
  },
};
</script>

<style scoped>
.background {
  background: url("../images/search-results.jpg");
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

.searchResult {
  width: 60%;
  background-color: rgb(145, 250, 250, 0.7);
  color: darkgreen;
  text-align: center;
  border-radius: 50px;
  border-style: solid;
  border-color: pink;
  margin-top: 10px;
  margin-bottom: 10px;
}

#searchHeader {
  width: 60%;
  background-color: rgba(251, 146, 255, 0.7);
  color: rgb(105, 36, 36);
  text-align: center;
  border-radius: 50px;
  border-style: solid;
  border-color: rgb(118, 212, 255);
  margin-top: 10px;
  margin-bottom: 10px;
}

.selectDropdown {
  padding: 9px;
  border-radius: 20px;
  background-color: rgb(255, 202, 229);
  font-size: 12pt;
  margin-bottom: 5px;
  font-weight: bold;
     text-shadow: -1px 0 white, 0 1px white, 1px 0 white, 0 -1px white;
}
</style>