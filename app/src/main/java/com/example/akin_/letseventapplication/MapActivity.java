package com.example.akin_.letseventapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.onResume();
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        //Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            /*
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION);
            */
            return;
        }

        // or,

        /*
        try {
            //get current location
            myLocation = locationManager.getLastKnownLocation(provider);
        } catch (SecurityException e) {
            Log.e("PERMISSION_EXCEPTION", "PERMISSION_NOT_GRANTED");
        }
        */

        //get current location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        //get latitude and longitude
        //latitude = myLocation.getLatitude();
        //longitude = myLocation.getLongitude();

        latitude = 41.195160;
        longitude = 29.050421;

        // Add a marker in myCoordinates and move the camera
        LatLng myCoordinates = new LatLng(latitude, longitude);
        //mMap.addMarker(new MarkerOptions().position(myCoordinates).title("I am here!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myCoordinates));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        getNearbyEvents();
    }

    private void getNearbyEvents() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestEvent");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseEvent, ParseException e) {
                if (e == null) {
                    int len = parseEvent.size();
                    for (int i = 0; i < len; i++) {
                        //get the i th element of event in order to obtain details
                        ParseObject p = parseEvent.get(i);

                        //get the detail information of userAccount
                        double pLatitude = p.getDouble("Latitude");
                        double pLongitude = p.getDouble("Longitude");
                        String pEvent_Name;
                        String pCategory_Name;

                        if(((latitude - pLatitude <= 1) || (latitude - pLatitude <= -1)) &&
                                ((longitude - pLongitude <= 1) || (longitude - pLongitude <= -1))){
                            pEvent_Name = p.getString("Event_Name");
                            pCategory_Name = p.getString("Category_Name");
                            LatLng EventLatLng = new LatLng(pLatitude,pLongitude);
                            mMap.addMarker(new MarkerOptions().position(EventLatLng).title(pEvent_Name + "\n" + pCategory_Name));

                        } else if ((pLatitude == 555.0 && pLongitude  == 555.0) ||
                                            (pLatitude == 0.0 && pLongitude  == 0.0)){
                            // do not display
                        }

                    }
                } else {
                    System.out.println("Error::: in getNearbyEvents()!");

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
    }
}
