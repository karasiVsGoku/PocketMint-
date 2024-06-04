package com.example.pocketmint;

public class User {
    private final String username;
    public User() { this.username = ""; }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


}
