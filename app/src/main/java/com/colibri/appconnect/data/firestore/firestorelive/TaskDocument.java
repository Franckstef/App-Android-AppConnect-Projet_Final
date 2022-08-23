package com.colibri.appconnect.data.firestore.firestorelive;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.colibri.appconnect.data.firestore.firestorelive.util.FirestoreLiveUtil;
import com.colibri.appconnect.util.QueryStatus;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

class TaskDocumentLiveDataNative<T> extends LiveData<QueryStatus<T>> {
    private final Task<DocumentSnapshot> task;
    private final Class<T> aClass;
    TaskDocumentLiveDataNative(Task<DocumentSnapshot> task, Class<T> c){
        this.task = task;
        aClass = c;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        task.addOnCompleteListener(taskComplete -> {
            if (taskComplete.isSuccessful()) {
                DocumentSnapshot documentSnapshot = taskComplete.getResult();
                final T pojo = FirestoreLiveUtil.DocumentToPojo(documentSnapshot, aClass);
                if (pojo == null) {
                    setValue(new QueryStatus.Error<>("No Document Found"));
                } else {
                    setValue(new QueryStatus.Success<>(
                            pojo
                    ));
                }
            } else {
                Throwable taskCompleteException = taskComplete.getException();
                Log.e("FireStoreLiveData", "", taskCompleteException);
                setValue(new QueryStatus.Error<>(taskCompleteException));
            }
        });
    }
}

class TaskDocumentLiveDataCustom<T> extends LiveData<QueryStatus<T>> {
    private final Task<DocumentSnapshot> task;
    private final IDocumentSnapshotParser<T> parser;
    TaskDocumentLiveDataCustom(Task<DocumentSnapshot> task, IDocumentSnapshotParser<T> parser){
        this.task = task;
        this.parser =parser;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        task.addOnCompleteListener(taskComplete -> {
            if (taskComplete.isSuccessful()) {
                DocumentSnapshot documentSnapshot = taskComplete.getResult();
                final T parsed = parser.parse(documentSnapshot);

                if (parsed == null) {
                    setValue(new QueryStatus.Error<>("No Document Found"));
                } else {
                    setValue(new QueryStatus.Success<>(
                            parsed
                    ));
                }
            } else {
                Throwable taskCompleteException = taskComplete.getException();
                Log.e("FireStoreLiveData", "", taskCompleteException);
                setValue(new QueryStatus.Error<>(taskCompleteException));
            }
        });
    }
}

class TaskDocumentLiveDataRaw extends LiveData<QueryStatus<DocumentSnapshot>> {
    private final Task<DocumentSnapshot> task;

    TaskDocumentLiveDataRaw(Task<DocumentSnapshot> task){
        this.task = task;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        task.addOnCompleteListener(taskComplete -> {
            if (taskComplete.isSuccessful()) {
                setValue(new QueryStatus.Success<>(taskComplete.getResult()));
            } else {
                Throwable taskCompleteException = taskComplete.getException();
                Log.e("FireStoreLiveData", "", taskCompleteException);
                setValue(new QueryStatus.Error<>(taskCompleteException));
            }
        });
    }
}
