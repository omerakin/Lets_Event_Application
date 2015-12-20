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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

        latitude = 41.207189;
        longitude = 29.076012;

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
                        BitmapDescriptor pPicture;

                        if(((latitude - pLatitude <= 1) || (latitude - pLatitude <= -1)) &&
                                ((longitude - pLongitude <= 1) || (longitude - pLongitude <= -1))){
                            pEvent_Name = p.getString("Event_Name");
                            pEvent_Name = pEvent_Name.substring(0,1).toUpperCase() + pEvent_Name.substring(1);
                            pCategory_Name = p.getString("Category_Name");
                            LatLng EventLatLng = new LatLng(pLatitude,pLongitude);
                            // set the picture of event
                            if (pCategory_Name.equals("Birthday")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.birthday);
                            } else if (pCategory_Name.equals("Concerts")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.concerts);
                            } else if (pCategory_Name.equals("Conferences")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.conference);
                            } else if (pCategory_Name.equals("Comedy Events")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.comedyevents);
                            } else if (pCategory_Name.equals("Education")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.education);
                            } else if (pCategory_Name.equals("Family")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.family);
                            } else if (pCategory_Name.equals("Festivals")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.festivals);
                            } else if (pCategory_Name.equals("Film")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.film);
                            } else if (pCategory_Name.equals("Food - Wine")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.food_wine);
                            } else if (pCategory_Name.equals("Fundraising - Charity")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.fundraising);
                            } else if (pCategory_Name.equals("Art Galleries - Exhibits")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.exhibits);
                            } else if (pCategory_Name.equals("Health - Wellness")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.health);
                            } else if (pCategory_Name.equals("Holiday Events")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.holiday);
                            } else if (pCategory_Name.equals("Kids")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.kids);
                            } else if (pCategory_Name.equals("Literary - Books")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.literary);
                            } else if (pCategory_Name.equals("Museums - Attractions")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.museums);
                            } else if (pCategory_Name.equals("Business - Networking")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.bussiness);
                            } else if (pCategory_Name.equals("Nightlife - Singles")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.nightlife);
                            } else if (pCategory_Name.equals("University - Alumni")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.university);
                            } else if (pCategory_Name.equals("Organizations - Meetups")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.organizations);
                            } else if (pCategory_Name.equals("Outdoors - Recreation")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.outdoors);
                            } else if (pCategory_Name.equals("Performing Arts")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.performing);
                            } else if (pCategory_Name.equals("Pets")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.pets);
                            } else if (pCategory_Name.equals("Politics - Activism")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.politics);
                            } else if (pCategory_Name.equals("Sales - Retail")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.retail);
                            } else if (pCategory_Name.equals("Science")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.science);
                            } else if (pCategory_Name.equals("Religion - Spirituality")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.religion);
                            } else if (pCategory_Name.equals("Sports")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.sports);
                            } else if (pCategory_Name.equals("Technology")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.teknoloji);
                            } else if (pCategory_Name.equals("Tour Dates")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.tours);
                            } else if (pCategory_Name.equals("Tradeshows")) {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.tradeshow);
                            } else {
                                pPicture = BitmapDescriptorFactory.fromResource(R.drawable.other);
                            }
                            //Add to map depends on what kind of event it is.
                            mMap.addMarker(new MarkerOptions().position(EventLatLng).title(pEvent_Name)).setIcon(pPicture);


                        }

                    }
                } else {
                    System.out.println("Error::: in getNearbyEvents()!");

                }
            }
        });

    }

}
