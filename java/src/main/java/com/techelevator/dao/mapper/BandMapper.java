package com.techelevator.dao.mapper;

import com.techelevator.model.Band;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BandMapper implements RowMapper<Band> {
    @Override
    public Band mapRow(ResultSet rs, int i) throws SQLException {
        Band band = new Band();
        band.setBandId(rs.getInt("band_id"));
        band.setBandName(rs.getString("band_name"));
        band.setBandImage(null);
        band.setDescription(rs.getString("description"));
        band.setGenreId(rs.getInt("genre_id"));
        return band;
    }
}
