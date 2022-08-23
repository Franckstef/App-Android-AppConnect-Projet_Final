package com.colibri.appconnect.data.firestore.firestorelive;


import android.util.Log;

import androidx.lifecycle.LiveData;

import com.colibri.appconnect.data.firestore.document.FirestoreDocument;
import com.colibri.appconnect.data.firestore.firestorelive.util.FirestoreLiveUtil;
import com.colibri.appconnect.util.QueryStatus;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class DocumentLiveDataNative<T> extends LiveData<QueryStatus<T>> {
    private static final String TAG = "AP::DocLiveDataNative";
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
        listener = documentReference.addSnapshotListener((documentSnapshot, exception) -> {
            if (exception == null) {
                if (documentSnapshot != null) {
                    final T pojo = FirestoreLiveUtil.DocumentToPojo(documentSnapshot, aClass);
                    if (pojo == null) {
                        if(FirestoreDocument.class.isAssignableFrom(aClass)){
                            try {
                                final Constructor<? extends T> ctor = aClass.getConstructor(DocumentSnapshot.class);
                                Log.d(TAG, "onActive: Found Ctor");
                                final T newInstance = ctor.newInstance(documentSnapshot);
                                setValue(new QueryStatus.Error<>("No Document Found, but is FirestoreDoc",newInstance));
                            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                                setValue(new QueryStatus.Error<>(e.getMessage()));
                            }

                        } else {
                            setValue(new QueryStatus.Error<>("No Document Found"));
                        }
                    } else {
                        setValue(new QueryStatus.Success<>(
                                pojo
                        ));
                    }
                } else {
                    setValue(new QueryStatus.Error<>("No Document snapshot"));
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
                final T parsed = parser.parse(documentSnapshot);

                if (parsed == null) {
                    setValue(new QueryStatus.Error<>("No Document Found"));
                } else {
                    setValue(new QueryStatus.Success<>(
                            parsed
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
