package com.colibri.appconnect.data.firestore.firestorelive;

import android.util.Log;

import androidx.annotation.NonNull;

import com.colibri.appconnect.data.firestore.firestorelive.util.FirestoreLiveUtil;
import com.colibri.appconnect.util.QueryStatus;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

class TaskDocumentLiveDataNative<T> extends DocumentTaskLiveData<T> {
    private final Class<T> valueType;

    protected TaskDocumentLiveDataNative(Task<DocumentSnapshot> task, Class<T> valueType) {
        super(task);
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

class TaskDocumentLiveDataCustom<T> extends DocumentTaskLiveData<T> {
    private final IDocumentSnapshotParser<T> parser;

    protected TaskDocumentLiveDataCustom(Task<DocumentSnapshot> task, IDocumentSnapshotParser<T> parser) {
        super(task);
        this.parser = parser;
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

    private static final String TAG = "TaskDocumentLiveDataCus";
}

class TaskDocumentLiveDataRaw extends DocumentTaskLiveData<DocumentSnapshot> {

    protected TaskDocumentLiveDataRaw(Task<DocumentSnapshot> task) {
        super(task);
    }

    @Override
    QueryStatus<DocumentSnapshot> onNewDocument(@NonNull DocumentSnapshot value) {
        return new QueryStatus.NewDocument<>(value);
    }

    @Override
    QueryStatus<DocumentSnapshot> onSuccessResponse(@NonNull DocumentSnapshot value) {
        return new QueryStatus.Success<>(value);
    }
}
