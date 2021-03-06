package fr.cocoteam.co2co2.model;

import io.realm.RealmObject;

public class Trip extends RealmObject {
    private String email;
    private String coords_arr;
    private String coords_dep;
    private String address_arr;
    private String address_dep;
    private String heure;




    public Trip(){}

    public Trip(String email, String coords_dep, String coords_arr, String address_arr, String address_dep, String heure) {
        this.email = email;
        this.coords_dep = coords_dep;
        this.coords_arr = coords_arr;
        this.address_arr = address_arr;
        this.address_dep = address_dep;
        this.heure = heure;
    }

    public Trip(String address_dep, String address_arr, String email, String heure) {
        this.email =email;
        this.address_dep = address_dep;
        this.address_arr = address_arr;
        this.heure=heure;


    }
    public String getCoords_dep() {
        return coords_dep;
    }

    public void setCoords_dep(String coords_dep) {
        this.coords_dep = coords_dep;
    }

    public String getCoords_arr() {
        return coords_arr;
    }

    public void setCoords_arr(String coords_arr) {
        this.coords_arr = coords_arr;
    }


    public String getDeparture() {
        return address_dep;
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
        return this.address_dep;
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
