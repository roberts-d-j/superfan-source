package com.techelevator.dao;

import com.techelevator.model.Band;
import com.techelevator.model.Genre;

public interface GenreDao {
    Genre getGenreById(int genreId);
    Genre getGenreByName(String genreName);
    void changeBandGenre(Band band, int genreId);
}
