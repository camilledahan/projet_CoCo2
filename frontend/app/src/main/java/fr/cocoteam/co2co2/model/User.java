package fr.cocoteam.co2co2.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject  {

    @PrimaryKey
    private String _id;
    private String email;
    private String name;
    private String surname;
    private Trip trip;
    private String age;
    private int is_driver;
    private String phone;
    private String description;
    private RealmList<UserMatch> userMatches;





    public User() {

    }





    public User(String email,String name, String surname, String age, int isDriver, String telephone, String description, RealmList<UserMatch> userMatches, Trip trip) {
        this.name = name;
        this.surname = surname;
        this.trip = trip;
        this.age = age;
        this.is_driver = isDriver;
        this.phone = telephone;
        this.description = description;
        this.userMatches = userMatches;
        this.email =email;

    }



    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {this.email = email;}

    public String getName() {
        return name;
    }
    public void setName(String name) {this.name = name;}

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {this.surname = surname;}


    public String getPhone() { return this.phone; }
    public String getAge() { return this.age; }
    public String getDescription() { return this.description; }
    public Trip getTrip() {return trip; }
    public void   setTrip(Trip trip){this.trip= trip;}
    public String get_id() {
        return this._id;
    }
    public int getIsDriver() { return is_driver;
    }

}

