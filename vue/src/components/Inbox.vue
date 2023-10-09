<template>
  <div id="content">
    <div class="inbox">
      <h1 id="pageHeaderTitle">Messages From Bands You Follow</h1>
      <div id="sortDropdowns">
        <select class="selectDropdown" name="alphabetical" id="alphabetical" v-model.number="orderSelection">
          <option value="1">Newest First</option>
          <option value="2">Oldest First</option>
        
        </select>
        <select class="selectDropdown" name="band-picker" id="band-picker" v-model="filterBandId">
          <option value="0">Filter By Band</option>
          <option v-for="band in followedBands" v-bind:key="band.bandId" :value="band.bandId">{{band.bandName}}</option>
        </select>
      </div>
      <div class="messageBox" v-for="message in displayedMessages" v-bind:key="message.messageId"> 
        <message-box v-bind:message="message" />
      </div>
    </div>
  </div>
</template>

<script>
import BandService from "../services/BandService.js";
import MessageService from "../services/MessageService.js";
import MessageBox from "../components/MessageBox.vue";
export default {
  components: { MessageBox },
  data() {
    return {
      messages: [],
      followedBands: [],
      filterBandId: 0,
      orderSelection: 1
    }
  },
  computed: {
    displayedMessages() {
      // let filteredMessages = this.messages;
      let filteredMessages = JSON.parse(JSON.stringify(this.messages));

      if (this.filterBandId != 0){    //picks a bandId from drop down, and only shows those bands
         filteredMessages = this.messages.filter( message => {
          return (this.filterBandId === message.senderBandId);
        })
      }
      if (this.orderSelection === 1){
        return filteredMessages;
      }else if (this.orderSelection === 2){
        return filteredMessages.reverse();
      }
      return filteredMessages
    },
  },
  created() {
    MessageService.getAllMessagesToUser(this.$store.state.user.id).then( (response) => {
      this.messages = response.data;
    })
    BandService.getAllUserFollowing(this.$store.state.user.id).then( response => {
      this.followedBands = response.data;
    })

  },
  methods: {
    getBandNameByBandId(bandId){
      BandService.getBandByBandId(bandId).then( response => {
        return response.data.bandName;
      })
    }
  }
};
</script>

<style scoped>
#content {
  overflow-y: auto;
}
.inbox {
  background-color: rgba(162, 6, 252, 0.856);
  border-radius: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 3px;
  padding-bottom: 3px;
  border: 15px;
  border-style: solid;
  border-color: rgba(255, 112, 212, 0.842);
   text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}

#pageHeaderTitle {
  color: white;
  padding: 5px;
  border-radius: 0px;
  text-align: center;
}

#sortDropdowns {
  display: flex;
  justify-content: space-evenly;
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