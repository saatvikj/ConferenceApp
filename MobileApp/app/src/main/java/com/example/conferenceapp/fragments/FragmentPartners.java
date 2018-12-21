package com.example.conferenceapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.Partner;
import com.example.conferenceapp.adapters.PartnerAdapter;
import com.example.conferenceapp.R;
import com.example.conferenceapp.utils.ConferenceCSVParser;

import java.util.ArrayList;
import java.util.List;

public class FragmentPartners extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_event_partners, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Conference conference = null;
        try {
            conference = ConferenceCSVParser.parseCSV(getContext());
        } catch (Exception e) {

        }

    }
}