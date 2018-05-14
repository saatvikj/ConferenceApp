package com.example.conferenceapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        RecyclerView recyclerView = view.findViewById(R.id.mPartnersView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Partner> partners = new ArrayList<>();

        partners.add(new Partner(1,"Alibaba Group","Champions","https://www.google.com",R.drawable.alibaba));
        partners.add(new Partner(2,"Bloomberg","Champions","https://www.google.com",R.drawable.bloomberg));
        partners.add(new Partner(3,"Facebook","Champions","https://www.google.com",R.drawable.facebook));
        partners.add(new Partner(4,"Google","Champions","https://www.google.com",R.drawable.google));
        partners.add(new Partner(5,"IBM Research","Champions","https://www.google.com",R.drawable.ibm));
        partners.add(new Partner(6,"Microsoft","Champions","https://www.google.com",R.drawable.microsoft));
        partners.add(new Partner(7,"Oath","Champions","https://www.google.com",R.drawable.oath));

        partners.add(new Partner(8,"Disney Research","Contributors","https://www.google.com",R.drawable.disney));
        partners.add(new Partner(9,"NSF","Contributors","https://www.google.com",R.drawable.nsf));
        partners.add(new Partner(10,"Salesforce","Contributors","https://www.google.com",R.drawable.sforce));
        partners.add(new Partner(11,"SAT","Contributors","https://www.google.com",R.drawable.sat));

        partners.add(new Partner(12,"Adobe Systems","Friends of CHI","https://www.google.com",R.drawable.adobe));
        PartnerAdapter partnerAdapter = new PartnerAdapter(partners,view.getContext());
        recyclerView.setAdapter(partnerAdapter);

    }
}