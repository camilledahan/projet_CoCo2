package fr.cocoteam.co2co2.model;

import java.util.List;

public class User extends AbstractUser {

    private List<UserMatch> userMatches;

    public User(String name, Trip trip, List<UserMatch> userMatches) {

        super(name, trip);
        this.userMatches = userMatches;
    }
}
