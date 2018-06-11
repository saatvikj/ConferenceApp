package com.example.conferenceapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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

import com.example.conferenceapp.fragments.FragmentAbout;
import com.example.conferenceapp.fragments.FragmentConferenceSchedule;
import com.example.conferenceapp.fragments.FragmentFeed;
import com.example.conferenceapp.fragments.FragmentGuide;
import com.example.conferenceapp.fragments.FragmentLocationDetails;
import com.example.conferenceapp.fragments.FragmentMessages;
import com.example.conferenceapp.fragments.FragmentMySchedule;
import com.example.conferenceapp.fragments.FragmentPartners;
import com.example.conferenceapp.fragments.FragmentProfile;
import com.example.conferenceapp.fragments.FragmentSpeakerSchedule;
import com.example.conferenceapp.R;

public class NavBarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String src;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        src = getIntent().getStringExtra("Source");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        if(src.equals("skip")) {
            disableOptionsNavigationView(navigationView);
            TextView name = navigationView.getHeaderView(0).findViewById(R.id.nameHeading);
            name.setText("Guest");
            TextView email = navigationView.getHeaderView(0).findViewById(R.id.emailHeading);
            email.setVisibility(View.GONE);

        }
        else {
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setItemIconTintList(null);
            displaySelectedScreen(R.id.nav_feed);
            ImageView userIcon = navigationView.getHeaderView(0).findViewById(R.id.imageView);
            userIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    android.support.v4.app.Fragment fragment = null;
                    fragment = new FragmentProfile();
                    if (fragment != null) {
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }

                    setActionBarTitle("My Profile");
                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);

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
                fragment = new FragmentSpeakerSchedule();
                setActionBarTitle("Speakers");
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
                Intent intent = new Intent(NavBarActivity.this, ActivityLogin.class);
                startActivity(intent);
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
