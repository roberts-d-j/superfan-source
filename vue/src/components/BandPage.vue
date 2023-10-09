<template>
  <div id="content">
    <div class="bandInfo">
      
      <div class="bandPageBox" v-show="bandFound" 
      v-bind:class="{
      'bandPageBox-generic': bandGenreId == 0,
      'bandPageBox-pop': bandGenreId == 1,
      'bandPageBox-rock': bandGenreId == 2,
      'bandPageBox-country': bandGenreId == 3,
      'bandPageBox-jazz': bandGenreId == 4,
      'bandPageBox-electronic': bandGenreId == 5,
      'bandPageBox-hiphop': bandGenreId == 6,
      'bandPageBox-world': bandGenreId == 7,
      'bandPageBox-experimental': bandGenreId == 8,
      'bandPageBox-latin': bandGenreId == 9,
      'bandPageBox-metal': bandGenreId == 10,
    }">
        <div id="bandTitleBox">
          <h1 id="bandName">{{ band.bandName }}</h1>
        </div>
        <div>
        <router-link id="sendMessageLabel" v-bind:to="{name: 'newMessage', params: {bandId: band.bandId}}" v-if="userIsOwner">Message Followers</router-link>
        </div>
        <label for="updateBand" id="updateBandLabel" v-on:click="navToUpdateBand()" v-if="userIsOwner">Update Band</label>
        <button name="updateBand" id="updateBand" v-show="false" />


        <div class="follow_unfollow" v-if="$store.state.token !== '' && !userIsOwner ">
          <label id="followLabel" class="followLabel" for="followButton" v-on:click="toggleFollow()" v-show="!isFollowing" >Follow</label>
          <button name="followButton" v-show="false">Follow</button>
          <label id="unfollowLabel" class="followLabel" for="unfollowButton" v-on:click="toggleFollow()" v-show="isFollowing"  >Unfollow</label>
          <button name="unfollowButton" v-show="false">Unfollow</button>
        </div>
        <img id="coverPhoto" :src="imgSrcData" alt="" v-show="imgSrcData"/>
        <div id="description">
            <h2>About {{ band.bandName }}</h2>
            <p>{{ band.description }}</p>
        </div>
        <!-- <input class="button" type="file" id="file" ref="fileInput" />
        -->
        <label for="file" id="fileButton" class="select-file-button" v-if="userIsOwner">Select Cover Image</label>
        <input class="button" type="file" id="file" ref="fileInput" v-show="false" />
        <label for="uploadButton" id="uploadLabel" class="select-file-button" v-if="userIsOwner">Upload Image</label>
        <button class="select-file-button" id="uploadButton" v-on:click="uploadImage()" v-show="false">Upload Image</button>
        <button id="galleryButton" v-on:click="navToPhotoGallery()">View Band Photo Gallery</button>
      </div>
      <h1 v-show="!bandFound">BAND PAGE NOT FOUND</h1>
    </div>
  </div>
</template>

<script>
import BandService from "../services/BandService";

export default {
  data() {
    return {
      //   band: {
      //     bandName: "",
      //     bandId: "",
      //     description: "",
      //     genreId: "",
      //     subgenres: "",
      //     bandImage: {
      //       imageId: "",
      //       fileName: "",
      //       imageData: "",
      //     },
      //   },
      bandFound: true,
      isFollowing: "",
      bandOwnerId: ""
    };
  },
  computed: {
    bandGenreId() {
      if (this.$route.name == "bandPageView") {
        return this.$store.state.currentBand.genreId;
      } else {
        return 0;
      }
    },
    band() {
      return this.$store.state.currentBand.bandId ? this.$store.state.currentBand : {};
    },
    imgSrcData() {
      if (this.band.bandImage && this.band.bandImage.fileName) {
        const dotIndex = this.band.bandImage.fileName.lastIndexOf(".");
        const extension = this.band.bandImage.fileName.substring(dotIndex + 1);
        return `data:image/${extension};base64, ${this.band.bandImage.imageData}`;
      }
      return '';
    },
    userIsOwner() {
      return (this.bandOwnerId === this.$store.state.user.id);
    }
  },
  created() {
    
    const name = this.$route.params.bandName;
    BandService.getBand(name)
      .then((response) => {
        // this.band = response.data;
        this.$store.commit("SET_BAND", response.data);
        
        BandService.isUserFollowing(this.band.bandId, this.$store.state.user.id)
        .then((response) => {
          this.isFollowing = response.data;
        });
        BandService.getBandOwnerIdByBandId(this.band.bandId)
        .then( (response) => {
          this.bandOwnerId = response.data;
        })
      })
      .catch((error) => {
        if (error.response) {
          this.bandFound = false;
         
        }
      });
    // BandService.getBandCoverImage(this.band.bandId).then(response => {
    //     this.band.photo = response.data;
    // }).catch( error => {
    //     if (error.response) {
    //         this.band.photo = 'No Photo Available'
    //     }
    // })
  },
  methods: {
    uploadImage() {
      const fileInfo = this.$refs.fileInput.files[0];
      let formData = new FormData();
      formData.append("file", fileInfo);
      BandService.postBandCoverImage(this.band.bandId, formData)
      .then((response) => {
          this.band.bandImage = response.data;
        }
      ).catch( (error) => {
        if (error) {
          window.alert('Unable to upload image. Please make sure your image is not too large.');
        }
      })
    },
    toggleFollow() {
      const userId = this.$store.state.user.id;
      if (this.isFollowing) {
        BandService.unfollowGroup(this.band.bandId, userId)
          .then((response) => {
            if (response) {
              this.isFollowing = false;
            }
          })
          .catch((error) => window.alert(error.status));
      } else {
        BandService.followGroup(this.band.bandId, userId)
          .then((response) => {
            if (response) {
              this.isFollowing = true;
            }
          })
          .catch((error) => window.alert(error.status));
      }
    },
    navToPhotoGallery() {
        this.$router.push({ name: "gallery", params: { bandId: this.band.bandId } })
    },
    navToUpdateBand() {
        this.$router.push({ name: "updateBand", params: { bandId: this.band.bandId }})
    }
  },
};
</script>

