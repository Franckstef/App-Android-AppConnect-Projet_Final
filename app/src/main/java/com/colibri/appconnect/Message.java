package com.colibri.appconnect;

import java.util.ArrayList;
import java.util.Date;

public class Message {
    private String id;
    private ArrayList<String> chatUsers;
    private Date createdAt;
    private String type; //0=text,1=audio,2=image,3=video,4=file
    private int status; //0=sending,1=sent,2=delivered,3=seen,4=failed
    private String textMessage;
    private Date deliveryTime;
    private Date seenTime;
    private String chatUserId;

    public Message(){}

    public Message(String id, ArrayList<String> chatUsers, Date createdAt, String type, int status, String textMessage, Date deliveryTime, Date seenTime, String chatUserId) {
        this.id = id;
        this.chatUsers = chatUsers;
        this.createdAt = createdAt;
        this.type = type;
        this.status = status;
        this.textMessage = textMessage;
        this.deliveryTime = deliveryTime;
        this.seenTime = seenTime;
        this.chatUserId = chatUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getChatUsers() {
        return chatUsers;
    }

    public void setChatUsers(ArrayList<String> chatUsers) {
        this.chatUsers = chatUsers;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getSeenTime() {
        return seenTime;
    }

    public void setSeenTime(Date seenTime) {
        this.seenTime = seenTime;
    }

    public String getChatUserId() {
        return chatUserId;
    }

    public void setChatUserId(String chatUserId) {
        this.chatUserId = chatUserId;
    }
}
