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
import java.util.Arrays;

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
        start.setText(food.time.split("-")[0]);
        desc.setText(food.type.toUpperCase());
        end.setText(food.time.split("-")[1]);
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
                intent.putExtra("Source",getActivity().getIntent().getStringExtra("Source"));
                if (!(getActivity().getIntent().getStringExtra("Source")).equals("skip")) {
                    intent.putExtra("email", getActivity().getIntent().getStringExtra("email"));
                }
                startActivity(intent);
            }
        });
        addPaper.setVisibility(View.GONE);
        paperAdd.setVisibility(View.GONE);
    }

    public int makeTimeView(LinearLayout root, LayoutInflater inflater, int startTime, int endTime, String current_date) {

        int count = 0;
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
                    String date = schedule[0];
                    String confTime[] = schedule[1].split("-");
                    String start_time = confTime[0];
                    String end_time = confTime[1];
                    CustomTime paper_schedule = new CustomTime(date, start_time, end_time);
                    Paper paper = new Paper(title, venue, paper_schedule, authors, topics, paper_abstract);
                    if (paper.getTime().getStartTimeInt() >= startTime &&
                            paper.getTime().getStartTimeInt() <= endTime &&
                            date.equals(current_date)) {
                        addPaperToView(root, inflater, paper);
                        count++;
                    }
                } while (cursor.moveToNext());
            }
        }
        return count;
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
        String date[] = conference.getConference_start_day().split("-");
        int _date = Integer.parseInt(date[2]) + day;
        String new_date = _date < 10 ? "0" + Integer.toString(_date) : Integer.toString(_date);
        String date_for_page = new_date.concat("/").concat(date[1]).concat("/").concat(date[0]);
        int times[] = new int[conference.getConference_food_guide().length];
        int number_of_breaks = conference.getConference_food_guide().length;
        int j = 0;
        int break_end = 0;
        for (int i = 0; i < conference.getConference_food_guide().length; i++) {
            times[i] = conference.getConference_food_guide()[i].getStartTime();
        }
        Arrays.sort(times);
        boolean all_added = false;
        int papers_before_first_break = makeTimeView(root, inflater, 0000, times[0], date_for_page);
        if (papers_before_first_break == 0) {
            addNotificationForNoPapers(root, inflater);
        }

        for (int i = 0; i < conference.getConference_food_guide().length; i++) {
            if (conference.getConference_food_guide()[i].getStartTime() == times[j]) {
                break_end = conference.getConference_food_guide()[i].getEndTime();
                addFoodView(root, inflater, conference.getConference_food_guide()[i]);
                j++;
            }
        }

        if (j>= number_of_breaks) {
            int papers = makeTimeView(root, inflater, break_end, 2359, date_for_page);
            if (papers == 0) {
                addNotificationForNoPapers(root, inflater);
            }

            all_added = true;
        } else {
            int papers = makeTimeView(root, inflater, break_end, times[j], date_for_page);
            if (papers == 0) {
                addNotificationForNoPapers(root, inflater);
            }
        }

        if (j<number_of_breaks) {
            for (int i = 0; i < conference.getConference_food_guide().length; i++) {
                if (conference.getConference_food_guide()[i].getStartTime() == times[j]) {
                    break_end = conference.getConference_food_guide()[i].getEndTime();
                    addFoodView(root, inflater, conference.getConference_food_guide()[i]);
                    j++;
                }
            }
        }

        if (j>= number_of_breaks && !all_added) {
            int papers = makeTimeView(root, inflater, break_end, 2359, date_for_page);
            if (papers == 0) {
                addNotificationForNoPapers(root, inflater);
            }
        } else {
            int papers = makeTimeView(root, inflater, break_end, times[j], date_for_page);
            if (papers == 0) {
                addNotificationForNoPapers(root, inflater);
            }
        }

        if (j<number_of_breaks) {
            for (int i = 0; i < conference.getConference_food_guide().length; i++) {
                if (conference.getConference_food_guide()[i].getStartTime() == times[j]) {
                    break_end = conference.getConference_food_guide()[i].getEndTime();
                    addFoodView(root, inflater, conference.getConference_food_guide()[i]);
                    j++;
                }
            }
        }
    }
}