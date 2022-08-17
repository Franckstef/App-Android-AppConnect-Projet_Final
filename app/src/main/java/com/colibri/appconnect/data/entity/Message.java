package com.colibri.appconnect.data.entity;

import com.google.firebase.Timestamp;

public class Message {
    private String textMessage;
    private Timestamp timestamp;

    Message(){}

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
