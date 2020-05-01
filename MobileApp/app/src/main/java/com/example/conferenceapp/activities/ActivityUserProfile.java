package com.example.conferenceapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

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

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class ActivityUserProfile extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Conference conference;
    LinearLayout root;
    boolean profile_found = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        root = findViewById(R.id.profile_root);
        try {
            conference = ConferenceCSVParser.parseCSV(getApplicationContext());
        } catch (Exception e) {

        }

        final String email_of_user = getIntent().getStringExtra("email");
        profile_found = false;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.child(conference.getConference_id()).child("Profiles").getChildren()) {
                    final Profile p = d.getValue(Profile.class);
                    if (p.getEmailID().equals(email_of_user)) {
                        AboutPage aboutPage = new AboutPage(getApplicationContext())
                                .isRTL(false).setImage(R.drawable.femaleuser);


                        Element email = new Element();
                        email.setTitle("Contact me on mail");
                        email.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_SENDTO);
                                String mailto = "mailto:".concat(email_of_user);
                                intent.setData(Uri.parse(mailto));
                                startActivity(intent);
                            }
                        });

                        aboutPage.addItem(email);

                        if (p.getResearchInterests().length() != 0) {
                            String description = p.getBio();
                            description = description.concat("\n");
                            description = description.concat("Research Interests and fields for collaboration: ");
                            description = description.concat(p.getResearchInterests());

                            aboutPage.setDescription(description);
                        }

                        if (p.getTwitter().length() != 0) {
                            Element twitter = new Element();
                            twitter.setTitle("Find me on Twitter");
                            twitter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(("https://twitter.com/").concat(p.getTwitter())));
                                    startActivity(intent);
                                }
                            });
                            aboutPage.addItem(twitter);
                        }

                        if (p.getWebsite().length() != 0) {
                            Element website = new Element();
                            website.setTitle("On the web");
                            website.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(p.getWebsite()));
                                    startActivity(intent);
                                }
                            });
                            aboutPage.addItem(website);
                        }

                        if (p.getLinkedin().length() != 0) {
                            Element linkedin = new Element();
                            linkedin.setTitle("My LinkdIn");
                            linkedin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(p.getLinkedin()));
                                }
                            });
                        }


                        profile_found = true;

                        View aboutView = aboutPage.create();
                        root.addView(aboutView);
                    }
                }

                if (!profile_found) {
                    for(DataSnapshot d: dataSnapshot.child(conference.getConference_id()).child("Users").getChildren()) {
                        User u = d.getValue(User.class);
                        if (u.getEmail().equals(email_of_user)) {
                            AboutPage about = new AboutPage(getApplicationContext()).isRTL(false);
                            about.setDescription("This user hasn't created a profile yet, you can still contact them on their email ID.");
                            about.addEmail(email_of_user, "Contact now");

                            View aboutView = about.create();
                            root.addView(aboutView);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
            Intent intent = new Intent(ActivityUserProfile.this, NavBarActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}