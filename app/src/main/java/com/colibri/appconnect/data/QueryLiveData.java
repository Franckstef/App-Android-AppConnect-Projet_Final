package com.colibri.appconnect.data;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.colibri.appconnect.util.QueryStatus;

public class QueryLiveData<T> extends LiveData<QueryStatus<T>> {
    public QueryLiveData(T value) {
        super(new QueryStatus.Success<>(value));
    }

    public QueryLiveData() {
        super(new QueryStatus.Loading<>());
    }

    protected void setError(String message){
        super.setValue(new QueryStatus.Error<T>(message));
    }

    protected void setLoading(){
        super.setValue(new QueryStatus.Loading<>());
    }

    @Nullable
    @Override
    public QueryStatus<T> getValue() {
        return super.getValue();
    }

    @Override
    protected void setValue(QueryStatus<T> value) {
        super.setValue(value);
    }
}
