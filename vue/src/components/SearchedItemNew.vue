<template>
  <div class="band-card">
    
      <img :src="imgSrcData" alt="" class="band-image" v-if="coverImage">
    <div class="band-info-holder">
      <h1>{{ band.bandName }}</h1>
      <h2>{{ genre }}</h2>
      <button v-on:click="navigateToBandPage()">View Page</button>
    </div>
  </div>
</template>

<script>
import BandService from '../services/BandService.js'
export default {
  props: { band: {} },
  data() {
    return {
      coverImage: ''
      
    }
  },
  computed: {
    genre() {
      const genres = ["", "Pop", "Rock", "Country", "Jazz", "Electronic", "Hip-Hop", "World", "Experimental", "Latin", "Metal"];
      return genres[this.band.genreId];
    },
    imgSrcData() {
      if (this.coverImage && this.coverImage.fileName) {
        const dotIndex = this.coverImage.fileName.lastIndexOf(".");
        const extension = this.coverImage.fileName.substring(dotIndex + 1);
        return `data:image/${extension};base64, ${this.coverImage.imageData}`
      }
       return "";
    }
  },
  methods :{
    navigateToBandPage(){
      this.$router.push(`/bands/${this.band.bandName}`)
    }
  },
  created() {
    BandService.getBandCoverImage(this.band.bandId).then( response => {
      this.coverImage = response.data;
    })
  }
};
</script>

<style scoped>
.band-card {
 display: flex;
 padding: 20px;
 justify-content: space-evenly;
}
.band-image {
  max-width: 356px;
  max-height: 237px;
  width: auto;
  height: auto;
  border-radius: 20px;
}
.band-info-holder {
  display: flex;
  flex-direction: column;
  flex-basis: 50%;
}
</style>