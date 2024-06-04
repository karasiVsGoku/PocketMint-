package com.example.pocketmint;

public class Debt {
    String receiver;
    String giver;

    public Debt(String receiver, String giver) {
        this.receiver = receiver;
        this.giver = giver;
    }

    public Debt() {
        this.receiver = "";
        this.giver = "";
    }

    public String getReceiver() {
        return receiver;
    }

    public String getGiver() {
        return giver;
    }
}
