package com.example.conferenceapp.activities;

import com.example.conferenceapp.models.Event;
import com.example.conferenceapp.utils.*;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.conferenceapp.R;

import java.io.IOException;
import java.util.ArrayList;

import mehdi.sakout.aboutpage.AboutPage;

public class ActivityKeynote extends AppCompatActivity {

    ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String id = getIntent().getStringExtra("id");
        try {
            events = EventCSVParser.parseCSV(getApplicationContext(), Integer.parseInt(id));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Event e = events.get(0);

        if(e.getAuthors()[0].equals("Aruna Roy")){
            AboutPage page = new AboutPage(getApplicationContext()).isRTL(false)
                    .setDescription(e.get_abstract())
                    .setImage(R.drawable.aruna);
            setContentView(page.create());
        }

        else if(e.getAuthors()[0].equals("Rahul Alex Panicker")){
            AboutPage page = new AboutPage(getApplicationContext()).isRTL(false)
                    .setDescription(e.get_abstract())
                    .setImage(R.drawable.rahul);
            setContentView(page.create());
        }

        else if(e.getAuthors()[0].equals("Smitha Radhakrishnan")){
            AboutPage page = new AboutPage(getApplicationContext()).isRTL(false)
                    .setDescription(e.get_abstract())
                    .setImage(R.drawable.smitha);
            setContentView(page.create());
        }

        else if(e.getAuthors()[0].equals("Murali Sanmugavelan")){
            AboutPage page = new AboutPage(getApplicationContext()).isRTL(false)
                    .setDescription(e.get_abstract())
                    .setImage(R.drawable.murali);
            setContentView(page.create());
        } else if (e.getAuthors()[0].equals("") || e.getAuthors()[0].length() == 0) {
            AboutPage page = new AboutPage(getApplicationContext()).isRTL(false)
                    .setDescription(e.get_abstract())
                    .setImage(R.drawable.logo);
            setContentView(page.create());

        }

        else {
            AboutPage page = new AboutPage(getApplicationContext()).isRTL(false)
                    .setDescription(e.get_abstract())
                    .setImage(R.drawable.michael);
            setContentView(page.create());
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
            Intent intent = new Intent(ActivityKeynote.this, NavBarActivity.class);
            intent.putExtra("Source", getIntent().getStringExtra("Source"));
            if (!(getIntent().getStringExtra("Source")).equals("skip")) {
                intent.putExtra("email", getIntent().getStringExtra("email"));
            }
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
