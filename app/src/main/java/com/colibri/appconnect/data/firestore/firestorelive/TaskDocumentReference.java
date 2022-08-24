package com.colibri.appconnect.data.firestore.firestorelive;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.colibri.appconnect.util.QueryStatus;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

class TaskDocumentReferenceLiveDataNative<T> extends LiveData<QueryStatus<T>> {
    private final Task<DocumentReference> task;
    private final Class<T> aClass;
    private LiveData<QueryStatus<T>> data;
    TaskDocumentReferenceLiveDataNative(Task<DocumentReference> task, Class<T> c){
        this.task = task;
        aClass = c;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        task.addOnCompleteListener(taskComplete -> {
            if (taskComplete.isSuccessful()) {
                DocumentReference reference = taskComplete.getResult();
                data = DocumentTo.liveData(reference, aClass);
                data.observeForever(this::setValue);
            } else {
                Throwable taskCompleteException = taskComplete.getException();
                Log.e("FireStoreLiveData", "", taskCompleteException);
                setValue(new QueryStatus.Error<>(taskCompleteException));
            }
        });
    }
    @Override
    protected void onInactive(){
        super.onInactive();

        if (data != null) {
            data.removeObserver(this::setValue);
        }
    }
}

class TaskDocumentReferenceLiveDataCustom<T> extends LiveData<QueryStatus<T>> {
    private final Task<DocumentReference> task;
    private final IDocumentSnapshotParser<T> parser;
    private LiveData<QueryStatus<T>> data;
    TaskDocumentReferenceLiveDataCustom(Task<DocumentReference> task, IDocumentSnapshotParser<T> parser){
        this.task = task;
        this.parser =parser;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        task.addOnCompleteListener(taskComplete -> {
            if (taskComplete.isSuccessful()) {
                DocumentReference reference = taskComplete.getResult();
                data = DocumentTo.liveData(reference, parser);
                data.observeForever(this::setValue);
            } else {
                Throwable taskCompleteException = taskComplete.getException();
                Log.e("FireStoreLiveData", "", taskCompleteException);
                setValue(new QueryStatus.Error<>(taskCompleteException));
            }
        });
    }

    @Override
    protected void onInactive(){
        super.onInactive();

        if (data != null) {
            data.removeObserver(this::setValue);
        }
    }
}

class TaskDocumentReferenceLiveDataRaw extends LiveData<QueryStatus<DocumentSnapshot>> {
    private final Task<DocumentReference> task;
    private LiveData<QueryStatus<DocumentSnapshot>> data;
    TaskDocumentReferenceLiveDataRaw(Task<DocumentReference> task){
        this.task = task;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        task.addOnCompleteListener(taskComplete -> {
            if (taskComplete.isSuccessful()) {
                final DocumentReference reference = taskComplete.getResult();
                data = DocumentTo.liveData(reference);
                data.observeForever(this::setValue);
            } else {
                Throwable taskCompleteException = taskComplete.getException();
                Log.e("FireStoreLiveData", "", taskCompleteException);
                setValue(new QueryStatus.Error<>(taskCompleteException));
            }
        });
    }

    @Override
    protected void onInactive(){
        super.onInactive();

        if (data != null) {
            data.removeObserver(this::setValue);
        }
    }
}