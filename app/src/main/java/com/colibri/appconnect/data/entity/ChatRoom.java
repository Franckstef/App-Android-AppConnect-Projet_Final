package com.colibri.appconnect.data.entity;

import androidx.lifecycle.LiveData;

import com.colibri.appconnect.data.firestore.document.ChatDoc;
import com.colibri.appconnect.data.firestore.document.MessageDoc;
import com.colibri.appconnect.data.firestore.firestorelive.CollectionTo;
import com.colibri.appconnect.util.QueryStatus;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import java.util.List;

///////////////////////////////////////////////////////////////////////////
// ChatRoom
// Cette classe sert à gérer une ChatRoom
// Elle a la responsabilité de mettre à jour la base de donnée
///////////////////////////////////////////////////////////////////////////
public class ChatRoom {
    private ChatDoc document;

    public ChatRoom(ChatDoc document) {
        this.document = document;
    }

    public String getName(){
        return document.getName();
    }

    private CollectionReference getMessageCollection(){
        return document.getDocumentReference().collection("messages");
    }

    private Query getMessagesQuery() {
//        Log.d(TAG, "getMessagesQuery: Collection Path: "+ getMessageCollection().getPath());
        return getMessageCollection().orderBy("timestamp");
    }

    public LiveData<QueryStatus<List<MessageDoc>>> getLiveMessages() {
        return CollectionTo.liveData(getMessagesQuery(), MessageDoc.class);

    }

    public LiveData<QueryStatus<List<MessageDoc>>> getMessagesSnapshot() {
        return CollectionTo.liveData(getMessagesQuery().get(), MessageDoc.class);
    }
    
    public void sendMessage(MessageDoc message, OnMessageSend callback){
       getMessageCollection().add(message).addOnCompleteListener(task -> {
           if(callback != null){
                if(task.isSuccessful()){
                       callback.callback();
               }
           }
       });
    }

    @Override
    public String toString() {
        if (document != null)
            return document.toString();
        else{
            return "Null Document";
        }
    }

    private static final String TAG = "AP::ChatRoom";
}
