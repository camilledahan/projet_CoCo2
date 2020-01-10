package fr.cocoteam.co2co2.Listener;

import java.util.List;

import fr.cocoteam.co2co2.DirectionModel.Route;

public interface DirectionsServiceListener {
    void onDirectionFinderSuccess(List<Route> direction);

}
