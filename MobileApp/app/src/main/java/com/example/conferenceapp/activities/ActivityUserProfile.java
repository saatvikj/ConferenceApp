package com.example.conferenceapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.Profile;
import com.example.conferenceapp.models.User;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.example.conferenceapp.utils.UserCSVParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class ActivityUserProfile extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Conference conference;
    LinearLayout root;
    boolean profile_found = false;
    ArrayList<User> users;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        try {
            conference = ConferenceCSVParser.parseCSV(getApplicationContext());
            users = UserCSVParser.parseCSV(getApplicationContext());
        } catch (Exception e) {

        }

        String email_of_user = getIntent().getStringExtra("email");
        ImageView profile_photo = findViewById(R.id.user_image);
        LinearLayout content = findViewById(R.id.user_content);
        ImageView mail = findViewById(R.id.mail);
        for (int i = 0; i< users.size(); i++) {
            if (users.get(i).getEmail().equals(email_of_user)) {
                user = users.get(i);
                setTitle(user.getName());
                String initial = user.getName().split(" ")[0].substring(0, 1).concat(user.getName().split(" ")[1].substring(0, 1));
                profile_photo.setImageDrawable(TextDrawable.builder().buildRound(initial, getApplicationContext().getResources().getColor(R.color.tabtextcolor)));
                AboutPage about = new AboutPage(getApplicationContext()).isRTL(false);
                if (user.getBio().length() != 0) {
                    String experience = "My experience of ICTD..";
                    experience = experience.concat("\n");
                    experience = experience.concat(user.getBio());
                    about.setDescription(experience);
                } else {
                    about.setDescription("The user has not provided any description. You can contact on email below.");
                }
                content.addView(about.create());
                mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        String mailto = "mailto:".concat(user.getEmail());
                        intent.setData(Uri.parse(mailto));
                        startActivity(intent);
                    }
                });
            }
        }
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
            intent.putExtra("Source", getIntent().getStringExtra("Source"));
            intent.putExtra("email", getIntent().getStringExtra("email"));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}