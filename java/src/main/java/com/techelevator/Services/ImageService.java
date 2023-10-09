package com.techelevator.Services;

import com.techelevator.dao.BandDao;
import com.techelevator.dao.ImageDao;
import com.techelevator.model.Band;
import com.techelevator.model.Image;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private final ImageDao imageDao;
    private final BandDao bandDao;

    public ImageService(ImageDao imageDao, BandDao bandDao) {
        this.imageDao = imageDao;
        this.bandDao = bandDao;
    }

    public Image uploadImage(String fileName, byte[] data) {
        int imageId = imageDao.uploadImage(fileName, data);
        return imageDao.getBandImageById(imageId);
    }

    public Image getBandImageById(int imageId) {
        return imageDao.getBandImageById(imageId);
    }

    public Image getCoverImageByBandId(int bandId) {
        return imageDao.getCoverImageByBandId(bandId);
    }

    public Image addCoverImageToBand(int imageId, int bandId) {
        Band band = bandDao.getBandById(bandId);
        band.setBandImage(imageDao.getBandImageById(imageId));
        bandDao.updateBand(band);
        int rowsUpdated = imageDao.addCoverImageToBand(imageId, bandId);
        if(rowsUpdated != 0) {
            return imageDao.getBandImageById(imageId);
        } else {
            return null;
        }
    }

    public boolean addImageToBandGallery(String fileName, byte[] data, int bandId){
        int imageId = imageDao.uploadImage(fileName, data);
        return imageDao.addImageToBandGallery(imageId, bandId);

    }
    public List<Image> getAllBandImagesByBandId(int bandId) {
        return imageDao.getAllBandImagesByBandId(bandId);
    }
}
