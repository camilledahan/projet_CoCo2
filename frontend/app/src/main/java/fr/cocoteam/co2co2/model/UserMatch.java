package fr.cocoteam.co2co2.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserMatch extends RealmObject {

    private int userId;
    private String mail;
    private String name;
    private Trip trip;
    private int age;
    private boolean isDriver;
    private int telephone;
    private String description;
    private Boolean expanded = false;

    public UserMatch(){

    }

    public UserMatch(String mail, String name, Trip trip, int age, boolean isDriver, int telephone, String description) {
        this.mail = mail;
        this.name = name;
        this.trip = trip;
        this.age = age;
        this.isDriver = isDriver;
        this.telephone = telephone;
        this.description = description;
    }

    public UserMatch(int userId, String name, Trip trip, int age, boolean isDriver, int telephone, String description) {
        this.userId = userId;
        this.name = name;
        this.trip = trip;
        this.age = age;
        this.isDriver = isDriver;
        this.telephone = telephone;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}
