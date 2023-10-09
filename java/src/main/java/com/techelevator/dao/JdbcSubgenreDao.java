package com.techelevator.dao;

import com.techelevator.dao.mapper.BandMapper;
import com.techelevator.dao.mapper.SubgenreMapper;
import com.techelevator.model.Band;
import com.techelevator.model.Subgenre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Component
public class JdbcSubgenreDao implements SubgenreDao {

    private final SubgenreMapper subgenreMapper = new SubgenreMapper();
    private final BandMapper bandMapper = new BandMapper();
    private final JdbcTemplate jdbcTemplate;

    public JdbcSubgenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Subgenre getSubgenreById(int subgenreId) {
        String sql = "SELECT * FROM subgenres WHERE subgenre_id = ?;";

        try{
            return jdbcTemplate.queryForObject(sql, subgenreMapper, subgenreId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subgenre not found.");
        }
    }

    @Override
    public Subgenre getSubgenreByName(String subgenreName) {
        String sql = "SELECT * FROM subgenres WHERE subgenre_name = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, subgenreMapper, subgenreName);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subgenre not found.");
        }
    }

    @Override
    public int addNewSubgenre(String subgenreName) {
        Subgenre subgenreToReturn = new Subgenre();

        String sql = "INSERT INTO subgenres (subgenre_name) VALUES (?) " +
                "RETURNING subgenre_id;";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, subgenreName);
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create subgenre.");
        }
    }

    @Override
    public List<Band> getBandsBySubgenre(int subgenreId) {
        String sql = "SELECT bands.* FROM bands " +
                "JOIN band_subgenres ON (bands.band_id = band_subgenres.band_id) " +
                "WHERE band_subgenres.subgenre_id = ?;";

        return jdbcTemplate.query(sql, bandMapper, subgenreId);
    }

    @Override
    public int linkBandToSubgenre(int subgenreId, int bandId) {
        String sql = "INSERT INTO band_subgenres (subgenre_id, band_id) VALUES " +
                "(?, ?) RETURNING band_id;";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, subgenreId, bandId);
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create subgenre.");
        }
    }

    /*
    * TODO: Properly integrate delete methods for subgenres
    *  so that admin credentials allow subgenre management.
    * */
//    @Override
//    public boolean deleteSubgenreByName(String subgenreName) {
//        return false;
//    }
//
//    @Override
//    public boolean deleteSubgenreById(int subgenreId) {
//        return false;
//    }
}
