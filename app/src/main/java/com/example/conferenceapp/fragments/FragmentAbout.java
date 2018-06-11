package com.example.conferenceapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conferenceapp.R;

import mehdi.sakout.aboutpage.AboutPage;

/**
 * Created by meghna on 8/1/18.
 */

public class FragmentAbout extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View aboutPage = new AboutPage(getContext()).isRTL(false)
                .setDescription(getString(R.string.description))
                .setImage(R.drawable.logo)
                .addWebsite("https://chi2018.acm.org\n")
                .addEmail("chi@gmail.com")
                .addFacebook("acmchi")
                .addTwitter("sig_chi")
                .create();
        return aboutPage;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {



    }
}