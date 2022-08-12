package com.colibri.appconnect.data.firestorelive;

import com.google.firebase.firestore.DocumentSnapshot;

public interface IDocumentSnapshotParser<T> {
    T parse(DocumentSnapshot documentSnapshot);
}
