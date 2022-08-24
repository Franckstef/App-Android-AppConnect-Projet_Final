package com.colibri.appconnect.data.firestore.document;

import androidx.lifecycle.LiveData;

import com.colibri.appconnect.data.firestore.firestorelive.CollectionTo;
import com.colibri.appconnect.util.QueryStatus;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class ChatDoc extends FirestoreDocument {

    private String name;

    ChatDoc(){ super();}
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
}
