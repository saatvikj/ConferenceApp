package com.example.conferenceapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.Food;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.example.conferenceapp.utils.FoodComparator;

import java.util.Arrays;

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
        Food[] food_guide = conference.getConference_food_guide();

        Arrays.sort(food_guide, new FoodComparator());

        LayoutInflater inflater = getLayoutInflater();
        LinearLayout food_list = view.findViewById(R.id.food_guide);

        for (int i=0; i<food_guide.length; i++) {

            View food_item = inflater.inflate(R.layout.inflator_food_guide, null);

            int day_number = food_guide[i].time.time_difference(conference.getConference_start_day());

            TextView name = food_item.findViewById(R.id.foodLabel);
            name.setText("Day ".concat((Integer.toString(day_number))).concat(" - ").concat(food_guide[i].type));

            TextView time = food_item.findViewById(R.id.foodTime);
            time.setText(food_guide[i].time.getPrettyTime());

            TextView description = food_item.findViewById(R.id.foodDescription);
            description.setText(food_guide[i].description);

            TextView location = food_item.findViewById(R.id.foodLocation);
            location.setText(food_guide[i].location);

            food_list.addView(food_item);
        }

    }
}