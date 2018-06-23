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
import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;
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
        for (char ch = 'A'; ch<='Z';ch++) {
            speakers.add(Character.toString(ch));
        }

        SpeakerAdapter adapter = new SpeakerAdapter(speakers, getContext());
        recyclerView.setAdapter(adapter);

        //has to be called AFTER RecyclerView.setAdapter()
        fastScroller.setRecyclerView(recyclerView);


    }
}