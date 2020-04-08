package com.example.conferenceapp.utils;

import com.example.conferenceapp.models.FeedPost;

import java.util.Comparator;

public class PostComparator implements Comparator<FeedPost> {

    @Override
    public int compare(FeedPost post, FeedPost t1) {
        if (post.getTime() <= t1.getTime()) {
            return 1;
        } else {
            return  -1;
        }
    }
}