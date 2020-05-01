package com.example.conferenceapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
import com.example.conferenceapp.models.CustomTime;
import com.example.conferenceapp.models.Food;
import com.example.conferenceapp.models.Paper;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.example.conferenceapp.utils.DBManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentMyDaySchedule extends Fragment implements Serializable {

    public static final String ARG_PAGE = "ARG_PAGE";
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
        addDayView(mPage, root, inflater, view.getContext());
    }

    public void addFoodView(LinearLayout root, LayoutInflater inflater, Food food) {
        View food_view = inflater.inflate(R.layout.inflator_break_schedule, null);
        TextView start = food_view.findViewById(R.id.breakStartTime);
        TextView desc = food_view.findViewById(R.id.breakDescTextView);
        TextView end = food_view.findViewById(R.id.breakEndTime);
        start.setText(food.time.getStartTime());
        desc.setText(food.type.toUpperCase());
        end.setText(food.time.getEndTime());
        root.addView(food_view);
        food_view.setOnClickListener(new View.OnClickListener() {
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

    public void addNotificationForNoPapers(LinearLayout root, LayoutInflater inflater) {
        View notification = inflater.inflate(R.layout.inflator_no_paper_notification, null);
        root.addView(notification);
    }

    public void addDayView(int day, LinearLayout root, LayoutInflater inflater, Context context) {

        Conference conference = null;
        try {
            conference = ConferenceCSVParser.parseCSV(context);
        } catch (Exception e) {

        }

        String[] date = conference.getConference_start_day().split("-");
        int _date = Integer.parseInt(date[2]) + day;
        String new_date = _date < 10 ? "0" + _date : Integer.toString(_date);
        String date_for_page = date[0].concat("-").concat(date[1]).concat("-").concat(new_date);

        Food[] guide_for_day = conference.get_guide_for_day(date_for_page);
        Paper[] papers_for_day = get_papers_from_database(date_for_page);

        int[] times = new int[guide_for_day.length + papers_for_day.length];

        Map<Integer, List<Object>> schedule_map = new HashMap<Integer, List<Object>>();
        for (int i = 0; i < guide_for_day.length; i++) {
            times[i] = guide_for_day[i].time.getStartTimeInt();

            if (schedule_map.containsKey(times[i])) {
                schedule_map.get(times[i]).add(guide_for_day[i]);
            } else {
                ArrayList<Object> objectArrayList = new ArrayList<>();
                objectArrayList.add(guide_for_day[i]);
                schedule_map.put(times[i], objectArrayList);
            }
        }

        for (int i = 0; i < papers_for_day.length; i++) {
            times[i+guide_for_day.length] = papers_for_day[i].getTime().getStartTimeInt();
            if (schedule_map.containsKey(times[i+guide_for_day.length])) {
                schedule_map.get(times[i+guide_for_day.length]).add(papers_for_day[i]);
            } else {
                ArrayList<Object> objectArrayList = new ArrayList<>();
                objectArrayList.add(papers_for_day[i]);
                schedule_map.put(times[i+guide_for_day.length], objectArrayList);
            }
        }
        Arrays.sort(times);

        int added_since_last_break = 0;

        for (int i = 0; i < times.length; i++) {
            int time = times[i];
            try {
                if (added_since_last_break == 0) {
//                    addNotificationForNoPapers(root, inflater);
                }

                added_since_last_break = 0;

                Food food = (Food) schedule_map.get(time).get(0);
                schedule_map.get(time).remove(0);
                addFoodView(root, inflater, food);
            } catch (Exception e) {
                added_since_last_break += 1;
                Paper paper = (Paper) schedule_map.get(time).get(0);
                schedule_map.get(time).remove(0);
                addPaperToView(root, inflater, paper);
            }

        }

    }

    public Paper[] get_papers_from_database(String current_date) {

        ArrayList<Paper> papers_for_today = new ArrayList<>();
        Cursor cursor = dbManager.fetch();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String[] authors = cursor.getString(cursor.getColumnIndex("authors")).replace("[", "")
                            .replace("]", "")
                            .split(",");
                    String[] topics = cursor.getString(cursor.getColumnIndex("topics")).replace("[", "")
                            .replace("]", "")
                            .split(",");
                    String venue = cursor.getString(cursor.getColumnIndex("venue"));
                    String[] schedule = cursor.getString(cursor.getColumnIndex("schedule")).split(",");
                    String paper_abstract = cursor.getString(cursor.getColumnIndex("abstract"));
                    String date = schedule[0];
                    String[] confTime = schedule[1].split("-");
                    String start_time = confTime[0];
                    String end_time = confTime[1];
                    CustomTime paper_schedule = new CustomTime(date, start_time, end_time);
                    Paper paper = new Paper(title, venue, paper_schedule, authors, topics, paper_abstract);
                    if (date.equals(current_date)) {
                        papers_for_today.add(paper);
                    }
                } while (cursor.moveToNext());
            }
        }

        return papers_for_today.toArray(new Paper[papers_for_today.size()]);
    }
}