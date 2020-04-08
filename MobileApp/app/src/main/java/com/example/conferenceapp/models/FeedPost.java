package com.example.conferenceapp.models;


import android.text.format.DateUtils;

import com.github.marlonlom.utilities.timeago.TimeAgo;

public class FeedPost {

    public String name;
    long time;
    public String content;

    public FeedPost() {

    }

    public FeedPost(String name, long time, String content) {
        this.name = name;
        this.time = time;
        this.content = content;

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

}
