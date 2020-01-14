package fr.cocoteam.co2co2.view;


import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import fr.cocoteam.co2co2.model.directionModel.Route;
import fr.cocoteam.co2co2.service.DirectionsWebService;
import fr.cocoteam.co2co2.listener.DirectionsServiceListener;
import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
    private SupportMapFragment mapFragment;
   private  List<Route> direction;
   private String origin ="45.75,4.7";
   private String destination = "45.73,4.9";
   private GoogleMap mMap;

    public MapFragment() {
        // Required empty public constructor
    }
private LatLng stringToLatLng(String coordString){
    String[] lat = coordString.split(",");
     Double latitude= Double.valueOf(lat[0]);
    Double longitude= new Double(lat[1]);
    return new LatLng(latitude,longitude);
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        fetchDirections( origin, destination);
        final LatLng latLngOrigin = stringToLatLng(origin);


        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    mMap =googleMap;
                    mMap.addMarker(new MarkerOptions().position(stringToLatLng(origin)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    mMap.addMarker(new MarkerOptions().position(stringToLatLng(destination)));
                    googleMap.setMyLocationEnabled(true);
                    googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                        @Override
                        public boolean onMyLocationButtonClick() {
                            Toast.makeText(getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    googleMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
                        @Override
                        public void onMyLocationClick(@NonNull Location location) {
                            Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();

                        }
                    });

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOrigin,10));
                }


            });



        }
        // R.id.map is a FrameLayout, not a Fragment
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        return view;
    }


    private void fetchDirections(String origin, String destination) {
        DirectionsServiceListener directionsServiceListener = new DirectionsServiceListener() {
            @Override


            public void onDirectionFinderSuccess(final List<Route> route) {


                try {
                    for (Route routes : route) {

                        mMap.addPolyline(new PolylineOptions().addAll(routes.points));
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error occurred on finding the directions...", Toast.LENGTH_SHORT).show();
                }
            }


            };


       new DirectionsWebService( directionsServiceListener,origin, destination).execute();
    }

}