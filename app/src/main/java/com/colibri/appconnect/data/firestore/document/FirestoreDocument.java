package com.colibri.appconnect.data.firestore.document;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirestoreDocument that = (FirestoreDocument) o;
        return Objects.equals(docId, that.docId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docId);
    }
}
