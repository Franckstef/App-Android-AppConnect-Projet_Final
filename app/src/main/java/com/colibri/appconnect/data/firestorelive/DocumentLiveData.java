package com.colibri.appconnect.data.firestorelive;


import android.util.Log;

import androidx.lifecycle.LiveData;

import com.colibri.appconnect.util.QueryStatus;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

class DocumentLiveDataNative<T> extends LiveData<QueryStatus<T>> {
    private final DocumentReference documentReference;
    private final Class<T> aClass;

    private ListenerRegistration listener = null;

    DocumentLiveDataNative(DocumentReference documentReference, Class<T> c){
        this.documentReference = documentReference;
        aClass = c;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        listener = documentReference.addSnapshotListener((querySnapshot, exception) -> {
            if (exception == null) {
                if (querySnapshot != null) {
                    setValue(new QueryStatus.Success<>(querySnapshot.toObject(aClass)));
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

class DocumentLiveDataCustom<T> extends LiveData<QueryStatus<T>> {
    private final DocumentReference documentReference;
    private final IDocumentSnapshotParser<T> parser;

    private ListenerRegistration listener = null;

    DocumentLiveDataCustom(DocumentReference cr, IDocumentSnapshotParser<T> parser){
        documentReference = cr;
        this.parser =parser;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        listener = documentReference.addSnapshotListener((documentSnapshot, exception) -> {
            if (exception == null) {
                setValue(new QueryStatus.Success<>(parser.parse(documentSnapshot)));
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

class DocumentLiveDataRaw extends LiveData<QueryStatus<DocumentSnapshot>> {
    private final DocumentReference documentReference;

    private ListenerRegistration listener = null;

    DocumentLiveDataRaw(DocumentReference cr){
        documentReference = cr;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        listener = documentReference.addSnapshotListener((documentSnapshot, exception) -> {
            if (exception == null) {
                setValue(new QueryStatus.Success<>(documentSnapshot));
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
