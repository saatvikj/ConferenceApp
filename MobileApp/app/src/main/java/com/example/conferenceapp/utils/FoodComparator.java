package com.example.conferenceapp.utils;

import com.example.conferenceapp.models.Food;

import java.util.Comparator;

public class FoodComparator implements Comparator<Food> {

    @Override
    public int compare(Food f1, Food f2) {
        if (f1.getTime().getParseStartTime() >= f2.getTime().getParseStartTime()) {
            return 1;
        } else {
            return  -1;
        }
    }
}