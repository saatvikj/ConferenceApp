package com.example.conferenceapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.Food;
import com.example.conferenceapp.utils.ConferenceCSVParser;

public class FragmentGuide extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Conference conference = null;
        try {
            conference = ConferenceCSVParser.parseCSV(view.getContext());
        } catch (Exception e) {

        }
//        Food[] food_guide = conference.getConference_food_guide();
//        for(int i = 0; i < food_guide.length; i++){
//            if(food_guide[i] != null) {
//                if (food_guide[i].type.equalsIgnoreCase("breakfast")) {
//                    CardView breakfastCard = view.findViewById(R.id.breakfastCard);
//                    breakfastCard.setVisibility(View.VISIBLE);
//                    TextView breakfastLabel = view.findViewById(R.id.breakfastLabel);
//                    TextView breakfastTime = view.findViewById(R.id.breakfastTime);
//                    TextView breakfastDesc = view.findViewById(R.id.breakfastDescription);
//                    TextView breakfastLoc = view.findViewById(R.id.breakfastLocation);
//                    breakfastLabel.setText(food_guide[i].type.toUpperCase());
//                    breakfastTime.setText(food_guide[i].time);
//                    breakfastLoc.setText(food_guide[i].location);
//                    breakfastDesc.setText(food_guide[i].description);
//
//                } else if (food_guide[i].type.equalsIgnoreCase("lunch")) {
//                    CardView lunchCard = view.findViewById(R.id.lunchCard);
//                    lunchCard.setVisibility(View.VISIBLE);
//                    TextView lunchLabel = view.findViewById(R.id.lunchLabel);
//                    TextView lunchTime = view.findViewById(R.id.lunchTime);
//                    TextView lunchDesc = view.findViewById(R.id.lunchDescription);
//                    TextView lunchLoc = view.findViewById(R.id.lunchLocation);
//                    lunchLabel.setText(food_guide[i].type.toUpperCase());
//                    lunchTime.setText(food_guide[i].time);
//                    lunchLoc.setText(food_guide[i].location);
//                    lunchDesc.setText(food_guide[i].description);
//
//                } else {
//                    CardView snacksCard = view.findViewById(R.id.snacksCard);
//                    snacksCard.setVisibility(View.VISIBLE);
//                    TextView snacksLabel = view.findViewById(R.id.snacksLabel);
//                    TextView snacksTime = view.findViewById(R.id.snacksTime);
//                    TextView snacksDesc = view.findViewById(R.id.snacksDescription);
//                    TextView snacksLoc = view.findViewById(R.id.snacksLocation);
//                    snacksLabel.setText(food_guide[i].type.toUpperCase());
//                    snacksTime.setText(food_guide[i].time);
//                    snacksLoc.setText(food_guide[i].location);
//                    snacksDesc.setText(food_guide[i].description);
//
//                }
//            }
//        }



    }
}
