package com.example.conferenceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentDaySchedule extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public String day1[][] = {{"Quality Assurance","HALL A","10:50 AM -1:00 PM"},{"Free Paper 2(PG)","HALL B","10:30 AM -11:30 AM"},{"Free Paper 1(Member)","HALL B","11:30 AM -12:30 PM"}};
    public String day2[][] = {{"Free Paper 2(PG)","HALL B","10:30 AM -11:30 AM"},{"Free Paper 1(Member)","HALL B","11:30 AM -12:30 PM"},{"Quality Assurance","HALL A","10:50 AM -1:00 PM"}};
    public String breakfast[] = {"10:30AM","BREAKFAST","10:50AM"};
    public String lunch[] = {"1:00PM","LUNCH","2:00PM"};
    private int mPage;

    public static FragmentDaySchedule newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentDaySchedule fragment = new FragmentDaySchedule();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_schedule, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout root = view.findViewById(R.id.daySchedule);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        if(mPage == 1) {
            View bFast = inflater.inflate(R.layout.inflator_break_schedule, null);
            TextView start = bFast.findViewById(R.id.breakStartTime);
            TextView desc = bFast.findViewById(R.id.breakDescTextView);
            TextView end = bFast.findViewById(R.id.breakEndTime);

            start.setText(breakfast[0]);
            desc.setText(breakfast[1]);
            end.setText(breakfast[2]);
            root.addView(bFast);
            bFast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), FoodGuide.class);
                    startActivity(intent);
                }
            });
            for (int i=0; i<day1.length;i++) {
                View paper = inflater.inflate(R.layout.inflator_paper_schedule, null);
                TextView paperstart = paper.findViewById(R.id.paperName);
                TextView papervenue = paper.findViewById(R.id.paperVenue);
                TextView paperend = paper.findViewById(R.id.paperTimings);

                paperstart.setText(day1[i][0]);
                papervenue.setText(day1[i][1]);
                paperend.setText(day1[i][2]);
                root.addView(paper);
            }

            View lunch = inflater.inflate(R.layout.inflator_break_schedule, null);
            TextView start1 = lunch.findViewById(R.id.breakStartTime);
            TextView desc1 = lunch.findViewById(R.id.breakDescTextView);
            TextView end1 = lunch.findViewById(R.id.breakEndTime);

            start1.setText(this.lunch[0]);
            desc1.setText(this.lunch[1]);
            end1.setText(this.lunch[2]);
            root.addView(lunch);
            lunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), FoodGuide.class);
                    startActivity(intent);
                }
            });
            for (int i=0; i<day2.length;i++) {
                View paper = inflater.inflate(R.layout.inflator_paper_schedule, null);
                TextView paperstart = paper.findViewById(R.id.paperName);
                TextView papervenue = paper.findViewById(R.id.paperVenue);
                TextView paperend = paper.findViewById(R.id.paperTimings);

                paperstart.setText(day2[i][0]);
                papervenue.setText(day2[i][1]);
                paperend.setText(day2[i][2]);
                root.addView(paper);
            }
        } else if (mPage == 2) {

            View bFast = inflater.inflate(R.layout.inflator_break_schedule, null);
            TextView start = bFast.findViewById(R.id.breakStartTime);
            TextView desc = bFast.findViewById(R.id.breakDescTextView);
            TextView end = bFast.findViewById(R.id.breakEndTime);

            start.setText(breakfast[0]);
            desc.setText(breakfast[1]);
            end.setText(breakfast[2]);
            root.addView(bFast);
            bFast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), FoodGuide.class);
                    startActivity(intent);
                }
            });
            for (int i=0; i<day2.length;i++) {
                View paper = inflater.inflate(R.layout.inflator_paper_schedule, null);
                TextView paperstart = paper.findViewById(R.id.paperName);
                TextView papervenue = paper.findViewById(R.id.paperVenue);
                TextView paperend = paper.findViewById(R.id.paperTimings);

                paperstart.setText(day2[i][0]);
                papervenue.setText(day2[i][1]);
                paperend.setText(day2[i][2]);
                root.addView(paper);
            }

            View lunch = inflater.inflate(R.layout.inflator_break_schedule, null);
            TextView start1 = lunch.findViewById(R.id.breakStartTime);
            TextView desc1 = lunch.findViewById(R.id.breakDescTextView);
            TextView end1 = lunch.findViewById(R.id.breakEndTime);

            start1.setText(this.lunch[0]);
            desc1.setText(this.lunch[1]);
            end1.setText(this.lunch[2]);
            root.addView(lunch);
            lunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), FoodGuide.class);
                    startActivity(intent);
                }
            });
            for (int i=0; i<day1.length;i++) {
                View paper = inflater.inflate(R.layout.inflator_paper_schedule, null);
                TextView paperstart = paper.findViewById(R.id.paperName);
                TextView papervenue = paper.findViewById(R.id.paperVenue);
                TextView paperend = paper.findViewById(R.id.paperTimings);

                paperstart.setText(day1[i][0]);
                papervenue.setText(day1[i][1]);
                paperend.setText(day1[i][2]);
                root.addView(paper);
            }

        }


    }
}
