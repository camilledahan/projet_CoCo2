package fr.cocoteam.co2co2.model;

import io.realm.RealmObject;

public class Trip extends RealmObject {
    private String email;
    private String address_arr;
    private String address_dep;
    private String heure;

    public Trip(){}

    public Trip(String address_dep, String address_arr,String email,String heure) {
        this.email =email;
        this.address_arr = address_dep;
        this.address_dep = address_arr;
        this.heure=heure;


    }

    public String getDeparture() {
        return address_arr;
    }

    public void setDeparture(String departure) {
        this.address_arr = departure;
    }

    public String getarrival() {
        return address_arr;
    }

    public void setarrival(String arrival) {
        this.address_arr = arrival;
    }

    public String getDepart() {
        return this.address_arr;
    }

    public String getArrivee() {
        return this.address_arr;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
}
