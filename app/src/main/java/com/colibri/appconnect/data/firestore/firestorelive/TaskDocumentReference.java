package com.colibri.appconnect.data.firestore.firestorelive;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.colibri.appconnect.util.QueryStatus;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

abstract class TaskReferenceLiveData<T, Q> extends TaskLiveData<T,Q>  {
    private LiveData<QueryStatus<T>> data;

    protected TaskReferenceLiveData(Task<Q> task) {
        super(task);
    }

    @Override
    public void onComplete(@NonNull Task<Q> task) {
        if (task.isSuccessful()) {
            final Q result = task.getResult();
            if (result == null) {
                final ExceptionListenerNoValue noValue = new ExceptionListenerNoValue();
                Log.e(TAG, "onEvent: ", noValue);
                setValue(new QueryStatus.Error<>(noValue));
            } else {
                data = getLiveData(result);
                data.observeForever(this::setValue);
            }
        } else {
            Throwable taskCompleteException = task.getException();
            Log.e("FireStoreLiveData", "", taskCompleteException);
            setValue(new QueryStatus.Error<>(taskCompleteException));
        }
    }

    @Override
    protected void onInactive(){
        super.onInactive();

        if (data != null) {
            data.removeObserver(this::setValue);
        }
    }

    abstract LiveData<QueryStatus<T>> getLiveData(Q result);

    @Override
    final QueryStatus<T> onResult(@NonNull Q value) {
        return null;
    }

    private static final String TAG = "AP::TaskLiveData";
}

class TaskDocumentReferenceLiveDataNative<T> extends TaskReferenceLiveData<T,DocumentReference> {
    private final Class<T> aClass;

    protected TaskDocumentReferenceLiveDataNative(Task<DocumentReference> task, Class<T> aClass) {
        super(task);
        this.aClass = aClass;
    }

    @Override
    LiveData<QueryStatus<T>> getLiveData(DocumentReference result) {
        return DocumentTo.liveData(result, aClass);
    }
}

class TaskDocumentReferenceLiveDataCustom<T> extends TaskReferenceLiveData<T,DocumentReference> {
    private final IDocumentSnapshotParser<T> parser;

    protected TaskDocumentReferenceLiveDataCustom(Task<DocumentReference> task, IDocumentSnapshotParser<T> parser) {
        super(task);
        this.parser = parser;
    }

    @Override
    LiveData<QueryStatus<T>> getLiveData(DocumentReference result) {
        return DocumentTo.liveData(result, parser);
    }
}

class TaskDocumentReferenceLiveDataRaw extends TaskReferenceLiveData<DocumentSnapshot,DocumentReference> {

    protected TaskDocumentReferenceLiveDataRaw(Task<DocumentReference> task) {
        super(task);
    }

    @Override
    LiveData<QueryStatus<DocumentSnapshot>> getLiveData(DocumentReference result) {
        return DocumentTo.liveData(result);
    }
}