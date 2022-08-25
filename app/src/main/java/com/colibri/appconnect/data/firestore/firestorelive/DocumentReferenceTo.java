package com.colibri.appconnect.data.firestore.firestorelive;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.colibri.appconnect.util.QueryStatus;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class DocumentReferenceTo {
    @NonNull
    public static <T> LiveData<QueryStatus<T>> liveData(Task<DocumentReference> task, Class<T> tClass) {
        return new TaskDocumentReferenceLiveDataNative<>(task, tClass);
    }

    @NonNull
    public static <T> LiveData<QueryStatus<T>> liveData(Task<DocumentReference> task, IDocumentSnapshotParser<T> parser) {
        return new TaskDocumentReferenceLiveDataCustom<>(task, parser);
    }

    @NonNull
    public static LiveData<QueryStatus<DocumentSnapshot>> liveData(Task<DocumentReference> task) {
        return new TaskDocumentReferenceLiveDataRaw(task);
    }
}
