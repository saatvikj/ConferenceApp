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
        AboutPage page = new AboutPage(getContext()).isRTL(false)
                .setDescription(conference.getConference_about().getDescription())
                .setImage(R.drawable.logo);
        if (conference.getConference_about().getTwitter() != null) {
            page.addTwitter(conference.getConference_about().getTwitter());
        }

        if (conference.getConference_about().getFacebook() != null) {
            page.addFacebook(conference.getConference_about().getFacebook());
        }

        if (conference.getConference_about().getWebsite() != null) {
            page.addWebsite(conference.getConference_about().getWebsite());
        }

        if (conference.getConference_about().getContact() != null) {
            page.addEmail(conference.getConference_about().getContact());
        }

        return page.create();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


    }
}