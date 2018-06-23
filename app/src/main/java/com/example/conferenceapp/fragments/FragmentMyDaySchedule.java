package com.example.conferenceapp.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
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
        super.onViewCreated(view, savedInstanceState);
        dbManager = new DBManager(getContext());
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        LinearLayout root = view.findViewById(R.id.daySchedule);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        if (mPage == 1) {
            addBreakfastView(root, inflater);
            makeTimeView(root, inflater, 0, 12, "2 Dec 18");
            addLunchView(root, inflater);
            makeTimeView(root, inflater, 13, 24, "2 Dec 18");

        } else if (mPage == 2) {
            addBreakfastView(root, inflater);
            makeTimeView(root, inflater, 0, 12, "3 Dec 18");
            addLunchView(root, inflater);
            makeTimeView(root, inflater, 13, 24, "3 Dec 18");
        }
    }

    public void addBreakfastView(LinearLayout root, LayoutInflater inflater) {
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
    }

    public void addLunchView(LinearLayout root, LayoutInflater inflater) {
        View lunch = inflater.inflate(R.layout.inflator_break_schedule, null);
        TextView lunchStart = lunch.findViewById(R.id.breakStartTime);
        TextView lunchDesc = lunch.findViewById(R.id.breakDescTextView);
        TextView lunchEnd = lunch.findViewById(R.id.breakEndTime);

        lunchStart.setText(this.lunch[0]);
        lunchDesc.setText(this.lunch[1]);
        lunchEnd.setText(this.lunch[2]);
        root.addView(lunch);
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityFoodGuide.class);
                startActivity(intent);
            }
        });
    }

    public void addPaperToView(LinearLayout root, LayoutInflater inflater, final Paper paper) {

        final Paper copy = paper;
        View paper_view = inflater.inflate(R.layout.inflator_paper_schedule, null);
        TextView paperstart = paper_view.findViewById(R.id.paperName);
        TextView papervenue = paper_view.findViewById(R.id.paperVenue);
        TextView paperend = paper_view.findViewById(R.id.paperTimings);
        final TextView addPaper = paper_view.findViewById(R.id.add);
        final ImageView paperAdd = paper_view.findViewById(R.id.addPaperIcon);
        paperstart.setText(paper.getTitle());
        papervenue.setText(paper.getVenue());
        paperend.setText(paper.getTime().displayTime());
        root.addView(paper_view);
        paper_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityPaperDetails.class);
                intent.putExtra("Paper", copy);
                startActivity(intent);
            }
        });
        addPaper.setVisibility(View.GONE);
        paperAdd.setVisibility(View.GONE);
    }

    public void makeTimeView(LinearLayout root, LayoutInflater inflater, int startTime, int endTime, String current_date) {

        Cursor cursor = dbManager.fetch();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String authors[] = cursor.getString(cursor.getColumnIndex("authors")).replace("[", "")
                            .replace("]", "")
                            .split(",");
                    String topics[] = cursor.getString(cursor.getColumnIndex("topics")).replace("[", "")
                            .replace("]", "")
                            .split(",");
                    String venue = cursor.getString(cursor.getColumnIndex("venue"));
                    String schedule[] = cursor.getString(cursor.getColumnIndex("schedule")).split(",");
                    String paper_abstract = cursor.getString(cursor.getColumnIndex("abstract"));
                    String day = schedule[0];
                    String date = schedule[1];
                    String confTime[] = schedule[2].split("-");
                    String start_time = confTime[0];
                    String end_time = confTime[1];
                    CustomTime paper_schedule = new CustomTime(date, start_time, end_time, day);
                    Paper paper = new Paper(title, venue, paper_schedule, authors, topics, paper_abstract);
                    if (paper.getTime().getStartTimeHour() >= startTime &&
                            paper.getTime().getStartTimeHour() <= endTime &&
                            date.equals(current_date)) {
                        addPaperToView(root, inflater, paper);
                    }
                } while (cursor.moveToNext());
            }
        }

    }
}