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
import com.example.conferenceapp.activities.ActivityKeynote;
import com.example.conferenceapp.activities.ActivityPaperDetails;
import com.example.conferenceapp.activities.ActivitySessionDetails;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.CustomTime;
import com.example.conferenceapp.models.Food;
import com.example.conferenceapp.models.Paper;
import com.example.conferenceapp.models.Session;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.example.conferenceapp.utils.DBManager;
import com.example.conferenceapp.utils.ProgramCSVParser;

import java.io.Serializable;
import java.util.ArrayList;
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

    public void addFoodView(LinearLayout root, LayoutInflater inflater, Session session) {
        View food_view = inflater.inflate(R.layout.inflator_break_schedule, null);
        ImageView image = food_view.findViewById(R.id.breakImage);
        TextView start = food_view.findViewById(R.id.breakStartTime);
        TextView end = food_view.findViewById(R.id.breakEndTime);
        start.setText(session.getDateTime().getStartTime());
        end.setText(session.getDateTime().getEndTime());

        if (session.getTitle().equalsIgnoreCase("Chai/Coffee")) {
            image.setImageResource(R.drawable.coffee);
        } else if (session.getTitle().equalsIgnoreCase("Lunch")) {
            image.setImageResource(R.drawable.lunch);
        }
        root.addView(food_view);
    }

    public void addSessionToView(LinearLayout root, LayoutInflater inflater, final Session session, Boolean clickable) {

        View paper_view = inflater.inflate(R.layout.inflator_paper_schedule, null);
        TextView paperstart = paper_view.findViewById(R.id.paperName);
        TextView papervenue = paper_view.findViewById(R.id.paperVenue);
        TextView paperend = paper_view.findViewById(R.id.paperTimings);
        ImageView imagevenue = paper_view.findViewById(R.id.sessionImageView);
        ImageView imagebullet = paper_view.findViewById(R.id.bulletImageView);
        final TextView addPaper = paper_view.findViewById(R.id.add);
        final ImageView paperAdd = paper_view.findViewById(R.id.addPaperIcon);
        paperstart.setText(session.getTitle());
        paperend.setText(session.getDateTime().displayTime());
        papervenue.setText(session.getVenue());

        int bullet_id = getContext().getResources().getIdentifier(session.getBulletDrawable(),"drawable",getContext().getPackageName());
        imagebullet.setImageResource(bullet_id);

        int icon_id = getContext().getResources().getIdentifier(session.getIconDrawable(),"drawable",getContext().getPackageName());
        imagevenue.setImageResource(icon_id);
        root.addView(paper_view);

        if (clickable == true) {
            paper_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = null;
                    if (session.getType().equals("Keynote")) {
                        intent = new Intent(getActivity(), ActivityKeynote.class);
                    } else {
                        intent = new Intent(getActivity(), ActivitySessionDetails.class);
                    }
                    intent.putExtra("id", Integer.toString(session.getID()));
                    intent.putExtra("Source", getActivity().getIntent().getStringExtra("Source"));
                    if (!(getActivity().getIntent().getStringExtra("Source").equals("skip"))) {
                        intent.putExtra("email",getActivity().getIntent().getStringExtra("email"));
                    }
                    startActivity(intent);
                }
            });
        }

        addPaper.setVisibility(View.GONE);
        paperAdd.setVisibility(View.GONE);
    }

    public int makeTimeView(LinearLayout root, LayoutInflater inflater, int startTime, int endTime, String current_date, Boolean add) {

        int count = 0;
        Cursor cursor = dbManager.fetch();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String schedule[] = cursor.getString(cursor.getColumnIndex("schedule")).split(",");
                    String date = schedule[0];
                    String confTime[] = schedule[1].split("-");
                    String start_time = confTime[0];
                    String end_time = confTime[1];
                    String type = cursor.getString(cursor.getColumnIndex("type"));
                    String icon = cursor.getString(cursor.getColumnIndex("icon"));
                    String bullet = cursor.getString(cursor.getColumnIndex("bullet"));
                    String venue = cursor.getString(cursor.getColumnIndex("venue"));
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    boolean clickable = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("clickable")));
                    CustomTime paper_schedule = new CustomTime(date, start_time, end_time);

                    Session session = new Session(id,title,paper_schedule,type,clickable,icon,bullet,venue);

                    if (session.getDateTime().getStartTimeInt() >= startTime &&
                            session.getDateTime().getStartTimeInt() <= endTime &&
                            date.equals(current_date)) {
                        if (add) {
                            addSessionToView(root, inflater, session, clickable);
                        }
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
        ArrayList<Session> sessions = null;
        try {
            conference = ConferenceCSVParser.parseCSV(context);
            sessions = ProgramCSVParser.parseCSV(context);
        } catch (Exception e) {

        }
        String date[] = conference.getConference_start_day().split("-");
        int _date = Integer.parseInt(date[2]) + day;
        String new_date = _date < 10 ? "0" + Integer.toString(_date) : Integer.toString(_date);
        String date_for_page = new_date.concat("/").concat(date[1]).concat("/").concat(date[0]);
        int last_time = 0000;
        boolean new_addition = true;

        int daySessions = makeTimeView(root, inflater, 0000, 2359, date_for_page, false);
        if (daySessions == 0) {
            addNotificationForNoPapers(root, inflater);
        }
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getType().equals("Food") && sessions.get(i).getDateTime().getDate().equals(date_for_page)) {
                makeTimeView(root, inflater, last_time, sessions.get(i).getDateTime().getEndTimeInt(), date_for_page, true);
                addFoodView(root, inflater, sessions.get(i));
                last_time = sessions.get(i).getDateTime().getEndTimeInt();
            }
        }
    }
}