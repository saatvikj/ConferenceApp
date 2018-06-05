package com.example.conferenceapp.models;

import android.net.Uri;

public class FeedPost {

    public String name;
    public String time;
    public String content;
    public Uri media;
    public Integer likes;
    public String[] comments;

    public FeedPost(String name, String time, String content, Uri media, Integer likes, String[] comments) {
        this.name = name;
        this.time = time;
        this.content = content;

        this.media = media;
        this.likes = likes;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public Uri getMedia() {
        return media;
    }

    public Integer getLikes() {
        return likes;
    }

    public String[] getComments() {
        return comments;
    }




}
