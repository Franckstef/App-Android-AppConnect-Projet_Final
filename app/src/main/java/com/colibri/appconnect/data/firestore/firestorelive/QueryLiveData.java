package com.colibri.appconnect.data.firestore.firestorelive;

import androidx.annotation.NonNull;

import com.colibri.appconnect.data.firestore.firestorelive.util.FirestoreLiveUtil;
import com.colibri.appconnect.util.QueryStatus;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

class QueryLiveDataNative<T> extends QueryLiveData<List<T>> {
    private final Class<T> aClass;

    protected QueryLiveDataNative(Query query, Class<T> aClass) {
        super(query);
        this.aClass = aClass;
    }

    @Override
    QueryStatus<List<T>> onResponse(@NonNull QuerySnapshot value) {
        return new QueryStatus.Success<>(FirestoreLiveUtil.QueryToPojo(value,aClass));
    }
}

class QueryLiveDataCustom<T> extends QueryLiveData<List<T>> {

    private final IDocumentSnapshotParser<T> parser;

    protected QueryLiveDataCustom(Query query, IDocumentSnapshotParser<T> parser) {
        super(query);
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

class QueryLiveDataRaw extends QueryLiveData<QuerySnapshot> {

    protected QueryLiveDataRaw(Query query) {
        super(query);
    }

    @Override
    QueryStatus<QuerySnapshot> onResponse(@NonNull QuerySnapshot value) {
        return new QueryStatus.Success<>(value);
    }

}
