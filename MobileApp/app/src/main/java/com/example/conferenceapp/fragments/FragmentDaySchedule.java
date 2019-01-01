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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conferenceapp.R;
import com.example.conferenceapp.activities.ActivityKeynote;
import com.example.conferenceapp.activities.ActivityPaperDetails;
import com.example.conferenceapp.activities.ActivitySessionDetails;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.Food;
import com.example.conferenceapp.models.Paper;
import com.example.conferenceapp.models.Session;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.example.conferenceapp.utils.DBManager;
import com.example.conferenceapp.utils.PaperCSVParser;
import com.example.conferenceapp.utils.ProgramCSVParser;
import com.example.conferenceapp.utils.UserCSVParser;

import java.io.Serializable;
import java.util.ArrayList;
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

    public void addFoodView(LinearLayout root, LayoutInflater inflater, Session session) {
        View food_view = inflater.inflate(R.layout.inflator_break_schedule, null);
        ImageView image = food_view.findViewById(R.id.breakImage);
        TextView papervenue = food_view.findViewById(R.id.paperVenue);
        ImageView imagebullet = food_view.findViewById(R.id.bulletImageView);
        TextView start = food_view.findViewById(R.id.breakStartTime);
        TextView end = food_view.findViewById(R.id.breakEndTime);
        papervenue.setText(session.getVenue());
        start.setText(session.getDateTime().getStartTime());
        end.setText(session.getDateTime().getEndTime());

        int bullet_id = getContext().getResources().getIdentifier(session.getBulletDrawable(),"drawable",getContext().getPackageName());
        imagebullet.setImageResource(bullet_id);

        if (session.getTitle().equalsIgnoreCase("Chai/Coffee")) {
            image.setImageResource(R.drawable.chai);
        } else if (session.getTitle().equalsIgnoreCase("Lunch")) {
            image.setImageResource(R.drawable.lunch);
        } else if (session.getTitle().equalsIgnoreCase("Evening Snacks")) {
            image.setImageResource(R.drawable.snacks);
        }
        root.addView(food_view);
    }

    public void addSessionToView(LinearLayout root, LayoutInflater inflater,final Session session, Boolean clickable) {

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
        addPaper.setText("");
        final boolean exists = inMyAgenda(session);
        if (exists == true) {

//            addPaper.setTextColor(Color.parseColor("#C72026"));
            paperAdd.setImageDrawable(getResources().getDrawable(R.drawable.minus_colored));
        }
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

        paperAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check_exists = inMyAgenda(session);
                if (check_exists == false) {
                    dbManager.insert(session);
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.Events.TITLE, session.getTitle())
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, session.getVenue())
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, session.getDateTime().getParseStartTime())
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, session.getDateTime().getParseEndTime());
                    startActivity(intent);
                    paperAdd.setImageDrawable(getResources().getDrawable(R.drawable.minus_colored));
//                    addPaper.setTextColor(Color.parseColor("#C72026"));
//                    addPaper.setText("Remove");
                } else {
                    dbManager.delete(session);
                    paperAdd.setImageDrawable(getResources().getDrawable(R.drawable.plus_plain));
//                    addPaper.setTextColor(Color.parseColor("#0F9D57"));
//                    addPaper.setText("Add");
                }
            }
        });
    }

    public boolean inMyAgenda(Session session) {
        Cursor cursor = dbManager.fetch();
        boolean exists = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String time = cursor.getString(cursor.getColumnIndex("schedule"));
                    if (title.trim().equalsIgnoreCase(session.getTitle().trim()) && time.trim().equalsIgnoreCase(session.getDateTime().toString().trim())) {
                        exists = true;
                    }
                } while (cursor.moveToNext());
            }
        }
        return exists;
    }

    public void addDayView(int day, LinearLayout root, LayoutInflater inflater, Context context) {

        Conference conference = null;
        ArrayList<Session> sessions = new ArrayList<>();
        try {
            conference = ConferenceCSVParser.parseCSV(context);
            sessions = ProgramCSVParser.parseCSV(context);
        } catch (Exception e) {

        }
        String date[] = conference.getConference_start_day().split("-");
        int _date = Integer.parseInt(date[2]) + day;
        String new_date = _date < 10 ? "0" + Integer.toString(_date) : Integer.toString(_date);
        String date_for_page = new_date.concat("/").concat(date[1]).concat("/").concat(date[0]);

        for (int i = 0; i< sessions.size(); i++) {

            Session session = sessions.get(i);

            if (session.getType().equals("Food") && session.getDateTime().getDate().equals(date_for_page)) {
                addFoodView(root, inflater, session);
            } else if (session.getType().equals("Individual") && session.getDateTime().getDate().equals(date_for_page)) {
                addSessionToView(root, inflater, session, session.isClickable());
            } else if (session.getType().equals("List") && session.getDateTime().getDate().equals(date_for_page)) {
                addSessionToView(root, inflater, session, session.isClickable());
            } else if (session.getType().equals("Keynote") && session.getDateTime().getDate().equals(date_for_page)) {
                addSessionToView(root, inflater, session, session.isClickable());
            }
        }
    }
}
