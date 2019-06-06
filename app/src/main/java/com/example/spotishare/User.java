package com.example.spotishare;

public class User {
    private String userName;

    public User(String userName){
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
