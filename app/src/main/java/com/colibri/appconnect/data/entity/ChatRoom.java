package com.colibri.appconnect.data.entity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.colibri.appconnect.data.firestore.document.ChatDoc;
import com.colibri.appconnect.data.firestore.document.FirestoreDocument;
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

    public void setName(String name){
        document.setName(name);
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
    public LiveData<QueryStatus<ChatRoom>> pushToFirebase(){
         return Transformations.map(document.pushToFirebase(), input -> {
            switch (input.getState()) {
                case Success:
                    return new QueryStatus.Success<>(new ChatRoom(input.getData()));
                case Error:
                    return new QueryStatus.Error<>(input.getMessage());
                case Loading:
                    return new QueryStatus.Loading<>();
                default:
                    return new QueryStatus.Error<>("Unknown Error");
            }
        });

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
