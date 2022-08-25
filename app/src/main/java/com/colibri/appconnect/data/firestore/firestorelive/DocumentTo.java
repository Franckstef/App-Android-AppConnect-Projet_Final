package com.colibri.appconnect.data.firestore.firestorelive;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;


import com.colibri.appconnect.util.QueryStatus;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class DocumentTo {
    @NonNull
    public static <T> LiveData<QueryStatus<T>> liveData(DocumentReference documentReference, Class<T> tClass) {
        return new DocumentLiveDataNative<>(documentReference, tClass);
    }

    @NonNull
    public static <T> LiveData<QueryStatus<T>> liveData(Task<DocumentSnapshot> task, Class<T> tClass) {
        return new TaskDocumentLiveDataNative<>(task, tClass);
    }

    @NonNull
    public static <T> LiveData<QueryStatus<T>> liveData(DocumentReference documentReference, IDocumentSnapshotParser<T> parser) {
        return new DocumentLiveDataCustom<>(documentReference, parser);
    }

    @NonNull
    public static <T> LiveData<QueryStatus<T>> liveData(Task<DocumentSnapshot> task, IDocumentSnapshotParser<T> parser) {
        return new TaskDocumentLiveDataCustom<>(task, parser);
    }

    @NonNull
    public static LiveData<QueryStatus<DocumentSnapshot>> liveData(DocumentReference documentReference) {
        return new DocumentLiveDataRaw(documentReference);
    }

    @NonNull
    public static LiveData<QueryStatus<DocumentSnapshot>> liveData(Task<DocumentSnapshot> task) {
        return new TaskDocumentLiveDataRaw(task);
    }


}
