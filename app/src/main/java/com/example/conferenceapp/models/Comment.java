package com.example.conferenceapp.models;

public class Comment {

    public String name;
    public String timestamp;
    public String text;

    public Comment(String name, String timestamp, String text) {
        this.name = name;
        this.timestamp = timestamp;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

}
