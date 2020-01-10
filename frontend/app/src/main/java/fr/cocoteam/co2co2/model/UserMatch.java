package fr.cocoteam.co2co2.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class UserMatch extends RealmObject {

    @PrimaryKey
    @Required
    private String userMatchId;
    private String name;
    private String surname;
    private Integer age;
    private String startLocation;
    private String endLocation;
    private Integer startTime;
    private Integer endTime;

    public UserMatch(String name, String surname, Integer age, String startLocation, String endLocation, Integer startTime, Integer endTime) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
