package fr.cocoteam.co2co2.model;

import io.realm.RealmModel;
import io.realm.RealmObject;

public class Trip extends RealmObject {
    private String depart;
    private String arrivee;

    public Trip(){}

    public Trip(String depart, String arrivee) {
        this.depart = depart;
        this.arrivee = arrivee;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrivee() {
        return arrivee;
    }

    public void setArrivee(String arrivee) {
        this.arrivee = arrivee;
    }
}
