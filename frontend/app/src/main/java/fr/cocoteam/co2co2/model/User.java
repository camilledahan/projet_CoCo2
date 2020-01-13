package fr.cocoteam.co2co2.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class User extends RealmObject {

    @PrimaryKey
    @Required
    private Integer userId;
    private String name;
    private String depart;
    private String arrivee;

    public User (){

    }

    public User(String name, String depart, String arrivee) {
        this.name = name;
        this.depart = depart;
        this.arrivee = arrivee;
    }

    public String getTrajet(){
        return this.depart + " à " + this.arrivee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
