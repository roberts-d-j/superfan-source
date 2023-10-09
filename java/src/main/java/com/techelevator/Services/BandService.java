package com.techelevator.Services;

import com.techelevator.dao.BandDao;
import com.techelevator.dao.ImageDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Band;
import com.techelevator.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BandService {
    private ImageDao imageDao;
    private UserDao userDao;
    private final BandDao bandDao;

    /*
    * This class handles the middle point between all basic
    * DAO methods and the controller, being where any additional
    * logic is handled.
    * */
    public BandService(BandDao bandDao, UserDao userDao, ImageDao imageDao) {
        this.bandDao = bandDao;
        this.imageDao = imageDao;
        this.userDao = userDao;
    }

    public Band getBandByName(String bandName) {
        Band band = bandDao.getBandByName(bandName);
        band.setBandImage(imageDao.getCoverImageByBandId(band.getBandId()));
        return band;
    }

    public List<Band> getBandsBySimilarName(String searchTerm) {
        return bandDao.getBandsBySimilarName(searchTerm);
    }

    public List<Band> getBandsByGenre(int genreId) {
        return bandDao.getBandsByGenre(genreId);
    }

    public Band getBandById(int bandId) {
        return bandDao.getBandById(bandId);
    }

    public List<Band> findAllBands() {
        return bandDao.findAllBands();
    }

    public Band updateBand(Band bandToUpdate, int bandId) {
        Band referenceBand = bandDao.getBandById(bandId);
        bandToUpdate.setBandImage(referenceBand.getBandImage());
        bandToUpdate.setBandId(bandId);
        int rowsUpdated = bandDao.updateBand(bandToUpdate);
        if(rowsUpdated == 1) {
            return bandDao.getBandById(bandToUpdate.getBandId());
        } else {
            return null;
        }
    }

    public Band createBand(Band band, int userId) {
        Band newBand = bandDao.createBand(band);
        bandDao.setBandOwner(userId, newBand.getBandId());
        return newBand;
    }

    public int getBandIdByBandName(String bandName) {
        return bandDao.getBandIdByBandName(bandName);
    }

    public String getBandNameById(int bandId) {
        return bandDao.getBandNameById(bandId);
    }

    public boolean followBand(int userId, int bandId) {
        return bandDao.followBand(userId, bandId);
    }

    public List<Band> getAllUserFollowedBands(int userId) {
        List<Integer> bandIds = bandDao.getAllUserFollowedBands(userId);
        List<Band> bandsFollowed = new ArrayList<>();
        for(int i = 0; i < bandIds.size(); i++) {
            bandsFollowed.add(bandDao.getBandById(bandIds.get(i)));
        }
        return bandsFollowed;
    }

    public boolean unfollowBand(int userId, int bandId) {
        return bandDao.unfollowBand(userId, bandId);
    }

    public boolean isFollowing(int userId, int bandId) {
        return bandDao.isFollowing(userId, bandId);
    }

    public boolean setBandOwner(int userId, int bandId) {
        return bandDao.setBandOwner(userId, bandId);
    }

    public boolean removeBandOwner(int userId, int bandId) {
        return bandDao.removeBandOwner(userId, bandId);
    }

    public int getBandOwnerIdByBandId(int bandId) {
        return bandDao.getBandOwnerIdByBandId(bandId);
    }

    public List<Band> getBandsByOwnerId(int userId) {
        List<Integer> bandIds = bandDao.getBandIdsByOwnerId(userId);
        List<Band> ownedBands = new ArrayList<>();
        for(int i = 0; i < bandIds.size(); i++) {
            ownedBands.add(bandDao.getBandById(bandIds.get(i)));
        }
        return ownedBands;
    }
}
