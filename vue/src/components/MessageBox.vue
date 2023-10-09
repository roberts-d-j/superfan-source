<template>
    <div id="messageInfo">
        <h2 id="messageSender"> Message from {{senderBand.bandName}}</h2>
        <p id="sendDate">Sent on {{message.sendDate}}</p>
        <p id="messageBody"> {{message.messageBody}} </p>
    </div>
</template>

<script>
import BandService from "../services/BandService.js";

export default {
    data() {
        return {
            senderBand: {}
        }
    },
    props: { message: {} },
    created() {
        BandService.getBandByBandId(this.message.senderBandId).then( (response) => {
            this.senderBand = response.data;
        } )
    }
}
</script>

<style>
    #messageSender {
        background-color: blueviolet;
        color: white;
        padding: 5px;
        border-radius: 50px;
    }

    #sendDate {
        color: white;
    }

    #messageBody {
        background-color: rgb(209, 162, 253);
        border-radius: 50px;
        border-style: solid;
        border-color: black;
        border: 2px;
        padding: 5px;
    }

    #messageInfo {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: space-evenly;
        background-color: rgb(185, 121, 245);
        border-radius: 50px;
        margin: 15px;
    }
</style>