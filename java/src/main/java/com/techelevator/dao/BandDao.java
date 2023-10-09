package com.techelevator.dao;

import com.techelevator.model.Band;

import java.util.List;

public interface BandDao {

    List<Band> findAllBands();
    Band getBandById(int bandId);
    Band getBandByName(String bandName);
    List<Band> getBandsBySimilarName(String searchTerm);
    List<Band> getBandsByGenre(int genreId);
    int updateBand(Band bandToUpdate);
    int getBandIdByBandName(String bandName);
    String getBandNameById(int bandId);
    Band createBand(Band band);
    boolean followBand(int userId, int bandId);
    List<Integer> getAllUserFollowedBands(int userId);
    boolean unfollowBand(int userId, int bandId);
    boolean isFollowing(int userId, int bandId);
    boolean setBandOwner(int userId, int bandId);
    boolean removeBandOwner(int userId, int bandId);
    int getBandOwnerIdByBandId(int bandId);
    List<Integer> getBandIdsByOwnerId(int userId);
}
