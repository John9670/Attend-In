package com.example.firestoretest;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(33.753472876486015, -84.38590214122773);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Class")).showInfoWindow();
        LatLng sydney2 = new LatLng(33.7535, -84.38520214122773);
        mMap.addMarker(new MarkerOptions().position(sydney2).title("Your Are Here")).showInfoWindow();
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(33.753472876486015, -84.38590214122773))
                .radius(50)
                .strokeColor(Color.RED)
                .fillColor(Color.argb(0,225,255,255)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}