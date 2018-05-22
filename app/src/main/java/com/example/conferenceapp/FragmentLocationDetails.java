package com.example.conferenceapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.otaliastudios.zoom.ZoomImageView;

public class FragmentLocationDetails extends Fragment{

    private final LatLng LOCATION = new LatLng(45.50386, -73.56096889999998);
    private GoogleMap map;
    private Marker myMarker;
    private ImageView mapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        return inflater.inflate(R.layout.fragment_location_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        SupportMapFragment mSupportMapFragment;

        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapwhere);
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.mapwhere, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if (googleMap != null) {

                        googleMap.getUiSettings().setAllGesturesEnabled(true);

                        CameraPosition cameraPosition = new CameraPosition.Builder().target(LOCATION).zoom(15.0f).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        googleMap.addMarker(new MarkerOptions().position(LOCATION).title("Find us here!"));
                        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        googleMap.moveCamera(cameraUpdate);

                        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                if (marker.getTitle().equals("Find us here!")) {
                                    // if marker source is clicked
                                    Toast.makeText(getActivity(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + "Palais des congrès de Montréal");
                                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                    mapIntent.setPackage("com.google.android.apps.maps");
                                    startActivity(mapIntent);
                                }
                                return true;
                            }
                        });

                    }

                }
            });

            ZoomImageView mapView = (ZoomImageView) view.findViewById(R.id.mapImageView);
            mapView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.map));
        }
    }
}