package com.example.conferenceapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentGuide extends Fragment {

    private ImageView iconFood;
    private CardView foodGuide;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        iconFood = (ImageView) view.findViewById(R.id.iconFood);
        iconFood.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        iconFood.getLayoutParams().height = (int) Resources.getSystem().getDisplayMetrics().widthPixels / 3;
        iconFood.requestLayout();

        foodGuide = (CardView) view.findViewById(R.id.foodCard);
        foodGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FoodGuide.class);
                startActivity(intent);
            }
        });




    }
}