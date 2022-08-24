package com.colibri.appconnect.data.firestore.firestorelive;


import androidx.annotation.NonNull;

import com.colibri.appconnect.data.firestore.firestorelive.util.FirestoreLiveUtil;
import com.colibri.appconnect.util.QueryStatus;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

class TaskCollectionLiveDataNative<T> extends TaskLiveData<List<T>, QuerySnapshot> {
    private final Class<T> aClass;

    protected TaskCollectionLiveDataNative(Task<QuerySnapshot> task, Class<T> aClass) {
        super(task);
        this.aClass = aClass;
    }


    @Override
    QueryStatus<List<T>> onResult(@NonNull QuerySnapshot value) {
        return new QueryStatus.Success<>(FirestoreLiveUtil.QueryToPojo(value,aClass));
    }
}

class TaskCollectionLiveDataCustom<T> extends TaskLiveData<List<T>, QuerySnapshot> {
    private final IDocumentSnapshotParser<T> parser;

    protected TaskCollectionLiveDataCustom(Task<QuerySnapshot> task, IDocumentSnapshotParser<T> parser) {
        super(task);
        this.parser = parser;
    }

    @Override
    QueryStatus<List<T>> onResult(@NonNull QuerySnapshot value) {
        List<DocumentSnapshot> snapshotList = value != null ? value.getDocuments() : new ArrayList<>();
        List<T> tList = new ArrayList<>();
        for (DocumentSnapshot doc :
                snapshotList) {
            tList.add(parser.parse(doc));
        }
        return new QueryStatus.Success<>(tList);
    }
}

class TaskCollectionLiveDataRaw extends TaskLiveData<QuerySnapshot, QuerySnapshot> {

    protected TaskCollectionLiveDataRaw(Task<QuerySnapshot> task) {
        super(task);
    }

    @Override
    QueryStatus<QuerySnapshot> onResult(@NonNull QuerySnapshot value) {
        return new QueryStatus.Success<>(value);
    }


}

