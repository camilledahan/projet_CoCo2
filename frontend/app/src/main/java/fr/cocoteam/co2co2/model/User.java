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
    private String _id;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private String age;
    private int is_driver;
    private String description;

    private Trip trip;
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
    }

}

