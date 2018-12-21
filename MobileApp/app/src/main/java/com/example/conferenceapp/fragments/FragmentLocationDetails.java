package com.example.conferenceapp.fragments;

import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.utils.ConferenceCSVParser;

public class FragmentLocationDetails extends Fragment{

    Conference conference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        return inflater.inflate(R.layout.fragment_location_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Toast.makeText(getContext(), "Tap on image to zoom", Toast.LENGTH_SHORT).show();

        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) view.findViewById(R.id.location_map);
        imageView.setImage(ImageSource.resource(R.drawable.map));

        conference = null;
        try {
            conference = ConferenceCSVParser.parseCSV(getContext());
        } catch (Exception e) {

        }
    }
}