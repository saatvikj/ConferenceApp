package com.example.conferenceapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.conferenceapp.fragments.FragmentDaySchedule;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.utils.ConferenceCSVParser;

public class DayPagerAdapter extends FragmentPagerAdapter {

    Context context;

    public DayPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        Conference conference = null;
        try {
            conference = ConferenceCSVParser.parseCSV(context);
        } catch (Exception e) {

        }
        int start_date = Integer.parseInt(conference.getConference_start_day().split("-")[2]);
        int end_date = Integer.parseInt(conference.getConference_end_day().split("-")[2]);
        return end_date - start_date + 1;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentDaySchedule.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return "Day " + Integer.toString(position+1);
    }

}
