package com.colibri.appconnect.data.firestore.firestorelive;


import android.util.Log;

import androidx.lifecycle.LiveData;

import com.colibri.appconnect.data.firestore.firestorelive.util.FirestoreLiveUtil;
import com.colibri.appconnect.util.QueryStatus;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

class TaskCollectionLiveDataNative<T> extends LiveData<QueryStatus<List<T>>> {
    private final Task<QuerySnapshot> task;
    private final Class<T> aClass;
    TaskCollectionLiveDataNative(Task<QuerySnapshot> task, Class<T> c){
        this.task = task;
        aClass = c;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        task.addOnCompleteListener(taskComplete -> {
            if (taskComplete.isSuccessful()) {
                QuerySnapshot querySnapshot = taskComplete.getResult();
                setValue(new QueryStatus.Success<>(
                        FirestoreLiveUtil.QueryToPojo(querySnapshot,aClass)
                ));
            } else {
                Throwable taskCompleteException = taskComplete.getException();
                Log.e("FireStoreLiveData", "", taskCompleteException);
                setValue(new QueryStatus.Error<>(taskCompleteException));
            }
        });
    }
}

class TaskCollectionLiveDataCustom<T> extends LiveData<QueryStatus<List<T>>> {
    private final Task<QuerySnapshot> task;
    private final IDocumentSnapshotParser<T> parser;
    TaskCollectionLiveDataCustom(Task<QuerySnapshot> task, IDocumentSnapshotParser<T> parser){
        this.task = task;
        this.parser =parser;
    }

    @Override
    protected void onActive(){
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        task.addOnCompleteListener(taskComplete -> {
            if (taskComplete.isSuccessful()) {
                QuerySnapshot querySnapshot = taskComplete.getResult();
                List<DocumentSnapshot> snapshotList = querySnapshot != null ? querySnapshot.getDocuments() : new ArrayList<>();
                List<T> tList = new ArrayList<>();
                for (DocumentSnapshot doc :
                        snapshotList) {
                    tList.add(parser.parse(doc));
                }
                setValue(new QueryStatus.Success<>(tList));
            } else {
                Throwable taskCompleteException = taskComplete.getException();
                Log.e("FireStoreLiveData", "", taskCompleteException);
                setValue(new QueryStatus.Error<>(taskCompleteException));
            }
        });
    }
}

class TaskCollectionLiveDataRaw extends LiveData<QueryStatus<QuerySnapshot>> {
    private final Task<QuerySnapshot> task;

    TaskCollectionLiveDataRaw(Task<QuerySnapshot> task){
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

