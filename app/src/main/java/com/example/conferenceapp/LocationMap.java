package com.example.conferenceapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationMap extends Activity implements OnMapReadyCallback {

    private final LatLng LOCATION_BURNABY = new LatLng(49.27645, -122.917587);
    private final LatLng LOCATION_SURRREY = new LatLng(49.187500, -122.849000);
    private final LatLng LOCATION_IIITD = new LatLng(28.5459495, 77.2688703);
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                if (arg0.getTitle().equals("Find us here! IIITD")) {
                    // if marker source is clicked
                    Toast.makeText(getApplicationContext(), arg0.getTitle(), Toast.LENGTH_SHORT).show();
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + "IIIT Delhi");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                return true;
            }

        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(LOCATION_IIITD).title("Find us here! IIITD"));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_IIITD, 16);
        map.animateCamera(update);


    }

    //onCreateOptionsMenu: no changes
    //onOptionsItemSelected: no changes
}