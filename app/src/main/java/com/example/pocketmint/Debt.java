package com.example.pocketmint;

import java.util.UUID;

public class Debt {
    private String receiver;
    private String giver;
    private String reason;
    private String date;
    private String id;
    private int amount;

    public Debt(String receiver, String giver, String reason, int amount, String date) {
        this.receiver = receiver;
        this.giver = giver;
        this.reason = reason;
        this.date = date;
        this.amount = amount;
        id = UUID.randomUUID().toString();
    }

    public Debt() {
        this.receiver = "";
        this.giver = "";
        this.reason = "";
        this.date = "";
        this.id = "";
        this.amount = 0;
    }

    public int getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public String getDate() {
        return date;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getGiver() {
        return giver;
    }
}
