package com.example.conferenceapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.FeedPost;
import com.example.conferenceapp.adapters.PostAdapter;
import com.example.conferenceapp.R;
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

public class ActivityHashtags extends AppCompatActivity {

    RecyclerView recyclerView;
    Conference conference = null;
    DatabaseReference mDatabase;
    List<FeedPost> posts;
    Context ctx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hashtags);

        final String hashtag = getIntent().getStringExtra("hashtag");
        try {
            conference = ConferenceCSVParser.parseCSV(getApplicationContext());
        } catch (Exception e) {

        }
        recyclerView = findViewById(R.id.mFeedView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        posts = new ArrayList<>();
        ctx = getApplicationContext();

        TextView title = (TextView) findViewById(R.id.hashtag_title);
        String text = title.getText().toString();
        title.setText(text.concat(hashtag));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.child(conference.getConference_id()).child("Posts").getChildren()) {
                    FeedPost fp = d.getValue(FeedPost.class);
                    if (fp.getContent().contains(hashtag)) {
                        posts.add(fp);
                    }

                }
                Collections.sort(posts, new PostComparator());

                PostAdapter postAdapter = new PostAdapter(posts,ctx, getIntent().getStringExtra("email"));
                recyclerView.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ActivityHashtags.this, NavBarActivity.class);
        intent.putExtra("Source", "paid");
        intent.putExtra("email", getIntent().getStringExtra("email"));

        startActivity(intent);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_button, menu);

        return true;
    }
}
