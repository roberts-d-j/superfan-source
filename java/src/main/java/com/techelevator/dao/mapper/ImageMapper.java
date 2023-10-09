package com.techelevator.dao.mapper;

import com.techelevator.model.Image;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageMapper implements RowMapper<Image> {
    @Override
    public Image mapRow(ResultSet rs, int i) throws SQLException {
        Image image = new Image();
        image.setImageId(rs.getInt("image_id"));
        image.setFileName(rs.getString("filename"));
        byte[] imageData = rs.getBytes("image_data");
        String imageDataString = Base64.encodeBase64String(imageData);
        image.setImageData(imageDataString);

        return image;
    }
}
