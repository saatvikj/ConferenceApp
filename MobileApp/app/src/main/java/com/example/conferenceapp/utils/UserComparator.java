package com.example.conferenceapp.utils;

import com.example.conferenceapp.models.User;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {

    @Override
    public int compare(User user, User t1) {
        return user.getName().compareToIgnoreCase(t1.getName());
    }
}
