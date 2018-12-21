package com.example.conferenceapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.conferenceapp.fragments.FragmentAbout;
import com.example.conferenceapp.fragments.FragmentConferenceSchedule;
import com.example.conferenceapp.fragments.FragmentFeed;
import com.example.conferenceapp.fragments.FragmentGuide;
import com.example.conferenceapp.fragments.FragmentLocationDetails;
import com.example.conferenceapp.fragments.FragmentMessages;
import com.example.conferenceapp.fragments.FragmentMySchedule;
import com.example.conferenceapp.fragments.FragmentPartners;
import com.example.conferenceapp.fragments.FragmentProfile;
import com.example.conferenceapp.fragments.FragmentAttendeeSchedule;
import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.User;
import com.example.conferenceapp.utils.ConferenceCSVParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NavBarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String src;
    Conference conference;
    ImageView userIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        src = getIntent().getStringExtra("Source");
        try {
            conference = ConferenceCSVParser.parseCSV(getApplicationContext());
        } catch (Exception e) {

        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(6).setVisible(false);
        if(src.equals("skip")) {
            userIcon = navigationView.getHeaderView(0).findViewById(R.id.imageView);
            disableOptionsNavigationView(navigationView);
            TextView name = navigationView.getHeaderView(0).findViewById(R.id.nameHeading);
            name.setText("Guest");
            TextView email = navigationView.getHeaderView(0).findViewById(R.id.emailHeading);
            email.setVisibility(View.GONE);

        }
        else {
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setItemIconTintList(null);
            displaySelectedScreen(0);
            userIcon = navigationView.getHeaderView(0).findViewById(R.id.imageView);
            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot d: dataSnapshot.child(conference.getConference_id()).child("Users").getChildren()) {
                        User u = d.getValue(User.class);
                        if (u.getEmail().equals(getIntent().getStringExtra("email"))) {
                            TextView name = navigationView.getHeaderView(0).findViewById(R.id.nameHeading);
                            TextView email = navigationView.getHeaderView(0).findViewById(R.id.emailHeading);
                            String initial = u.getName().substring(0, 1);
                            TextDrawable drawable1 = TextDrawable.builder().buildRound(initial, Color.DKGRAY);
                            userIcon.setImageDrawable(drawable1);
                            name.setText(u.getName());
                            email.setText(u.getEmail());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            userIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NavBarActivity.this, ActivityMyProfile.class);
                    intent.putExtra("email",getIntent().getStringExtra("email"));
                    intent.putExtra("Source", "paid");
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void disableOptionsNavigationView(NavigationView navigationView){
        navigationView.getMenu().getItem(3).setVisible(false);
        navigationView.getMenu().getItem(4).setVisible(false);
        navigationView.getMenu().getItem(9).setTitle("Exit");
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        displaySelectedScreen(R.id.nav_conference_schedule);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        if (src.equals("skip")) {
            displaySelectedScreen(R.id.nav_conference_schedule);
        } else {
            displaySelectedScreen(R.id.nav_feed);
        }
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        android.support.v4.app.Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_conference_schedule:
                fragment = new FragmentConferenceSchedule();
                setActionBarTitle("Program");
                break;
            case R.id.nav_my_schedule:
                fragment = new FragmentMySchedule();
                setActionBarTitle("My Agenda");
                break;
            case R.id.nav_speaker_wise_schedule:
                fragment = new FragmentAttendeeSchedule();
                setActionBarTitle("Attendee");
                break;
            case R.id.nav_feed:
                fragment = new FragmentFeed();
                setActionBarTitle("Feed");
                break;
            case R.id.nav_messages:
                fragment = new FragmentMessages();
                setActionBarTitle("Messages");
                break;
            case R.id.nav_location:
                fragment = new FragmentLocationDetails();
                setActionBarTitle("Location");
                break;
            case R.id.nav_food_guide:
                fragment = new FragmentGuide();
                setActionBarTitle("Guide");
                break;
            case R.id.nav_event_partners:
                fragment = new FragmentPartners();
                setActionBarTitle("Partners");
                break;
            case R.id.nav_about:
                fragment = new FragmentAbout();
                setActionBarTitle("About");
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(NavBarActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    public void setActionBarTitle(String input){
        getSupportActionBar().setTitle(input);
    }
}
