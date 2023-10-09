package com.techelevator.dao.mapper;
import com.techelevator.model.Band;
import com.techelevator.model.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet rs, int i) throws SQLException {
        Message message = new Message();
        message.setMessageId(rs.getInt("message_id"));
        message.setMessageBody(rs.getString("message_body"));
        message.setSendDate(rs.getTimestamp("send_date").toLocalDateTime());
        message.setSenderBandId(rs.getInt("sender_band_id"));
        message.setVisible(rs.getBoolean("is_visible"));
        return message;
    }
}
