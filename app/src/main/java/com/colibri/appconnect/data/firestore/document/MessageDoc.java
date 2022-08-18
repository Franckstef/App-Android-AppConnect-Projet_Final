package com.colibri.appconnect.data.firestore.document;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.colibri.appconnect.data.entity.User;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.Objects;

public class MessageDoc extends FirestoreDocument{
    private String textMessage;
    private String userToId;
    private Timestamp timestamp = Timestamp.now();

//    public DocumentReference getSender() {
//        return sender;
//    }

//    private DocumentReference sender;

    MessageDoc(){super();}

    public MessageDoc(String textMessage, String userToId) {
        this.textMessage = textMessage;
        this.userToId = userToId;
    }

    public String getUserToId() {
        return userToId;
    }

    public MessageDoc setUserToId(String userToId) {
        this.userToId = userToId;
        return this;
    }

    public MessageDoc setTextMessage(String textMessage) {
        this.textMessage = textMessage;
        return this;
    }

    public MessageDoc setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
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

    @Override
    public int hashCode() {
        return Objects.hash(textMessage);
    }

    public static DiffUtil.ItemCallback<MessageDoc> diffCallback = new DiffUtil.ItemCallback<MessageDoc>() {
        @Override
        public boolean areItemsTheSame(@NonNull MessageDoc oldItem, @NonNull MessageDoc newItem) {
            boolean b = Objects.equals(oldItem.textMessage, newItem.textMessage);
//            Log.d(TAG, "areItemsTheSame: " + b + " - Old: " + oldItem + " - New: " + newItem);
            return b;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MessageDoc oldItem, @NonNull MessageDoc newItem) {
//            Log.d(TAG, "areItemsTheSame: " + oldItem.equals(newItem) + " - Old: " + oldItem + " - New: " + newItem);
            return oldItem.equals(newItem);
        }
    };
}
