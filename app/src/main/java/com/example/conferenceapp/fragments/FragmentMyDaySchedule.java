package com.example.conferenceapp.fragments;

import android.content.Intent;
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
import com.example.conferenceapp.models.Paper;
import com.example.conferenceapp.utils.PaperCSVParser;
import com.example.conferenceapp.utils.UserCSVParser;

import java.io.Serializable;

public class FragmentMyDaySchedule extends Fragment implements Serializable {

    public static final String ARG_PAGE = "ARG_PAGE";
    public String breakfast[] = {"10:30AM", "BREAKFAST", "10:50AM"};
    public String lunch[] = {"1:00PM", "LUNCH", "2:00PM"};
    private int mPage;

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

            for (int i = 0; i < UserCSVParser.users.get(0).getMyAgenda().size(); i++) {
                View paper_view = inflater.inflate(R.layout.inflator_paper_schedule, null);
                TextView paperstart = paper_view.findViewById(R.id.paperName);
                TextView papervenue = paper_view.findViewById(R.id.paperVenue);
                TextView paperend = paper_view.findViewById(R.id.paperTimings);
                final TextView addPaper = paper_view.findViewById(R.id.add);
                final ImageView paperAdd = paper_view.findViewById(R.id.addPaperIcon);
                addPaper.setVisibility(View.INVISIBLE);
                paperAdd.setVisibility(View.VISIBLE);
                final Paper paper = UserCSVParser.users.get(0).getMyAgenda().get(i);
                paperstart.setText(paper.getTitle());
                papervenue.setText(paper.getVenue());
                paperend.setText(paper.getTime().displayTime());
                root.addView(paper_view);
                paper_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ActivityPaperDetails.class);
                        intent.putExtra("Paper", paper);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
