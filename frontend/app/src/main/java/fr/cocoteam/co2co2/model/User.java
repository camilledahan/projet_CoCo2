package fr.cocoteam.co2co2.model;

public class User {

    private String name;
    private String depart;
    private String arrivee;

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
