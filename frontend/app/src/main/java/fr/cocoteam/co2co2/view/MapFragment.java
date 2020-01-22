package fr.cocoteam.co2co2.view;


import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
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

import java.io.Serializable;
import java.util.List;

import fr.cocoteam.co2co2.model.Agreement;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.model.directionModel.Route;
import fr.cocoteam.co2co2.service.DirectionsWebService;
import fr.cocoteam.co2co2.listener.DirectionsServiceListener;
import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.viewmodel.ContractViewModel;
import fr.cocoteam.co2co2.viewmodel.ProfilViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
    private SupportMapFragment mapFragment;
   private  List<Route> direction;
   private String origin;
   private String destination;
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
    private ContractViewModel contractViewModel;
    private Button callButton;
    private ProfilViewModel mViewModel;
    private User currentUser;
    private Agreement currentAgreement;
    public MapFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        MapsInitializer.initialize(getContext());
        contractViewModel= ViewModelProviders.of(getActivity()).get(ContractViewModel.class);
        mViewModel = ViewModelProviders.of(this).get(ProfilViewModel.class);
        Observer<User> currentUserObserver;
        currentUserObserver = user -> {
            currentUser=user;
                createMap();
                if(user.getTrip()!=null){
                    fetchDirections(user.getTrip().getCoords_dep(),user.getTrip().getCoords_arr(),Color.BLUE);

                }



        };
        mViewModel.getCurrentUser().observe(this,currentUserObserver);





        Observer<Agreement> currentAgreementObserver;
        currentAgreementObserver = agreement -> {
            if(agreement ==null) {
                Toast.makeText(getContext()," vous n'avez pas de covoiturage , allez dans vos contrats pour commencer un covoiturage",Toast.LENGTH_LONG).show();
            }else {
                currentAgreement=agreement;
                getFireBaseRef();
                updateMap(agreement);

            }


        };
        contractViewModel.getCurrentAgreement().observe(this,currentAgreementObserver);


        mViewModel.getCurrentUserProfil();
         startSwitch = view.findViewById(R.id.startCovoiturage);
         callButton = view.findViewById(R.id.callButton);
        if (savedInstanceState != null) {
                requestingLocationUpdates = savedInstanceState.getBoolean("requestingLocationUpdates")   ;
                startSwitch.setChecked(savedInstanceState.getBoolean("startSwitch"));
        }

        //handle start and sotp covoiturage
        startAndStop();
        return view;
    }

    private void updateMap(Agreement agreement) {


        if(agreement.getTrip()!=null){
            origin=agreement.getTrip().getCoords_dep();
            destination=agreement.getTrip().getCoords_arr();
            fetchDirections( origin, destination,Color.BLACK);
            callButton.setVisibility(View.VISIBLE);
            callButton.setOnClickListener(view -> callPhoneNumber(agreement.getPassager().getPhone()));
        }

    }


    private void createMap(){

        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            googleMap.setMyLocationEnabled(true);
            if(currentUser!=null){
                final LatLng latLngOrigin = MapFragment.this.stringToLatLng(currentUser.getTrip().getCoords_dep());
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOrigin, 13));
            }

        });
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

    }


    private void startAndStop() {

        startSwitch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked){
                startLocationUpdates();
                requestingLocationUpdates=true;
                myRefState.setValue(requestingLocationUpdates);
                showUsersLocation();
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
        });
    }



    private LatLng stringToLatLng(String coordString){
        String[] lat = coordString.split(",");
        Double latitude= Double.valueOf(lat[0]);
        Double longitude= new Double(lat[1]);
        return new LatLng(latitude,longitude);
    }
    private void getFireBaseRef() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRefLocation = database.getReference(currentUser.get_id()+"/Location");
        myRefState= database.getReference(currentUser.get_id()+"/State");
        refOtherUserLocation =database.getReference(currentAgreement.getPassager().get_id()+"/Location");
        refOtherUserState= database.getReference(currentAgreement.getPassager().get_id()+"/State");
        myRefState.setValue(requestingLocationUpdates);

    }

    public void callPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    private void fetchDirections(String origin, String destination, int color) {
        DirectionsServiceListener directionsServiceListener = route -> {
            try {
                for (Route routes : route) {

                    mMap.addPolyline(new PolylineOptions().addAll(routes.points).color(color));
                    mMap.addMarker(new MarkerOptions().position(stringToLatLng(origin)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.addMarker(new MarkerOptions().position(stringToLatLng(destination)));


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
    private void showUsersLocation(){



    //Location state
        refOtherUserState.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    if (dataSnapshot.getValue().toString().equals("false") && markerOtherLocation != null) {
                        markerOtherLocation.remove();
                    }
                    if(dataSnapshot.getValue().toString().equals("true")) {
                        //display other user position
                        refOtherUserLocation.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (markerOtherLocation != null) {
                                    markerOtherLocation.remove();
                                }
                                if (requestingLocationUpdates) {
                                    otherLastKnownLocation = dataSnapshot.getValue().toString();
                                    markerOtherLocation = mMap.addMarker(new MarkerOptions().position(stringToLatLng(otherLastKnownLocation)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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