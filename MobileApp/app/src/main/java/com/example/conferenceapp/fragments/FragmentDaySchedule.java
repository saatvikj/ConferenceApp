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
import com.example.conferenceapp.models.Food;
import com.example.conferenceapp.models.Paper;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.example.conferenceapp.utils.DBManager;
import com.example.conferenceapp.utils.PaperCSVParser;
import com.example.conferenceapp.utils.UserCSVParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentDaySchedule extends Fragment implements Serializable {

    public static final String ARG_PAGE = "ARG_PAGE";
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

        String[] date = conference.getConference_start_day().split("-");
        int _date = Integer.parseInt(date[2]) + day;
        String new_date = _date < 10 ? "0" + _date : Integer.toString(_date);
        String date_for_page = date[0].concat("-").concat(date[1]).concat("-").concat(new_date);

        Food[] guide_for_day = conference.get_guide_for_day(date_for_page);
        Paper[] papers_for_day = PaperCSVParser.get_papers_for_day(date_for_page);

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

        for (int i = 0; i < times.length; i++) {

            int time = times[i];

            try {

                Food food = (Food) schedule_map.get(time).get(0);
                schedule_map.get(time).remove(0);

                addFoodView(root, inflater, food);

            } catch (Exception e) {

                Paper paper = (Paper) schedule_map.get(time).get(0);
                schedule_map.get(time).remove(0);

                addPaperToView(root, inflater, paper);
            }

        }
    }
}
