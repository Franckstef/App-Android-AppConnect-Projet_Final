package com.colibri.appconnect.data.firestore.document;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public abstract  class FirestoreDocument {
    @DocumentId private String docId;
    @Exclude private DocumentReference documentReference;

    FirestoreDocument(){}

    public String getDocId() {
        return docId;
    }

    @Exclude
    public void setDocumentReference(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    @Exclude
    public DocumentReference getDocumentReference() {
        return documentReference;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }


}
