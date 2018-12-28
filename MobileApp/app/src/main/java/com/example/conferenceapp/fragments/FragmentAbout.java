package com.example.conferenceapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.conferenceapp.R;
import com.example.conferenceapp.models.Conference;
import com.example.conferenceapp.utils.ConferenceCSVParser;

import mehdi.sakout.aboutpage.AboutPage;

/**
 * Created by meghna on 8/1/18.
 */

public class FragmentAbout extends Fragment {

    Conference conference;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        conference = null;
        try {
            conference = ConferenceCSVParser.parseCSV(getContext());
        } catch (Exception e) {

        }
        AboutPage page = new AboutPage(getContext()).isRTL(false)
                .setDescription(conference.getConference_about().getDescription())
                .setImage(R.drawable.logo);

        final LinearLayout content = view.findViewById(R.id.about_content);
        content.addView(page.create());

        ImageView twitter = view.findViewById(R.id.twitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(("https://twitter.com/").concat(conference.getConference_about().getTwitter())));
                startActivity(intent);
            }
        });

        ImageView fb = view.findViewById(R.id.fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(("https://facebook.com/").concat(conference.getConference_about().getTwitter())));
                startActivity(intent);
            }
        });

        ImageView mail = view.findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String mailto = "mailto:".concat(conference.getConference_about().getContact());
                intent.setData(Uri.parse(mailto));
                startActivity(intent);
            }
        });

        ImageView website = view.findViewById(R.id.website);
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(conference.getConference_about().getWebsite()));
                startActivity(intent);
            }
        });


    }
}