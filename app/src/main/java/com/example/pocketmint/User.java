package com.example.pocketmint;

public class User {
    private final String username;
    private final String id;
    public User() {
        this.username = "";
        this.id = "";
    }

    public User(String username, String id) {
        this.username = username;
        this.id = id;
    }

    public String getId() {return id;
    }

    public String getUsername() {
        return username;
    }


}
