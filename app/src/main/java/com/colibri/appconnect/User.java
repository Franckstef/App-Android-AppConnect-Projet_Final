package com.colibri.appconnect;

public class User {

    String userId;
    String name;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    User(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
