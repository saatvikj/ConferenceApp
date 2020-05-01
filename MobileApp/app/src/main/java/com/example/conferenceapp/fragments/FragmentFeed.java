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
import android.widget.LinearLayout;

import com.example.conferenceapp.R;
import com.example.conferenceapp.activities.ActivityCreatePost;
import com.example.conferenceapp.adapters.PostAdapter;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.FeedPost;
import com.example.conferenceapp.models.MainApplication;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.example.conferenceapp.utils.PostComparator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentFeed extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    Conference conference = null;
    DatabaseReference mDatabase;
    List<FeedPost> posts;
    Context ctx;
    LinearLayout no_posts;
    public String email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        email = ((MainApplication) getActivity().getApplication()).getEmail();
        try {
            conference = ConferenceCSVParser.parseCSV(getContext());
        } catch (Exception e) {

        }
        recyclerView = view.findViewById(R.id.mFeedView);
        fab = view.findViewById(R.id.fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        posts = new ArrayList<>();
        ctx = view.getContext();
        no_posts = view.findViewById(R.id.no_posts);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ActivityCreatePost.class);
                startActivity(i);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.child(conference.getConference_id()).child("Posts").getChildren()) {
                    FeedPost fp = d.getValue(FeedPost.class);
                    posts.add(fp);
                }

                if (posts.size() > 0) {
                    no_posts.setVisibility(View.GONE);
                    Collections.sort(posts, new PostComparator());
                    PostAdapter postAdapter = new PostAdapter(posts, ctx, email, conference.getConference_id(), no_posts);
                    recyclerView.setAdapter(postAdapter);
                } else {

                    no_posts.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}