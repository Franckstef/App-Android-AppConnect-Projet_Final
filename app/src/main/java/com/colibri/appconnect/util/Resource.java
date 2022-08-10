package com.colibri.appconnect.util;

public class Resource<T> {
    private T data = null;
    private String message = null;

    protected Resource(){}
    protected Resource(T data){
        this.data = data;
    }
    protected Resource(T data,String message){
        this.data = data;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }


    public static class Success<T> extends Resource<T>{
        public Success(){
            super();
        }

        public Success(T data){
            super(data);
        }
    }

    public static class Error<T> extends Resource<T>{
        public Error(String message){
            super(null, message);
        }

        public Error(String message,T data){
            super(data, message);
        }
    }

    public static class Loading<T> extends Resource<T>{
        public Loading(){
            super(null);
        }
    }
}
