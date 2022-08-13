package com.colibri.appconnect.data.firestore.firestorelive;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.colibri.appconnect.data.firestore.firestorelive.util.FirestoreLiveUtil;
import com.colibri.appconnect.util.QueryStatus;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

class QueryLiveDataNative<T> extends LiveData<QueryStatus<List<T>>> {
    private final Query query;
    private final Class<T> aClass;

    private ListenerRegistration listener = null;

    QueryLiveDataNative(Query cr, Class<T> c){
        query = cr;
        aClass = c;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        listener = query.addSnapshotListener((querySnapshot, exception) -> {
            if (exception == null) {
                if (querySnapshot != null) {
                    setValue(new QueryStatus.Success<>(
                            FirestoreLiveUtil.QueryToPojo(querySnapshot,aClass)
                    ));
                }
            } else {
                Log.e("FireStoreLiveData", "", exception);
                setValue(new QueryStatus.Error<>(exception));
            }
        });
    }

    @Override
    protected void onInactive(){
        super.onInactive();

        if (listener != null) {
            listener.remove();
            listener = null;
        }
    }
}

class QueryLiveDataCustom<T> extends LiveData<QueryStatus<List<T>>> {
    private final Query query;
    private final IDocumentSnapshotParser<T> parser;

    private ListenerRegistration listener = null;

    QueryLiveDataCustom(Query cr, IDocumentSnapshotParser<T> parser){
        query = cr;
        this.parser =parser;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        listener = query.addSnapshotListener((querySnapshot, exception) -> {
            if (exception == null) {
                List<DocumentSnapshot> snapshotList = querySnapshot != null ? querySnapshot.getDocuments() : new ArrayList<>();
                List<T> tList = new ArrayList<>();
                for (DocumentSnapshot doc :
                        snapshotList) {
                    tList.add(parser.parse(doc));
                }
                setValue(new QueryStatus.Success<>(tList));
            } else {
                Log.e("FireStoreLiveData", "", exception);
                setValue(new QueryStatus.Error<>(exception));
            }
        });
    }

    @Override
    protected void onInactive(){
        super.onInactive();

        if (listener != null) {
            listener.remove();
            listener = null;
        }
    }
}

class QueryLiveDataRaw extends LiveData<QueryStatus<QuerySnapshot>> {
    private final Query query;

    private ListenerRegistration listener = null;

    QueryLiveDataRaw(Query cr){
        query = cr;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        listener = query.addSnapshotListener((querySnapshot, exception) -> {
            if (exception == null) {
                setValue(new QueryStatus.Success<>(querySnapshot));
            } else {
                Log.e("FireStoreLiveData", "", exception);
                setValue(new QueryStatus.Error<>(exception));
            }
        });
    }

    @Override
    protected void onInactive(){
        super.onInactive();

        if (listener != null) {
            listener.remove();
            listener = null;
        }
    }
}
