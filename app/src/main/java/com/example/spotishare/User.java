package com.example.spotishare;

public class User {
    private String email;

    public User(){};
    public User(String email){
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email =email;
    }
}
