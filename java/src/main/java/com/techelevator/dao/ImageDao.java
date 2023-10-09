package com.techelevator.dao;

import com.techelevator.model.Image;

import java.util.List;

public interface ImageDao {
    int uploadImage(String fileName, byte[] data);
    Image getBandImageById(int imageId);
    Image getCoverImageByBandId(int bandId);
    Integer addCoverImageToBand(int imageId, int bandId);
    List<Image> getAllBandImagesByBandId(int bandId);
    boolean addImageToBandGallery(int imageId, int bandId);
}
