package com.example.firestoretest;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    Location currentLocation;
    double goalLat;
    double goalLng;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private String id;
    private String crn;
    private String location;
    private GoogleFireStore g;
    private  Maps m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        id = getIntent().getStringExtra("id");
        location = getIntent().getStringExtra("location");
        crn = getIntent().getStringExtra("crn");
        g = new GoogleFireStore();
        m = new Maps();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();


    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude()
                            + " " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(MapsActivity.this);
            }
        });
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        g.getClassLocation(location, new GoogleFireStore.OnGetClassListener() {
            @Override
            public void onComplete(ArrayList<String> success) {

                LatLng latLng= new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                MarkerOptions markerOne = new MarkerOptions().position(latLng).title("I am Here");
                goalLat = Double.parseDouble(success.get(0));
                goalLng = Double.parseDouble(success.get(1));
                MarkerOptions markerTwo = new MarkerOptions().position( new LatLng(Double.parseDouble(success.get(0)),Double.parseDouble(success.get(1)))).title("Goal");
                Circle circle = googleMap.addCircle(new CircleOptions()
                        .center(new LatLng(Double.parseDouble(success.get(0)),Double.parseDouble(success.get(1))))
                        .radius(50)
                        .strokeColor(Color.RED)
                        .fillColor(Color.argb(0,225,255,255)));
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera((CameraUpdateFactory.newLatLngZoom(latLng,19f)));
                googleMap.addMarker(markerOne).showInfoWindow();
                googleMap.addMarker(markerTwo);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                break;
        }
    }
    public void refreshButton(View view){
        //TODO Something
        fetchLastLocation();

    }

    public void checkInButton(View view){
        g.isValidAttendanceList(crn, new GoogleFireStore.OnGetDataListener() {
            @Override
            public void onComplete(boolean success) {
                if(success){
                    if(m.validLocation(goalLat,goalLng,currentLocation.getLatitude(),currentLocation.getLongitude())){
                        g.addAttendanceList(id, crn, new GoogleFireStore.OnGetClassListener() {
                            @Override
                            public void onComplete(ArrayList<String> success) {
                                Intent intent = new Intent(getBaseContext(),CheckedInActivity.class);
                                intent.putExtra("id",id);
                                startActivity(intent);
                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "You Are Too Far Away", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Class Has Not Started", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}