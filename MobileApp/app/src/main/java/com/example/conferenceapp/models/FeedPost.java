package com.example.conferenceapp.models;


public class FeedPost {

    public String name;
    long time;
    public String content;
    public String email;

    public FeedPost() {

    }

    public FeedPost(String name, long time, String content, String email) {
        this.name = name;
        this.time = time;
        this.content = content;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object obj) {
        FeedPost fp = (FeedPost) obj;

        if (fp.name.equals(this.name) && fp.time == this.time && fp.content.equals(this.content) && fp.email.equals(this.email)) {
            return true;
        } else {
            return false;
        }
    }
}
