package com.colibri.appconnect.data.firestore.document;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class MessageDoc extends FirestoreDocument{
    private String message;
    private Timestamp timestamp = Timestamp.now();

    public DocumentReference getSender() {
        return sender;
    }

    private DocumentReference sender;

    MessageDoc(){super();}

    public MessageDoc(String message, DocumentReference sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }
//    public void setMessage(String message) {
//        this.message = message;
//    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
//    public void setTimestamp(Timestamp timestamp) {
//        this.timestamp = timestamp;
//    }


    @Override
    public String toString() {
        return "MessageDoc{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", sender=" + sender.getPath() +
                '}';
    }
}
