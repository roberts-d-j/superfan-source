package com.techelevator.dao.mapper;

import com.techelevator.model.Subgenre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubgenreMapper implements RowMapper<Subgenre> {

    @Override
    public Subgenre mapRow(ResultSet rs, int i) throws SQLException {
        Subgenre subgenre = new Subgenre();
        subgenre.setSubgenreId(rs.getInt("subgenre_id"));
        subgenre.setSubgenreName(rs.getString("subgenre_name"));
        return subgenre;
    }
}
