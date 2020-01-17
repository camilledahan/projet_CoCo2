package fr.cocoteam.co2co2.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

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


    /*public User(int ID_USER, String login, String description) {
        this.ID_USER = ID_USER;
        this.login = login;
        this.description = description;
    }*/




    public User(String _id, String email, String name, String surname, String phone, String age, int is_driver, String description) {
        this._id = _id;
        this.email = email;

        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.age = age;
        this.is_driver = is_driver;
        this.description = description;
        this.userMatches = userMatches;
        this.email =email;
    }

    public User(String name, String surname, Trip trip, String age, int isDriver, String telephone, String description, RealmList<UserMatch> userMatches, String email) {
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
        return this.email;
    }
}

