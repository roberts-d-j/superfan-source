<template>
  <div>
    <form class="newBand">
      <label for="bandName">Band Name</label>
      <input type="text" name="bandName" v-model="band.bandName" />
      <br />
      <label for="description">Description</label>
      <textArea
        name="description"
        rows="8"
        cols="50"
        @input="band.description = $event.target.value"
      ></textArea>

      <!-- <input name="description" rows="8" cols="50"  v-model="band.description" /> -->
      <br />
      <select class="selectDropdown" name="genre" id="genrePicker" v-model="band.genreId">
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
      <!-- <br/>
            <label for="bandImage">Band Image</label>
            <br/>
            <input name="bandImage" type="file" id="file" ref="fileInput"/> -->
      <button v-on:click.stop.prevent="makeBandPage()">CREATE PAGE</button>
    </form>
  </div>
</template>

<script>
import bandService from "../services/BandService.js";

export default {
  data() {
    return {
      band: {
        bandName: "",
        description: "",
        genreId: "",
      },
    };
  },
  methods: {
    makeBandPage() {
      bandService
        .newBand(this.band, this.$store.state.user.id)
        .then((response) => {
          if (response.status === 200 || response.status === 201) {
            this.$router.push({
              name: "bandPageView",
              params: { bandName: this.band.bandName },
            });
          }
        }).catch(error => {
          if (error){
            window.alert("Error adding band. Make sure your band name doesn't already exist.");
          }
        })
    },
  },
};
</script>

<style scoped>
.newBand {
  margin-top: 50px;
  display: flex;
  flex-direction: column;
  background-color: rgb(255, 214, 31, 0.7);
  padding: 20px;
  border-radius: 20px;
  align-items: center;
  justify-content: center;
  border: outset;
  border-color: slategray;
  text-shadow: -1px 0 rgb(255, 255, 255), 0 1px rgb(255, 255, 255), 1px 0 rgb(255, 255, 255), 0 -1px rgb(255, 255, 255);
}

input,
textarea {
  background-color: rgb(231, 248, 255);
  border-radius: 15px;
}

label {
  text-align: center;
  font-size: 20pt;
  font-family: fantasy;
}

button {
  width: 30%;
  margin-top: 20px;
  justify-self: center;
  background: rgb(255, 105, 180, 1);
  color: white;
  border-style: outset;
  border-radius: 20px;
  border: px;
  border-color: black;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.selectDropdown {
  padding: 9px;
  border-radius: 20px;
  background-color: rgb(255, 241, 162);
  font-size: 12pt;
  margin-bottom: 5px;
  font-weight: bold;
     text-shadow: -1px 0 white, 0 1px white, 1px 0 white, 0 -1px white;
}
</style>