<style scoped>
.bandPageBox-generic #bandTitleBox {
  background-color: rgb(255, 105, 180, 1);
  color: white;
  border-radius: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  border: 7px;
  border-style: double;
  border-color: rgb(0, 0, 0);
   text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-pop #bandTitleBox {
  background-color: #f04620;
  color: #f7e8c4;
  border-radius: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  border: 7px;
  border-style: double;
  border-color: rgb(0, 0, 0);
   text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-rock #bandTitleBox {
  background-color: #882a40;
  color: #c5ac8e;
  border-radius: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  border: 7px;
  border-style: double;
  border-color: rgb(0, 0, 0);
   text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-country #bandTitleBox {
  background-color: #fde100;
  color: #000000d8;
  border-radius: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  border: 7px;
  border-style: double;
  border-color: rgb(0, 0, 0);
   text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-jazz #bandTitleBox {
  background-color: rgb(216, 148, 13);
  color: rgb(0, 0, 0);
  border-radius: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  border: 7px;
  border-style: double;
  border-color: rgb(0, 0, 0);
   text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-electronic #bandTitleBox {
  background-color: whitesmoke;
  color: rgba(16, 195, 207, 0.924);
  border-radius: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  border: 7px;
  border-style: double;
  border-color: rgb(0, 0, 0);
   text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-hiphop #bandTitleBox {
  background-color: #fdb927;
  color: #000000;
  border-radius: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  border: 7px;
  border-style: double;
  border-color: rgb(0, 0, 0);
   text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-world #bandTitleBox {
  background-color: yellow;
  color: black;
  border-radius: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  border: 7px;
  border-style: double;
  border-color: rgb(0, 0, 0);
   text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-experimental #bandTitleBox {
  background-color: #cd4f39;
  color: rgb(211, 208, 198);
  border-radius: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  border: 7px;
  border-style: double;
  border-color: rgb(0, 0, 0);
   text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-latin #bandTitleBox {
  background-color:rgba(215, 104, 24, 0.911);
  color: rgb(253 241 227);
  border-radius: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  border: 7px;
  border-style: double;
  border-color: rgb(0, 0, 0);
   text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-metal #bandTitleBox {
  background-color: rgb(92 54 49);
  color: rgb(173, 172, 182);
  border-radius: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  border: 7px;
  border-style: double;
  border-color: rgb(0, 0, 0);
   text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

#bandName {
  text-align: center;
  font-size: 36pt;
}

.bandPageBox {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 20px;
  border: outset;
  border-color: slategray;
}
.bandPageBox-generic {
  background-color: rgba(73, 218, 198, 0.6);
}
.bandPageBox-pop {
  background-color: #dd9bc6e0;
}
.bandPageBox-rock {
  background-color: #574158d0;
}
.bandPageBox-country {
  background-color: #479829d3 ;
}
.bandPageBox-jazz {
  background-color: #493368dc;
}
.bandPageBox-electronic {
  background-color: rgba(255, 192, 203, 0.836);
}
.bandPageBox-hiphop {
  background-color: #562583d2;
}
.bandPageBox-world {
  background-color:  rgba(87, 208, 230, 0.863);
}
.bandPageBox-experimental {
  background-color: #4f632ddf;
}
.bandPageBox-latin {
  background-color: rgba(73, 189, 175, 0.849);
}
.bandPageBox-metal {
  background-color: #4f1627d3;
}
#updateBandLabel, #sendMessageLabel {
    background-color: steelblue;
    color:white;
    font-family: fantasy;
    text-transform: uppercase;
    width: 100px;
    height: auto;
    border-radius: 20px;
    border: 5px;
    border-style: outset;
    text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
    border-color: oldlace;
    text-align: center;
    font-size: 20px;
    margin: 20px;
    box-shadow: black;
    padding: 5px;
    text-decoration: none;
}

#coverPhoto {
  margin-top: 25px;
  border-radius: 20px;
  border: 15px;
 border-style: outset;
  border-color: oldlace;
  max-width: 60%;
  max-height: 300px;
}

#content {
  overflow-y: auto;
}

#roundedEdges {
    border-radius: 20px;
}

#description {
    padding: 20px;
    background-color: oldlace; 
    border-radius: 20px;
    margin: 10px;
    max-width: 80%;
    font-size: 18pt;
    text-align: center;
}

.bandPageBox-pop #followLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-pop #unfollowLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-rock #followLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-rock #unfollowLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-country #followLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-country #unfollowLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-jazz #followLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-jazz #unfollowLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-electronic #followLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-electronic #unfollowLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-hiphop #followLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-hiphop #unfollowLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-world #followLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-world #unfollowLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-experimental #followLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-experimental #unfollowLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-latin #followLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-latin #unfollowLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-metal #followLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

.bandPageBox-metal #unfollowLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
  
}


.bandPageBox-rock #uploadLabel {
  background-color: steelblue;
  color: #ffffff;
  border-style: outset;
  border-color: oldlace;
  border-radius: 20px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}


</style>