package com.example.conferenceapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conferenceapp.R;
import com.example.conferenceapp.adapters.AttendeeAdapter;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.MainApplication;
import com.example.conferenceapp.models.User;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.example.conferenceapp.utils.UserCSVParser;
import com.example.conferenceapp.utils.UserComparator;
import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentAttendeeSchedule extends Fragment {


    //TODO: Change the fast scroller to something better.
    RecyclerView recyclerView;
    FastScroller fastScroller;
    Conference conference;
    private DatabaseReference mDatabase;
    private List<User> users;
    public String src;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_attendee_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.recyclerview);
        fastScroller = view.findViewById(R.id.fastscroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        users = new ArrayList<User>();
        src = ((MainApplication) getActivity().getApplication()).getType();

        try {
            conference = ConferenceCSVParser.parseCSV(getContext());
            users = UserCSVParser.parseCSV(getContext());
        } catch (Exception e) {

        }

        Collections.sort(users,new UserComparator());

        FragmentActivity fa = getActivity();
        AttendeeAdapter adapter = new AttendeeAdapter(users, getContext(), fa, src);
        recyclerView.setAdapter(adapter);
        fastScroller.setBubbleColor(R.color.colorPrimary);
        fastScroller.setHandleColor(R.color.colorPrimaryDark);
        fastScroller.setRecyclerView(recyclerView);
    }
}