package fr.cocoteam.co2co2.view;


import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import fr.cocoteam.co2co2.model.directionModel.Route;
import fr.cocoteam.co2co2.service.DirectionsWebService;
import fr.cocoteam.co2co2.listener.DirectionsServiceListener;
import fr.cocoteam.co2co2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
    private SupportMapFragment mapFragment;
   private  List<Route> direction;
   private String origin ="45.75,4.7";
   private String destination = "45.73,4.9";
   private GoogleMap mMap;

    private FusedLocationProviderClient fusedLocationClient;
    //True si user a accepté de partager se position
    private boolean requestingLocationUpdates=false;
    private LocationCallback locationCallback;
    private LatLng lastKnownLocation;
    private String otherLastKnownLocation;
    private Marker markerMyLocation;
  private    DatabaseReference myRefLocation;
    private DatabaseReference refOtherUserLocation;
   private  Marker markerOtherLocation;
    private DatabaseReference myRefState;
    private DatabaseReference refOtherUserState;
private     Switch startSwitch;

    public MapFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        fetchDirections( origin, destination);
        final LatLng latLngOrigin = stringToLatLng(origin);
         startSwitch = view.findViewById(R.id.startCovoiturage);
        if (savedInstanceState != null) {
                requestingLocationUpdates = savedInstanceState.getBoolean("requestingLocationUpdates")   ;
                startSwitch.setChecked(savedInstanceState.getBoolean("startSwitch"));

        }
        //handle start and sotp covoiturage
        startAndStop(view);
        //write in firebase database
        getFBref();

            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    mMap =googleMap;
                    mMap.addMarker(new MarkerOptions().position(stringToLatLng(origin)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    mMap.addMarker(new MarkerOptions().position(stringToLatLng(destination)));
                    googleMap.setMyLocationEnabled(true);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOrigin,10));

                    //display other user position
                    refOtherUserLocation.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            otherLastKnownLocation = dataSnapshot.getValue().toString();
                            if(markerOtherLocation != null) {
                                markerOtherLocation.remove();}
                            if(requestingLocationUpdates){
                                markerOtherLocation = mMap.addMarker(new MarkerOptions().position(stringToLatLng(otherLastKnownLocation)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    refOtherUserState.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if( dataSnapshot.getValue()!=null) {
                                if(dataSnapshot.getValue().toString().equals("false")&& markerOtherLocation!=null){
                                    markerOtherLocation.remove();
                                }
                              }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }




            });
        // R.id.map is a FrameLayout, not a Fragment
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
        if(locationCallback!=null){
           // stopLocationUpdate();
        }

    }


    private void startAndStop(View view) {

        startSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    startLocationUpdates();
                    requestingLocationUpdates=true;
                    myRefState.setValue(requestingLocationUpdates);
                }
                else
                {
                    requestingLocationUpdates=false;
                    stopLocationUpdate();
                    if(markerOtherLocation !=null){
                        markerOtherLocation.remove();
                    }
                    myRefState.setValue(requestingLocationUpdates);


                }
            }
        });
    }



    private LatLng stringToLatLng(String coordString){
        String[] lat = coordString.split(",");
        Double latitude= Double.valueOf(lat[0]);
        Double longitude= new Double(lat[1]);
        return new LatLng(latitude,longitude);
    }
    private void getFBref() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());


        String id = acct.getId();
        String otherId;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRefLocation = database.getReference(id+"/Location");
        myRefState= database.getReference(id+"/State");


        if(id.equals("114280628950980724360")) {
            otherId = "108799991002058225966";
        }
        else{
            otherId="114280628950980724360";
        }
        refOtherUserLocation =database.getReference(otherId+"/Location");
        refOtherUserState= database.getReference(otherId+"/State");
        myRefState.setValue(requestingLocationUpdates);

    }


    private void fetchDirections(String origin, String destination) {
        DirectionsServiceListener directionsServiceListener = route -> {
            try {
                for (Route routes : route) {

                    mMap.addPolyline(new PolylineOptions().addAll(routes.points));
                }
            } catch (Exception e) {
            }
        };


       new DirectionsWebService( directionsServiceListener,origin, destination).execute();
    }



    private void startLocationUpdates() {

        //Get the location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        locationCallback = new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    lastKnownLocation= new LatLng(location.getLatitude(),location.getLongitude());
                    myRefLocation.setValue(lastKnownLocation.latitude+","+lastKnownLocation.longitude);
                    if(markerMyLocation !=null){
                        markerMyLocation.remove();
                    }
                    markerMyLocation=     mMap.addMarker(new MarkerOptions().position(lastKnownLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));


                }
            };


        };
        fusedLocationClient.requestLocationUpdates(createLocationRequest(),
                locationCallback,
                Looper.getMainLooper());
    }


    private void stopLocationUpdate(){
        fusedLocationClient.removeLocationUpdates(locationCallback);
        fusedLocationClient = null;
        locationCallback = null;

    }

    protected LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        //rate in milliseconds at which the app prefers to receive location updates
        locationRequest.setInterval(1000);
        //fastest rate in milliseconds at which the app can handle location
        locationRequest.setFastestInterval(1000);
        //most precise location possible. GPS location
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("requestingLocationUpdates",requestingLocationUpdates);
        outState.putBoolean("switchState",startSwitch.isActivated());
    }
}