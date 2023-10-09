package com.techelevator.model;

import java.time.LocalDateTime;

public class Message {
    private int messageId;
    private int senderBandId;
    private String messageBody;
    private LocalDateTime sendDate;
    private boolean isVisible;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getSenderBandId() {
        return senderBandId;
    }

    public void setSenderBandId(int senderBandId) {
        this.senderBandId = senderBandId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
