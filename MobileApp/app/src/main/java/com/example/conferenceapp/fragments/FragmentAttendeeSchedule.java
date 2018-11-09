package com.example.conferenceapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.conferenceapp.R;
import com.example.conferenceapp.adapters.AttendeeAdapter;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.User;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.example.conferenceapp.utils.PaperCSVParser;
import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentAttendeeSchedule extends Fragment {

    RecyclerView recyclerView;
    FastScroller fastScroller;
    Conference conference;
    private DatabaseReference mDatabase;
    private List<User> users;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_attendee_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        fastScroller = (FastScroller) view.findViewById(R.id.fastscroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        users = new ArrayList<User>();

        try {
            conference = ConferenceCSVParser.parseCSV(getContext());
        } catch (Exception e) {

        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.child("d52d1b95-ad2d-46de-8145-1844b15792d5").child("Users").getChildren()) {
                    User u = d.getValue(User.class);
                    users.add(u);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        for (int i= 0; i<users.size(); i++) {
            User i_user = users.get(i);
            for (int j=i+1; j<users.size(); j++) {
                User j_user = users.get(j);
                if (i_user.getName().compareTo(j_user.getName())>0) {
                    User temp_user = i_user;
                    users.set(i,j_user);
                    users.set(j,temp_user);
                }
            }
        }

        AttendeeAdapter adapter = new AttendeeAdapter(users, getContext());
        recyclerView.setAdapter(adapter);

        fastScroller.setRecyclerView(recyclerView);


    }
}