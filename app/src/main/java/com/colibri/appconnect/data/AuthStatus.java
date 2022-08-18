package com.colibri.appconnect.data;

import androidx.annotation.NonNull;

import com.colibri.appconnect.util.QueryStates;

public class AuthStatus<T> {
    protected QueryStates state;
    protected T data = null;
    protected String message = null;
    protected Throwable error = null;

    protected AuthStatus(QueryStates status){
        state = status;
    }

    public QueryStates getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Boolean isSuccessful(){
        return state == QueryStates.Success;
    }

    public Boolean isFailed(){
        return state == QueryStates.Error;
    }

    public Boolean isLoading(){
        return state == QueryStates.Loading;
    }
    public Throwable getError() {
        return error;
    }

    @Override
    public String toString() {
        return "QueryStatus{" +
                "state=" + state +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", error=" + error +
                '}';
    }

    public static class Success<T> extends AuthStatus<T> {
        public Success(){
            super(QueryStates.Success);
        }

        public Success(T data){
            super(QueryStates.Success);
            this.data = data;
        }

        @NonNull
        @Override
        public String toString() {
            return "QueryStatus.Success{" +
                    "state=" + state +
                    ", data=" + data +
                    '}';
        }
    }

    public static class Error<T> extends AuthStatus<T> {
        public Error(String message){
            super(QueryStates.Error);
            this.message = message;
        }

        public Error(Throwable e){
            super(QueryStates.Error);
            this.error = e;
        }

        public Error(String message,T data){
            super(QueryStates.Error);
            this.message = message;
            this.data = data;
        }

        @Override
        public String toString() {
            return "QueryStatus{" +
                    "state=" + state +
                    ", data=" + data +
                    ", message='" + message + '\'' +
                    ", error=" + error +
                    '}';
        }
    }

    public static class Loading<T> extends AuthStatus<T> {
        public Loading(){
            super(QueryStates.Loading);
        }
        @Override
        public String toString() {
            return "QueryStatus{" +
                    "state=" + state +
                    '}';
        }
    }
}
