package com.example.conferenceapp.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.conferenceapp.R;
import com.example.conferenceapp.activities.ActivityFoodGuide;
import com.example.conferenceapp.activities.ActivityPaperDetails;
import com.example.conferenceapp.activities.NavBarActivity;
import com.example.conferenceapp.models.CustomTime;
import com.example.conferenceapp.models.Paper;
import com.example.conferenceapp.utils.DBManager;
import com.example.conferenceapp.utils.PaperCSVParser;
import com.example.conferenceapp.utils.UserCSVParser;

import java.io.Serializable;

public class FragmentMyDaySchedule extends Fragment implements Serializable {

    public static final String ARG_PAGE = "ARG_PAGE";
    public String breakfast[] = {"10:30AM", "BREAKFAST", "10:50AM"};
    public String lunch[] = {"1:00PM", "LUNCH", "2:00PM"};
    private int mPage;
    private DBManager dbManager;

    public static FragmentMyDaySchedule newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentMyDaySchedule fragment = new FragmentMyDaySchedule();
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
        dbManager = new DBManager(getContext());
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        LinearLayout root = view.findViewById(R.id.daySchedule);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        if (mPage == 1) {
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
                    Intent intent = new Intent(getActivity(), ActivityFoodGuide.class);
                    startActivity(intent);
                }
            });
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        final String title = cursor.getString(cursor.getColumnIndex("title"));
                        final String authors = cursor.getString(cursor.getColumnIndex("authors"));
                        final String topics = cursor.getString(cursor.getColumnIndex("topics"));
                        final String venue = cursor.getString(cursor.getColumnIndex("venue"));
                        final String time = cursor.getString(cursor.getColumnIndex("schedule"));
                        final String paper_abstract = cursor.getString(cursor.getColumnIndex("abstract"));

                        View paper_view = inflater.inflate(R.layout.inflator_paper_schedule, null);
                        TextView paper_title = paper_view.findViewById(R.id.paperName);
                        TextView paper_venue = paper_view.findViewById(R.id.paperVenue);
                        final TextView paper_time = paper_view.findViewById(R.id.paperTimings);
                        final TextView addPaper = paper_view.findViewById(R.id.add);
                        final ImageView paperAdd = paper_view.findViewById(R.id.addPaperIcon);
                        addPaper.setVisibility(View.INVISIBLE);
                        paperAdd.setVisibility(View.INVISIBLE);
                        paper_title.setText(title);
                        paper_venue.setText(venue);
                        paper_time.setText(time);
                        root.addView(paper_view);
                        paper_view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String custom_time[] = time.split(",");
                                String day = custom_time[0];
                                String date = custom_time[1];
                                String confTime[] = custom_time[2].split("-");
                                String startTime = confTime[0];
                                String endTime = confTime[1];
                                CustomTime paper_schedule = new CustomTime(date, startTime, endTime, day);
                                Paper paper = new Paper(title,venue,paper_schedule,authors.replace("[","").replace("]","").split(","),
                                        topics.replace("[","").replace("]","").split(","),paper_abstract);
                                Intent intent = new Intent(getActivity(), ActivityPaperDetails.class);
                                intent.putExtra("Paper", paper);
                                startActivity(intent);
                            }
                        });


                    } while (cursor.moveToNext());
                }
            }
        }
    }
}
