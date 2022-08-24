package com.colibri.appconnect.data.firestore.firestorelive;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.colibri.appconnect.data.firestore.document.FirestoreDocument;
import com.colibri.appconnect.data.firestore.firestorelive.util.FirestoreLiveUtil;
import com.colibri.appconnect.util.QueryStates;
import com.colibri.appconnect.util.QueryStatus;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class DocumentLiveDataNative<T> extends DocumentLiveData<T>{
    private final Class<T> valueType;
    DocumentLiveDataNative(DocumentReference documentReference, Class<T> valueType) {
        super(documentReference);
        this.valueType = valueType;
    }


    @Override
    QueryStatus<T> onNewDocument(@NonNull DocumentSnapshot value) {
        return FirestoreLiveUtil.NewDocumentToPojo(value, this.valueType);
    }

    @Override
    QueryStatus<T> onSuccessResponse(@NonNull DocumentSnapshot value) {
        final T pojo = FirestoreLiveUtil.DocumentToPojo(value, this.valueType);
        if (pojo == null) {
            return new QueryStatus.Error<T>("Document couldn't be converted to POJO");
        }
        return new QueryStatus.Success<>(pojo);
    }
}

class DocumentLiveDataCustom<T> extends DocumentLiveData<T> {
    private static final String TAG = "AP::DocLiveDataCustom";
    private final IDocumentSnapshotParser<T> parser;

    DocumentLiveDataCustom(DocumentReference cr, IDocumentSnapshotParser<T> parser){
        super(cr);
        this.parser =parser;
    }

    @Override
    QueryStatus<T> onNewDocument(@NonNull DocumentSnapshot value) {
        final T parsed = parser.parse(value);
        if (parsed == null) {
            final ExceptionParserNoValue parserNoValue = new ExceptionParserNoValue();
            Log.e(TAG, "onNewDocument: ", parserNoValue);
            return new QueryStatus.Error<>(parserNoValue);
        }
        return new QueryStatus.NewDocument<>(parsed);
    }

    @Override
    QueryStatus<T> onSuccessResponse(@NonNull DocumentSnapshot value) {
        final T parsed = parser.parse(value);
        if (parsed == null) {
            final ExceptionParserNoValue parserNoValue = new ExceptionParserNoValue();
            Log.e(TAG, "onSuccessResponse: ", parserNoValue);
            return new QueryStatus.Error<>(parserNoValue);
        }
        return new QueryStatus.Success<>(parsed);
    }
}

class DocumentLiveDataRaw extends DocumentLiveData<DocumentSnapshot>{
    DocumentLiveDataRaw(DocumentReference documentReference) {
        super(documentReference);
    }

    private static final String TAG = "AP::DocLiveDataRaw";

    @Override
    QueryStatus<DocumentSnapshot> onNewDocument(@NonNull DocumentSnapshot value) {
        return new QueryStatus.NewDocument<>(value);
    }

    @Override
    QueryStatus<DocumentSnapshot> onSuccessResponse(@NonNull DocumentSnapshot value) {
        return new QueryStatus.Success<>(value);
    }
}

