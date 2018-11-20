package com.example.conferenceapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.Profile;
import com.example.conferenceapp.models.User;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityMyProfile extends AppCompatActivity {

    Button saveContinueButton;
    EditText bioEditText;
    EditText researchEditText;
    EditText websiteEditText;
    EditText linkedinEditText;
    EditText twitterEditText;
    private DatabaseReference mDatabase;
    Conference conference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        conference = null;
        try {
            conference = ConferenceCSVParser.parseCSV(getApplicationContext());
        } catch (Exception e) {

        }

        final String email = getIntent().getStringExtra("email");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.child(conference.getConference_id()).child("Users").getChildren()){
                    User c = d.getValue(User.class);
                    if(email.equals(c.getEmail())) {
                        String bio = c.getBio();
                        String research_interest = c.getInterests();
                        bioEditText.setText(bio);
                        researchEditText.setText(research_interest);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        saveContinueButton = findViewById(R.id.saveAndContinueButton);
        bioEditText = findViewById(R.id.bioEditText);
        researchEditText = findViewById(R.id.interestEditText);
        websiteEditText = findViewById(R.id.websiteEditText);
        linkedinEditText = findViewById(R.id.LinkedInEditText);
        twitterEditText = findViewById(R.id.TwitterEditText);
        saveContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bio = bioEditText.getText().toString();
                String research = researchEditText.getText().toString();
                String website = websiteEditText.getText().toString();
                String linkedin = linkedinEditText.getText().toString();
                String twitter = twitterEditText.getText().toString();

                Profile profile = new Profile(email, bio, research, website, twitter, linkedin);

                mDatabase.child(conference.getConference_id()).child("Profiles").push().setValue(profile);

                Intent intent = new Intent(ActivityMyProfile.this, NavBarActivity.class);
                intent.putExtra("Source", "paid");
                intent.putExtra("email",profile.getEmailID());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.guide_home) {
            Intent intent = new Intent(ActivityMyProfile.this, NavBarActivity.class);
            intent.putExtra("Source", getIntent().getStringExtra("Source"));
            intent.putExtra("email",getIntent().getStringExtra("email"));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
