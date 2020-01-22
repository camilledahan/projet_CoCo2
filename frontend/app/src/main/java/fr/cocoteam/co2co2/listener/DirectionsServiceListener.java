package fr.cocoteam.co2co2.listener;

import java.util.List;

import fr.cocoteam.co2co2.model.directionModel.Route;

public interface DirectionsServiceListener {
    void onDirectionFinderSuccess(List<Route> direction);

}
