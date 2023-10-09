<template>
  <div>
    <form class="newBand">
      <label class="updateFont" for="bandName"> Band Name </label>
      <input type="text" name="bandName" v-model="band.bandName" />
      <br />
      <label class="updateFont" for="description"> Description </label>
      <textArea name="description" rows="8" cols="50" @input="band.description = $event.target.value"></textArea>
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
      <button v-on:click.stop.prevent="updateBandPage()"> CREATE PAGE </button>
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
    updateBandPage() {
      bandService.updateBand(this.band, this.$route.params.bandId).then((response) => {
        if (response.status === 200 || response.status === 201) {
          this.$router.push({
            name: "bandPageView",
            params: { bandName: this.band.bandName },
          });
        }
      });
    },
  },
};
</script>

<style scoped>
.newBand {
  margin-top: 50px;
  display: flex;
  flex-direction: column;
  background-color: rgba(152, 123, 255, 0.7);
  padding: 20px;
  border-radius: 50px;
  align-items: center;
  justify-content: center;
}

input,
textarea {
  background-color: rgb(231, 248, 255);
  border-radius: 5px;
}

select {
  background-color: rgb(231, 248, 255);
  border-radius: 5px;
  width: 40%;
  align-self: center;
}

label {
  text-align: center;
  font-size: 20pt;
  font-family: fantasy;
}

.updateFont {
  color: white;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

button {
  width: 30%;
  margin-top: 20px;
  justify-self: center;
  background: rgb(105, 175, 255);
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
  background-color: rgb(205, 219, 255);
  font-size: 12pt;
  margin-bottom: 5px;
  font-weight: bold;
     text-shadow: -1px 0 white, 0 1px white, 1px 0 white, 0 -1px white;
}
</style>