package com.colibri.appconnect.data.firestore.firestorelive;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.colibri.appconnect.util.QueryStatus;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.List;

public class CollectionTo {
    @NonNull
    public static <T> LiveData<QueryStatus<List<T>>> liveData(CollectionReference collectionReference, Class<T> tClass) {
        return new CollectionLiveDataNative<>(collectionReference, tClass);
    }

    @NonNull
    public static <T> LiveData<QueryStatus<List<T>>> liveData(Query collectionReference, Class<T> tClass) {
        return new QueryLiveDataNative<>(collectionReference, tClass);
    }

    @NonNull
    public static <T> LiveData<QueryStatus<List<T>>> liveData(Task<QuerySnapshot> task, Class<T> tClass) {
        return new TaskCollectionLiveDataNative<>(task, tClass);
    }

    @NonNull
    public static <T> LiveData<QueryStatus<List<T>>> liveData(CollectionReference collectionReference, IDocumentSnapshotParser<T> parser) {
        return new CollectionLiveDataCustom<>(collectionReference, parser);
    }

    @NonNull
    public static <T> LiveData<QueryStatus<List<T>>> liveData(Query collectionReference, IDocumentSnapshotParser<T> parser) {
        return new QueryLiveDataCustom<>(collectionReference, parser);
    }

    @NonNull
    public static <T> LiveData<QueryStatus<List<T>>> liveData(Task<QuerySnapshot> task, IDocumentSnapshotParser<T> parser) {
        return new TaskCollectionLiveDataCustom<>(task, parser);
    }

    @NonNull
    public static LiveData<QueryStatus<QuerySnapshot>> liveData(CollectionReference collectionReference) {
        return new CollectionLiveDataRaw(collectionReference);
    }

    @NonNull
    public static LiveData<QueryStatus<QuerySnapshot>> liveData(Query collectionReference) {
        return new QueryLiveDataRaw(collectionReference);
    }

    @NonNull
    public static LiveData<QueryStatus<QuerySnapshot>> liveData(Task<QuerySnapshot> task) {
        return new TaskCollectionLiveDataRaw(task);
    }

}
