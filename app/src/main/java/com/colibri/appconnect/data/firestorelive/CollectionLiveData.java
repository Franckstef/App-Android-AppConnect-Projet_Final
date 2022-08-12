package com.colibri.appconnect.data.firestorelive;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.colibri.appconnect.util.QueryStatus;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

class CollectionLiveDataNative<T> extends LiveData<QueryStatus<List<T>>> {
    private final CollectionReference collectionReference;
    private final Class<T> aClass;

    private ListenerRegistration listener = null;

    CollectionLiveDataNative(CollectionReference cr, Class<T> c){
        collectionReference = cr;
        aClass = c;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        listener = collectionReference.addSnapshotListener((querySnapshot, exception) -> {
            if (exception == null) {
                if (querySnapshot != null) {
                    setValue(new QueryStatus.Success<>(querySnapshot.toObjects(aClass)));
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

class CollectionLiveDataCustom<T> extends LiveData<QueryStatus<List<T>>> {
    private final CollectionReference collectionReference;
    private final IDocumentSnapshotParser<T> parser;

    private ListenerRegistration listener = null;

    CollectionLiveDataCustom(CollectionReference cr, IDocumentSnapshotParser<T> parser){
        collectionReference = cr;
        this.parser =parser;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        listener = collectionReference.addSnapshotListener((querySnapshot, exception) -> {
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

class CollectionLiveDataRaw extends LiveData<QueryStatus<QuerySnapshot>> {
    private final CollectionReference collectionReference;

    private ListenerRegistration listener = null;

    CollectionLiveDataRaw(CollectionReference cr){
        collectionReference = cr;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        listener = collectionReference.addSnapshotListener((querySnapshot, exception) -> {
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