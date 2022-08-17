package com.colibri.appconnect.data.entity;

import androidx.lifecycle.LiveData;

import com.colibri.appconnect.data.firestore.document.ChatDoc;
import com.colibri.appconnect.data.firestore.document.MessageDoc;
import com.colibri.appconnect.data.firestore.firestorelive.CollectionTo;
import com.colibri.appconnect.util.QueryStatus;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Exclude;
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

    private CollectionReference getMessageCollection(){
        return document.getDocumentReference().collection("messages");
    }

    private Query getMessagesQuery() {
        return getMessageCollection().orderBy("timeStamp");
    }

    public LiveData<QueryStatus<List<MessageDoc>>> getLiveMessages() {
        return CollectionTo.liveData(getMessagesQuery(), MessageDoc.class);
    }

    public LiveData<QueryStatus<List<MessageDoc>>> getMessagesSnapshot() {
        return CollectionTo.liveData(getMessagesQuery().get(), MessageDoc.class);
    }
    
    public void sendMessage(Message message){
        // TODO: 2022-08-17 To implement 
    }
}
