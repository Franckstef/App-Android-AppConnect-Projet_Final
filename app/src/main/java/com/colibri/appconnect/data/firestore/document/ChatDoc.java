package com.colibri.appconnect.data.firestore.document;

import androidx.lifecycle.LiveData;

import com.colibri.appconnect.util.QueryStatus;
import com.google.firebase.firestore.DocumentSnapshot;

public class ChatDoc extends FirestoreDocument {

    private String name;

    ChatDoc(){ super();}
    public ChatDoc(DocumentSnapshot snapshot){
        super(snapshot);
        if(snapshot.exists()){
            final ChatDoc toObject = snapshot.toObject(ChatDoc.class);
            this.name = toObject.getName();
        } else {
            // TODO: 2022-08-22 Implement what to do when document doesn't exist
            setName(snapshot.getId());
        }
    }
    public ChatDoc(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChatDoc{" +
                "docId='" + getDocId() + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public LiveData<QueryStatus<ChatDoc>> pushToFirebase() {
        return (LiveData<QueryStatus<ChatDoc>>) super.pushToFirebase();
    }
}
