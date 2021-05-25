package com.example.try2.objects;

public class Chat {
    private String message,date,sender;

    public Chat() {
    }

    public Chat(String message, String date, String sender) {
        this.message = message;
        this.date = date;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
