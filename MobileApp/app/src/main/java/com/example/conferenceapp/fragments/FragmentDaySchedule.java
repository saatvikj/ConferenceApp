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
import java.util.Arrays;

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
                intent.putExtra("Source", getActivity().getIntent().getStringExtra("Source"));
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
        int times[] = new int[conference.getConference_food_guide().length];
        int number_of_breaks = conference.getConference_food_guide().length;
        int j = 0;
        int break_end = 0;
        for (int i = 0; i < conference.getConference_food_guide().length; i++) {
            times[i] = conference.getConference_food_guide()[i].getStartTime();
        }
        Arrays.sort(times);
        for (int i = 0; i < PaperCSVParser.papers.size(); i++) {
            Paper paper = PaperCSVParser.papers.get(i);
            if (paper.getTime().getStartTimeInt() <= times[j]
                    && paper.getTime().getDate().equals(date_for_page)) {
                addPaperToView(root, inflater, paper);
            }
        }
        for (int i = 0; i < conference.getConference_food_guide().length; i++) {
            if (conference.getConference_food_guide()[i].getStartTime() == times[0]) {
                break_end = conference.getConference_food_guide()[i].getEndTime();
                addFoodView(root, inflater, conference.getConference_food_guide()[i]);
                j++;
            }
        }

        for (int i = 0; i < PaperCSVParser.papers.size(); i++) {
            Paper paper = PaperCSVParser.papers.get(i);

            if (j >= number_of_breaks) {
                if (paper.getTime().getDate().equals(date_for_page)
                        && paper.getTime().getStartTimeInt() >= break_end) {
                    addPaperToView(root, inflater, paper);
                }
            } else if (paper.getTime().getStartTimeInt() <= times[j]
                    && paper.getTime().getStartTimeInt() >= break_end
                    && paper.getTime().getDate().equals(date_for_page)) {
                addPaperToView(root, inflater, paper);
            }

        }
        if (j < number_of_breaks) {
            for (int i = 0; i < conference.getConference_food_guide().length; i++) {
                if (conference.getConference_food_guide()[i].getStartTime() == times[j]) {
                    break_end = conference.getConference_food_guide()[i].getEndTime();
                    addFoodView(root, inflater, conference.getConference_food_guide()[i]);
                    j++;
                }
            }
        }


        for (int i = 0; i < PaperCSVParser.papers.size(); i++) {
            Paper paper = PaperCSVParser.papers.get(i);
            if (j >= number_of_breaks) {
                if (paper.getTime().getDate().equals(date_for_page)
                        && paper.getTime().getStartTimeInt() >= break_end) {
                    addPaperToView(root, inflater, paper);
                }
            } else if (paper.getTime().getStartTimeInt() <= times[j]
                    && paper.getTime().getStartTimeInt() >= break_end
                    && paper.getTime().getDate().equals(date_for_page)) {
                addPaperToView(root, inflater, paper);
            }

        }
        if (j < number_of_breaks) {
            for (int i = 0; i < conference.getConference_food_guide().length; i++) {
                if (conference.getConference_food_guide()[i].getStartTime() == times[j]) {
                    break_end = conference.getConference_food_guide()[i].getEndTime();
                    addFoodView(root, inflater, conference.getConference_food_guide()[i]);
                    j++;
                }
            }
        }


        for (int i = 0; i < PaperCSVParser.papers.size(); i++) {
            Paper paper = PaperCSVParser.papers.get(i);

            if (j >= number_of_breaks) {
                if (paper.getTime().getDate().equals(date_for_page)
                        && paper.getTime().getStartTimeInt() >= break_end) {
                    addPaperToView(root, inflater, paper);
                }
            } else if (paper.getTime().getStartTimeInt() >= break_end
                    && paper.getTime().getDate().equals(date_for_page)) {
                addPaperToView(root, inflater, paper);
            }

        }
    }
}
