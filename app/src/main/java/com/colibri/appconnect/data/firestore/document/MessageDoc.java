package com.colibri.appconnect.data.firestore.document;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class MessageDoc extends FirestoreDocument{
    private String textMessage;
    private Timestamp timestamp = Timestamp.now();

//    public DocumentReference getSender() {
//        return sender;
//    }

//    private DocumentReference sender;

    MessageDoc(){super();}

    public MessageDoc(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }



    @Override
    public String toString() {
        return "MessageDoc{" +
                "message='" + textMessage + '\'' +
//                ", timestamp=" + timestamp +
                '}';
    }
}
