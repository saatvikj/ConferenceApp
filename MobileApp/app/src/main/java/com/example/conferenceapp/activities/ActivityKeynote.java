package com.example.conferenceapp.activities;

import com.example.conferenceapp.models.Event;
import com.example.conferenceapp.utils.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        }

        else{
            AboutPage page = new AboutPage(getApplicationContext()).isRTL(false)
                    .setDescription(e.get_abstract())
                    .setImage(R.drawable.michael);
            setContentView(page.create());
        }




    }
}
