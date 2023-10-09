import axios from 'axios';

export default {
    getAllBandImagesByBandId(bandId) {
        return axios.get(`/bands/${bandId}/gallery`);
    },
    addImageToBandGallery(bandId, formData) {
        const options = {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }
        return axios.post(`/bands/${bandId}/gallery`, formData, options);
    }
}