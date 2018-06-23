package com.example.conferenceapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conferenceapp.adapters.DayPagerAdapter;
import com.example.conferenceapp.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class FragmentConferenceSchedule extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_conference_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        DayPagerAdapter adapter = new DayPagerAdapter(getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        final SmartTabLayout tabsStrip = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        tabsStrip.setViewPager(viewPager);
    }
}