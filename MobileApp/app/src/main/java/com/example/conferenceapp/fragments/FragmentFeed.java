package com.example.conferenceapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.FeedPost;
import com.example.conferenceapp.adapters.PostAdapter;
import com.example.conferenceapp.R;
import com.example.conferenceapp.activities.ActivityCreatePost;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentFeed extends Fragment {

    URI uri = null;
    URL url = null;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    Conference conference = null;
    DatabaseReference mDatabase;
    List<FeedPost> posts;
    Context ctx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        try {
            conference = ConferenceCSVParser.parseCSV(getContext());
        } catch (Exception e) {

        }
        recyclerView = view.findViewById(R.id.mFeedView);
        fab = view.findViewById(R.id.fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        posts = new ArrayList<>();
        ctx = view.getContext();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ActivityCreatePost.class);
                i.putExtra("email",getActivity().getIntent().getStringExtra("email"));
                i.putExtra("Source", "paid");
                startActivity(i);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.child(conference.getConference_id()).child("Posts").getChildren()) {
                    FeedPost fp = d.getValue(FeedPost.class);
                    posts.add(fp);
                }

                PostAdapter postAdapter = new PostAdapter(posts,ctx);
                recyclerView.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}