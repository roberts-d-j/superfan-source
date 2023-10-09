package com.techelevator.dao;

import com.techelevator.model.Band;
import com.techelevator.model.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcGenreDao implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcGenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre getGenreById(int genreId) {
        Genre genre = new Genre();
        String sql = "SELECT * FROM genres WHERE genre_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, genreId);
        if(results.next()) {
            genre = mapRowToGenre(results);
        }

        return genre;
    }

    @Override
    public Genre getGenreByName(String genreName) {
        Genre genre = new Genre();
        String sql = "SELECT * FROM genres WHERE genre_name = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, genreName);
        if(results.next()) {
            genre = mapRowToGenre(results);
        }

        return genre;
    }


    @Override
    public void changeBandGenre(Band band, int genreId) {
        /*
        * TODO: Implement this method to return proper data
        *  type and update a band's primary genre id. Maybe integrate
        *  JdbcBandDao into this dao? Consult SCRUM director for ideas.
        * */
    }
    public Genre mapRowToGenre(SqlRowSet rs) {
        Genre genre = new Genre();
        genre.setGenreId(rs.getInt("genre_id"));
        genre.setGenreName(rs.getString("genre_name"));
        return genre;
    }
}
