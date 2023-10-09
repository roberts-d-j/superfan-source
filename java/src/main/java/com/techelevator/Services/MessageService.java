package com.techelevator.Services;

import com.techelevator.dao.BandDao;
import com.techelevator.dao.MessageDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageDao messageDao;
    private final UserDao userDao;
    private final BandDao bandDao;
    public MessageService(MessageDao messageDao, BandDao bandDao, UserDao userDao) {
        this.messageDao = messageDao;
        this.bandDao = bandDao;
        this.userDao = userDao;
    }

    public Message getMessageById(int messageId) {
        return messageDao.getMessageById(messageId);
    }

    public List<Message> getAllMessagesToUser(int userId) {
        return messageDao.getAllMessagesToUser(userId);
    }

    public List<Message> getAllMessagesFromBand(int bandId) {
        return messageDao.getAllMessagesFromBand(bandId);
    }

    public Message sendMessage(Message message) {
        Message newMessage = messageDao.sendMessage(message);
        List<Integer> usersFollowing = userDao.getAllUsersFollowingBand(newMessage.getSenderBandId());
        for(int i = 0; i < usersFollowing.size(); i++) {
            messageDao.addMessageToUserMessages(newMessage.getMessageId(), usersFollowing.get(i));
        }
        return newMessage;
    }

    public boolean hideMessageForUser(int messageId, int userId) {
        return messageDao.hideMessageForUser(messageId, userId);
    }

    public boolean hideMessageForBand(int messageId) {
        return messageDao.hideMessageForBand(messageId);
    }

    public List<Message> getAllMessagesToUserOrderedByBandName(int userId) {
        return messageDao.getAllMessagesToUserOrderedByBandName(userId);
    }


}
