package com.example.conferenceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.FeedPost;
import com.example.conferenceapp.models.MainApplication;
import com.example.conferenceapp.models.User;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityCreatePost extends AppCompatActivity {

    EditText contentEditText;
    Button createButton;
    Conference conference = null;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        try {
            conference = ConferenceCSVParser.parseCSV(getApplicationContext());
        } catch (Exception e) {

        }
        getSupportActionBar().setTitle(conference.getConference_name());
        final String conference_id = conference.getConference_id();
        contentEditText = findViewById(R.id.postContentEditText);
        createButton = findViewById(R.id.createPostButton);
        final String email = ((MainApplication) getApplication()).getEmail();
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d: dataSnapshot.child(conference_id).child("Users").getChildren()){
                            User c = d.getValue(User.class);
                            String input_email = c.getEmail();

                            if(email.equals(input_email)){
                                String name = c.getName();
                                String content = contentEditText.getText().toString();
                                long time = System.currentTimeMillis();
                                FeedPost post = new FeedPost(name, time, content, email);
                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child(conference_id).child("Posts").push().setValue(post);
                                Intent intent = new Intent(ActivityCreatePost.this, NavBarActivity.class);
                                startActivity(intent);
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_post,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.create_cancel) {
            Intent intent = new Intent(ActivityCreatePost.this, NavBarActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
