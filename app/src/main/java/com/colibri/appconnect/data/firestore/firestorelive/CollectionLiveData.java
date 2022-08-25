package com.colibri.appconnect.data.firestore.firestorelive;

import android.util.Log;

import androidx.annotation.NonNull;

import com.colibri.appconnect.data.firestore.firestorelive.util.FirestoreLiveUtil;
import com.colibri.appconnect.util.QueryStatus;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

class CollectionLiveDataNative<T> extends CollectionLiveData<List<T>> {
    private final Class<T> aClass;

    protected CollectionLiveDataNative(CollectionReference collectionReference, Class<T> aClass) {
        super(collectionReference);
        this.aClass = aClass;
    }

    @Override
    QueryStatus<List<T>> onResponse(@NonNull QuerySnapshot value) {
        return new QueryStatus.Success<>(FirestoreLiveUtil.QueryToPojo(value,aClass));
    }
}

class CollectionLiveDataCustom<T> extends CollectionLiveData<List<T>> {
    private final IDocumentSnapshotParser<T> parser;

    protected CollectionLiveDataCustom(CollectionReference collectionReference, IDocumentSnapshotParser<T> parser) {
        super(collectionReference);
        this.parser = parser;
    }

    @Override
    QueryStatus<List<T>> onResponse(@NonNull QuerySnapshot value) {
        List<DocumentSnapshot> snapshotList = value.getDocuments();
        List<T> tList = new ArrayList<>();
        for (DocumentSnapshot doc :
                snapshotList) {
            tList.add(parser.parse(doc));
        }
        return new QueryStatus.Success<>(tList);
    }
}

class CollectionLiveDataRaw extends CollectionLiveData<QuerySnapshot> {

    protected CollectionLiveDataRaw(CollectionReference collectionReference) {
        super(collectionReference);
    }

    @Override
    QueryStatus<QuerySnapshot> onResponse(@NonNull QuerySnapshot value) {
        return new QueryStatus.Success<>(value);
    }
}