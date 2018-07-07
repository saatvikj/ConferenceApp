package com.example.conferenceapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.conferenceapp.R;
import com.example.conferenceapp.activities.ActivityPaperDetails;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.Paper;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.example.conferenceapp.utils.DBManager;
import com.example.conferenceapp.utils.PaperCSVParser;
import com.example.conferenceapp.utils.UserCSVParser;

import java.io.Serializable;

public class FragmentDaySchedule extends Fragment implements Serializable {

    public static final String ARG_PAGE = "ARG_PAGE";
    public String breakfast[] = {"10:30AM", "BREAKFAST", "10:50AM"};
    public String lunch[] = {"1:00PM", "LUNCH", "2:00PM"};
    private int mPage;
    public DBManager dbManager;

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
        try {
            PaperCSVParser.parseCSV(view.getContext());
            UserCSVParser.parseCSV(view.getContext());
        } catch (Exception e) {
        }

        dbManager = new DBManager(getContext());
        dbManager.open();
        LinearLayout root = view.findViewById(R.id.daySchedule);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        addDayView(mPage, root, inflater, view.getContext());

    }

    public void addBreakfastView(LinearLayout root, LayoutInflater inflater, Context context) {
        View bFast = inflater.inflate(R.layout.inflator_break_schedule, null);
        TextView start = bFast.findViewById(R.id.breakStartTime);
        TextView desc = bFast.findViewById(R.id.breakDescTextView);
        TextView end = bFast.findViewById(R.id.breakEndTime);
        Conference conference = null;
        try {
            conference = ConferenceCSVParser.parseCSV(context);
        } catch (Exception e) {

        }
        start.setText(conference.getConference_food_guide()[0].time.split("-")[0]);
        desc.setText("BREAKFAST");
        end.setText(conference.getConference_food_guide()[0].time.split("-")[1]);
        root.addView(bFast);
        bFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentGuide();
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
    }

    public void addSnacksView(LinearLayout root, LayoutInflater inflater, Context context) {
        View snacks = inflater.inflate(R.layout.inflator_break_schedule, null);
        TextView start = snacks.findViewById(R.id.breakStartTime);
        TextView desc = snacks.findViewById(R.id.breakDescTextView);
        TextView end = snacks.findViewById(R.id.breakEndTime);
        Conference conference = null;
        try {
            conference = ConferenceCSVParser.parseCSV(context);
        } catch (Exception e) {

        }
        start.setText(conference.getConference_food_guide()[2].time.split("-")[0]);
        desc.setText("SNACKS");
        end.setText(conference.getConference_food_guide()[2].time.split("-")[1]);
        root.addView(snacks);
        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentGuide();
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
    }

    public void addLunchView(LinearLayout root, LayoutInflater inflater, Context context) {
        View lunch = inflater.inflate(R.layout.inflator_break_schedule, null);
        TextView lunchStart = lunch.findViewById(R.id.breakStartTime);
        TextView lunchDesc = lunch.findViewById(R.id.breakDescTextView);
        TextView lunchEnd = lunch.findViewById(R.id.breakEndTime);
        Conference conference = null;
        try {
            conference = ConferenceCSVParser.parseCSV(context);
        } catch (Exception e) {

        }
        lunchStart.setText(conference.getConference_food_guide()[1].time.split("-")[0]);
        lunchDesc.setText("LUNCH");
        lunchEnd.setText(conference.getConference_food_guide()[1].time.split("-")[1]);
        root.addView(lunch);
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new FragmentGuide();
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                FragmentTransaction ft = appCompatActivity.getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
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
        final boolean exists = inMyAgenda(copy);
        if (exists == true) {
            addPaper.setText("Remove");
            addPaper.setTextColor(Color.parseColor("#C72026"));
            paperAdd.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
        }
        root.addView(paper_view);
        paper_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityPaperDetails.class);
                intent.putExtra("Paper", copy);
                startActivity(intent);
            }
        });

        paperAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check_exists = inMyAgenda(paper);
                if (check_exists == false) {
                    dbManager.insert(copy);
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.Events.TITLE, paper.getTitle())
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, paper.getVenue())
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, paper.getTime().getParseStartTime())
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, paper.getTime().getParseEndTime());
                    startActivity(intent);
                    paperAdd.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline_black_24dp));
                    addPaper.setTextColor(Color.parseColor("#C72026"));
                    addPaper.setText("Remove");
                } else {
                    dbManager.delete(copy);
                    paperAdd.setImageDrawable(getResources().getDrawable(R.drawable.ic_control_point_green_24dp));
                    addPaper.setTextColor(Color.parseColor("#0F9D57"));
                    addPaper.setText("Add");
                }
            }
        });
    }

    public boolean inMyAgenda(Paper paper) {
        Cursor cursor = dbManager.fetch();
        boolean exists = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    if (title.trim().equalsIgnoreCase(paper.getTitle().trim())) {
                        exists = true;
                    }
                } while (cursor.moveToNext());
            }
        }
        return exists;
    }

    public void addDayView(int day, LinearLayout root, LayoutInflater inflater, Context context) {

        Conference conference = null;
        try {
            conference = ConferenceCSVParser.parseCSV(context);
        } catch (Exception e) {

        }
        String date[] = conference.getConference_start_day().split("-");
        int _date = Integer.parseInt(date[2]) + day;
        String new_date = _date < 10 ? "0" + Integer.toString(_date) : Integer.toString(_date);
        String date_for_page = new_date.concat("/").concat(date[1]).concat("/").concat(date[0]);
        String bFastTime = conference.getConference_food_guide()[0].getTime();
        String bFast_start[] = bFastTime.split("-")[0].split(":");
        String bFast_end[] = bFastTime.split("-")[1].split(":");
        for (int i = 0; i < PaperCSVParser.papers.size(); i++) {
            Paper paper = PaperCSVParser.papers.get(i);
            if (paper.getTime().getStartTimeHour() < Integer.parseInt(bFast_start[0]) && paper.getTime().getDate().equals(date_for_page)) {
                addPaperToView(root, inflater, paper);
            } else if (paper.getTime().getStartTimeHour() == Integer.parseInt(bFast_start[0]) && paper.getTime().getDate().equals(date_for_page) && paper.getTime().getStartTimeMinute() <= Integer.parseInt(bFast_start[1]) ) {
                addPaperToView(root, inflater, paper);
            }
        }
        addBreakfastView(root, inflater, context);
        String lunchTime = conference.getConference_food_guide()[1].getTime();
        String lunch_start[] = lunchTime.split("-")[0].split(":");
        String lunch_end[] = lunchTime.split("-")[1].split(":");
        for (int i = 0; i < PaperCSVParser.papers.size(); i++) {
            Paper paper = PaperCSVParser.papers.get(i);
            if (paper.getTime().getStartTimeHour() < Integer.parseInt(lunch_start[0])
                    && paper.getTime().getDate().equals(date_for_page)
                    && paper.getTime().getStartTimeHour() >= Integer.parseInt(bFast_end[0])) {
                addPaperToView(root, inflater, paper);
            } else if (paper.getTime().getStartTimeHour() == Integer.parseInt(lunch_start[0])
                    && paper.getTime().getDate().equals(date_for_page)
                    && paper.getTime().getStartTimeMinute() <= Integer.parseInt(lunch_start[1])
                    && paper.getTime().getStartTimeHour() >= Integer.parseInt(bFast_end[0])) {
                addPaperToView(root, inflater, paper);
            }
        }
        addLunchView(root, inflater, context);
        for (int i = 0; i < PaperCSVParser.papers.size(); i++) {
            Paper paper = PaperCSVParser.papers.get(i);
            if (paper.getTime().getStartTimeHour() >= Integer.parseInt(lunch_end[0])
                    && paper.getTime().getDate().equals(date_for_page)) {
                addPaperToView(root, inflater, paper);
            }
        }
        addSnacksView(root, inflater, context);
    }
}
