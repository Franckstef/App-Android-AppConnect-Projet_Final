package com.colibri.appconnect.data.firestore.firestorelive;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.colibri.appconnect.util.QueryStatus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

class ExceptionListenerNoValue extends Exception{
    public ExceptionListenerNoValue() {
        super("Snapshot listener returned a NULL value");
    }
}

class ExceptionParserNoValue extends Exception{
    public ExceptionParserNoValue() {
        super("Snapshot parser returned a NULL value");
    }
}

abstract class DocumentTaskLiveData<T> extends TaskLiveData<T,DocumentSnapshot>{

    protected DocumentTaskLiveData(Task<DocumentSnapshot> task) {
        super(task);
    }

    @Override
    QueryStatus<T> onResult(@NonNull DocumentSnapshot value) {
        if(!value.exists()){
            return onNewDocument(value);
        }
        return onSuccessResponse(value);

    }

    abstract QueryStatus<T> onNewDocument(@NonNull DocumentSnapshot value);
    abstract QueryStatus<T> onSuccessResponse(@NonNull DocumentSnapshot value);
}

abstract class CollectionTaskLiveData<T> extends TaskLiveData<T,QuerySnapshot>{

    protected CollectionTaskLiveData(Task<QuerySnapshot> task) {
        super(task);
    }
}
abstract class TaskLiveData<T, Q> extends LiveData<QueryStatus<T>> implements OnCompleteListener<Q> {
    private final Task<Q> task;

    protected TaskLiveData(Task<Q> task) {
        this.task = task;
    }

    @Override
    protected void onActive() {
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        task.addOnCompleteListener(this);
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
                setValue(onResult(result));
            }
        } else {
            Throwable taskCompleteException = task.getException();
            Log.e("FireStoreLiveData", "", taskCompleteException);
            setValue(new QueryStatus.Error<>(taskCompleteException));
        }
    }

    abstract QueryStatus<T> onResult(@NonNull Q value);

    private static final String TAG = "AP::TaskLiveData";
}
public abstract class ListenerLiveData<T,Q> extends LiveData<QueryStatus<T>> implements EventListener<Q> {

    private ListenerRegistration listener = null;

    @Override
    protected void onActive() {
        super.onActive();
        setValue(new QueryStatus.Loading<>());
        listener = addSnapshotListener(this);
    }

    protected abstract ListenerRegistration addSnapshotListener(@NonNull EventListener<Q> eventListener);

    @Override
    protected void onInactive() {
        super.onInactive();
        if (listener != null) {
            listener.remove();
            listener = null;
        }
    }

    @Override
    public void onEvent(@Nullable Q value, @Nullable FirebaseFirestoreException error) {
        if (error == null) {
            if (value == null) {
                final ExceptionListenerNoValue noValue = new ExceptionListenerNoValue();
                Log.e(TAG, "onEvent: ", noValue);
                setValue(new QueryStatus.Error<>(noValue));
            } else {
                setValue(onResponse(value));
            }
        } else {
            Log.e(TAG, "onEvent: ", error);
            setValue(new QueryStatus.Error<>(error));
        }
    }

    abstract QueryStatus<T> onResponse(@NonNull Q value);
    private static final String TAG = "AP::ListenerLiveData";
}

abstract class QueryLiveData<T> extends ListenerLiveData<T, QuerySnapshot>{
    private final Query query;

    protected QueryLiveData(Query query) {
        this.query = query;
    }

    @Override
    protected ListenerRegistration addSnapshotListener(@NonNull EventListener<QuerySnapshot> eventListener) {
        return query.addSnapshotListener(eventListener);
    }
}

abstract class CollectionLiveData<T> extends ListenerLiveData<T, QuerySnapshot>{
    private final CollectionReference collectionReference;

    protected CollectionLiveData(CollectionReference collectionReference) {
        this.collectionReference = collectionReference;
    }

    @Override
    protected ListenerRegistration addSnapshotListener(@NonNull EventListener<QuerySnapshot> eventListener) {
        return collectionReference.addSnapshotListener(eventListener);
    }
}
abstract class DocumentLiveData<T> extends ListenerLiveData<T, DocumentSnapshot>{
    private final DocumentReference documentReference;

    DocumentLiveData(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    @Override
    protected ListenerRegistration addSnapshotListener(@NonNull EventListener<DocumentSnapshot> eventListener) {
        return documentReference.addSnapshotListener(eventListener);
    }

    @Override
    QueryStatus<T> onResponse(@NonNull DocumentSnapshot value) {
        if(!value.exists()){
            return onNewDocument(value);
        }
        return onSuccessResponse(value);

    }

    abstract QueryStatus<T> onNewDocument(@NonNull DocumentSnapshot value);
    abstract QueryStatus<T> onSuccessResponse(@NonNull DocumentSnapshot value);
    private static final String TAG = "AP::DocLiveData";
}
