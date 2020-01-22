package fr.cocoteam.co2co2.model;

import java.util.List;

import fr.cocoteam.co2co2.model.Trip;
import io.realm.RealmList;
import io.realm.RealmObject;

public class Agreement extends RealmObject {

    private User user;

    //private RealmList<Day> days;
    private int lundi;
    private int mardi;
    private int mercredi;
    private int jeudi;
    private int vendredi;
    private int currentWeek;

    public Agreement(){

    }

    public Agreement(User userPassager, int lundi, int mardi, int mercredi, int jeudi, int vendredi, int currentWeek) {
        this.user = userPassager;
        this.lundi = lundi;
        this.mardi = mardi;
        this.mercredi = mercredi;
        this.jeudi = jeudi;
        this.vendredi = vendredi;
        this.currentWeek = currentWeek;
    }

    public User getPassager() {
        return this.user;
    }

    public int getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(int currentWeek) {
        this.currentWeek = currentWeek;
    }


    public Trip getTrip() {
        return user.getTrip();
    }

    public void setUserPassager(User userPassager) {
        this.user = userPassager;
    }

    public int getLundi() {
        return lundi;
    }

    public void setLundi(int lundi) {
        this.lundi = lundi;
    }

    public int getMardi() {
        return mardi;
    }

    public void setMardi(int mardi) {
        this.mardi = mardi;
    }

    public int getMercredi() {
        return mercredi;
    }

    public void setMercredi(int mercredi) {
        this.mercredi = mercredi;
    }

    public int getJeudi() {
        return jeudi;
    }

    public void setJeudi(int jeudi) {
        this.jeudi = jeudi;
    }

    public int getVendredi() {
        return vendredi;
    }

    public void setVendredi(int vendredi) {
        this.vendredi = vendredi;
    }
}
