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
}
