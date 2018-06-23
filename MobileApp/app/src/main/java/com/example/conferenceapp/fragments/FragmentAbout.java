package com.example.conferenceapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.utils.ConferenceCSVParser;

import mehdi.sakout.aboutpage.AboutPage;

/**
 * Created by meghna on 8/1/18.
 */

public class FragmentAbout extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Conference conference = null;
        try {
            conference = ConferenceCSVParser.parseCSV(getContext());
        } catch (Exception e) {

        }
        View aboutPage = new AboutPage(getContext()).isRTL(false)
                .setDescription(conference.getConference_about().getDescription())
                .setImage(R.drawable.logo)
                .addWebsite(conference.getConference_about().getWebsite())
                .addEmail(conference.getConference_about().getContact())
                .addFacebook(conference.getConference_about().getFacebook())
                .addTwitter(conference.getConference_about().getTwitter())
                .create();
        return aboutPage;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {



    }
}