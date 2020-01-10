package fr.cocoteam.co2co2.DirectionModel;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Route {

    public Distance distance;
    public Duration duration;
    public LatLng endLocation;
    public LatLng startLocation;

    public List<LatLng> points;
}
