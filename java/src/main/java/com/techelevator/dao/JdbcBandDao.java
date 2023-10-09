package com.techelevator.dao;

import com.techelevator.dao.mapper.BandMapper;
import com.techelevator.model.Band;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcBandDao implements BandDao {
    private final BandMapper bandMapper = new BandMapper();
    private final JdbcTemplate jdbcTemplate;

    public JdbcBandDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Band> findAllBands() {
        String sql = "SELECT * FROM bands;";

        return jdbcTemplate.query(sql, bandMapper);
    }

    @Override
    public Band getBandById(int bandId) {
        String sql = "SELECT * FROM bands WHERE band_id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, bandMapper, bandId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Band not found.");
        }
    }

    @Override
    public Band getBandByName(String bandName) {
        String sql = "SELECT * FROM bands WHERE LOWER(band_name) = LOWER(?);";
        try {
            return jdbcTemplate.queryForObject(sql, bandMapper, bandName);
        } catch(EmptyResultDataAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Band not found.");
        }
    }

    @Override
    public List<Band> getBandsBySimilarName(String searchTerm) {
        String sql = "SELECT * FROM bands WHERE band_name ILIKE ? ORDER BY band_name;";
        try{
            return jdbcTemplate.query(sql, bandMapper, "%" + searchTerm + "%");
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No matching bands found.");
        }
    }

    @Override
    public List<Band> getBandsByGenre(int genreId) {
        String sql = "SELECT * FROM bands WHERE genre_id = ?;";

        return jdbcTemplate.query(sql, bandMapper, genreId);
    }

    public Band createBand(Band band) {

        String sql = "INSERT INTO bands (band_name, description, genre_id)" +
                " VALUES (?, ?, ?) RETURNING band_id;";

        int bandId = 0;
        try {
            bandId = jdbcTemplate.queryForObject(sql, Integer.class, band.getBandName(),
                    band.getDescription(), band.getGenreId());
            band.setBandId(bandId);
            return band;
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Band data not present.");
        }
    }
    @Override
    public int updateBand(Band bandToUpdate) {
        String sql = "UPDATE bands SET band_name = ?, description = ?, genre_id = ? WHERE band_id = ?;";
        try {
            return jdbcTemplate.update(sql, bandToUpdate.getBandName(),
                    bandToUpdate.getDescription(), bandToUpdate.getGenreId(),
                    bandToUpdate.getBandId());
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update band.");
        }
    }

    @Override
    public int getBandIdByBandName(String bandName) {
        String sql = "SELECT * FROM bands WHERE band_name = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bandName);
        int bandId = 0;

        if(results.next()) {
            bandId = results.getInt("band_id");
        }
        return bandId;
    }

    @Override
    public String getBandNameById(int bandId) {
        String bandName = null;
        String sql = "SELECT * FROM bands WHERE band_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bandId);
        if(results.next()) {
            bandName = results.getString("band_name");
        }

        return bandName;
    }
    @Override
    public boolean followBand(int userId, int bandId) {
        String sql = "INSERT INTO user_following (user_id, band_id) VALUES (?, ?);";
        try{
            return jdbcTemplate.update(sql, userId, bandId) == 1;
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error following band");
        }
    }
    @Override
    public List<Integer> getAllUserFollowedBands(int userId) {
        String sql = "SELECT bands.band_id FROM user_following JOIN bands " +
                "ON (user_following.band_id = bands.band_id) WHERE user_id = ? ORDER BY band_name";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, userId);
        List<Integer> bandsFollowed = new ArrayList<>();
        try{
            while(rs.next()) {
                bandsFollowed.add(rs.getInt("band_id"));
            }
            return bandsFollowed;
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No bands found.");
        }
    }

    @Override
    public boolean unfollowBand(int userId, int bandId) {
        String sql = "DELETE FROM user_following WHERE (user_id = ? AND band_id = ?)";
        try{
            jdbcTemplate.update(sql, userId, bandId);
            return true;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error unfollowing band");
        }
    }

    @Override
    public boolean isFollowing(int userId, int bandId) {
        String sql = "SELECT COUNT(*) FROM user_following WHERE user_id = ? AND band_id = ?;";
        try{
            return jdbcTemplate.queryForObject(sql, Integer.class, userId, bandId) == 1;
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error handling follow retrieval");
        }
    }

    @Override
    public boolean setBandOwner(int userId, int bandId) {
        String sql = "INSERT INTO band_owners (owner_id, band_id) VALUES (?, ?);";
        try {
            return jdbcTemplate.update(sql, userId, bandId) == 1;
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error handling follow retrieval");
        }
    }
    @Override
    public boolean removeBandOwner(int userId, int bandId) {
        String sql = "DELETE FROM band_owners WHERE (owner_id = ? AND band_id = ?)";
        try{
            jdbcTemplate.update(sql, userId, bandId);
            return true;
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error unfollowing band");
        }
    }

    @Override
    public int getBandOwnerIdByBandId(int bandId) {
        String sql = "SELECT owner_id FROM band_owners WHERE band_id = ?";
        try{
            return jdbcTemplate.queryForObject(sql, Integer.class, bandId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Band owner not found.");
        }
    }

    @Override
    public List<Integer> getBandIdsByOwnerId(int userId) {
        String sql = "SELECT band_id FROM band_owners WHERE owner_id = ?";
        List<Integer> bandsOwned = new ArrayList<>();
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while(results.next()) {
                bandsOwned.add(results.getInt("band_id"));
            }
            return bandsOwned;
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error retrieving owned bands");
        }
    }

}
