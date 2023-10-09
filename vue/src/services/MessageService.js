import axios from 'axios';

export default {

    getMessageById(messageId) {
        return axios.get(`/messages/${messageId}`);
    },

    getAllMessagesToUser(userId) {
        return axios.get(`/messages/user/${userId}`);
    },

    getAllMessagesFromBand(bandId) {
        return axios.get(`/messages/band/${bandId}`);
    },

    sendMessage(message) {
        return axios.post(`/messages/send`, message);
    },

    deleteMessageForUser(userId, messageId) {
        return axios.put(`/messages/user/${userId}/delete/${messageId}`);
    },
    
    deleteMessageForBand(messageId) {
        return axios.put(`/messages/band/delete/${messageId}`);
    }

}