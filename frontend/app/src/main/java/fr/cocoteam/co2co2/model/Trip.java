package fr.cocoteam.co2co2.model;

import io.realm.RealmObject;

public class Trip extends RealmObject {
    private String id_user;
    private String departure;
    private String arrival;
    private String heure;

    public Trip(){}

    public Trip(String departure, String arrival,String id_user,String heure) {
        this.id_user =id_user;
        this.departure = departure;
        this.arrival = arrival;
        this.heure=heure;


    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getarrival() {
        return arrival;
    }

    public void setarrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDepart() {
        return this.departure;
    }

    public String getArrivee() {
        return this.arrival;
    }
}
