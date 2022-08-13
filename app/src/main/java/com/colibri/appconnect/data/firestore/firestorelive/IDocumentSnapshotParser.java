package com.colibri.appconnect.data.firestore.firestorelive;

import com.google.firebase.firestore.DocumentSnapshot;

public interface IDocumentSnapshotParser<T> {
    T parse(DocumentSnapshot documentSnapshot);
}
