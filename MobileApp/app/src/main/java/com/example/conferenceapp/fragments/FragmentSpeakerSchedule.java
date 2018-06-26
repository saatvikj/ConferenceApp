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
import com.example.conferenceapp.adapters.SpeakerAdapter;
import com.example.conferenceapp.utils.PaperCSVParser;
import com.example.conferenceapp.utils.UserCSVParser;
import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentSpeakerSchedule extends Fragment {

    RecyclerView recyclerView;
    FastScroller fastScroller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_speaker_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        fastScroller = (FastScroller) view.findViewById(R.id.fastscroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<String> speakers = new ArrayList<>();
        try {
            PaperCSVParser.parseCSV(view.getContext());
        } catch (Exception e) {
        }
        for (int j = 0; j < PaperCSVParser.papers.size(); j++) {
            for (int k = 0; k < PaperCSVParser.papers.get(j).getAuthors().length; k++) {
                if (!speakers.contains(PaperCSVParser.papers.get(j).getAuthors()[k]))
                    speakers.add(PaperCSVParser.papers.get(j).getAuthors()[k]);
            }
        }

        Collections.sort(speakers);

        SpeakerAdapter adapter = new SpeakerAdapter(speakers, getContext());
        recyclerView.setAdapter(adapter);

        //has to be called AFTER RecyclerView.setAdapter()
        fastScroller.setRecyclerView(recyclerView);


    }
}