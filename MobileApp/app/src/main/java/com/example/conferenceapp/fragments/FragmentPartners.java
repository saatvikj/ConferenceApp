package com.example.conferenceapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conferenceapp.R;
import com.example.conferenceapp.adapters.PartnerAdapter;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.models.Partner;
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

        RecyclerView recyclerView = view.findViewById(R.id.mPartnersView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Partner> partners = new ArrayList<>();
        for (int i=0; i<conference.getConference_partners().length; i++) {
            partners.add(conference.getConference_partners()[i]);
        }
        PartnerAdapter partnerAdapter = new PartnerAdapter(partners,view.getContext());
        recyclerView.setAdapter(partnerAdapter);

    }
}