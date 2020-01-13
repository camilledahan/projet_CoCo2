package fr.cocoteam.co2co2.model;

import java.util.ArrayList;
import java.util.List;
import fr.cocoteam.co2co2.model.UserMatch;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    private int userId;
    private String name;
    private String surname;
    private Trip trip;
    private int age;
    private boolean isDriver;
    private int telephone;
    private String description;
    private RealmList<UserMatch> userMatches;

    public User() {

    }

    public User(int userId, String name, String surname, Trip trip, int age, boolean isDriver, int telephone, String description, RealmList<UserMatch> userMatches) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.trip = trip;
        this.age = age;
        this.isDriver = isDriver;
        this.telephone = telephone;
        this.description = description;
        this.userMatches = userMatches;
    }
}

