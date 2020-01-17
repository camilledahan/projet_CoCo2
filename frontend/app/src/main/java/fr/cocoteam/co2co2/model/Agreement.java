package fr.cocoteam.co2co2.model;

import java.util.List;

import fr.cocoteam.co2co2.model.Trip;
import io.realm.RealmObject;

public class Agreement extends RealmObject {

    private String _id;
    private Trip trip;
    private String status;
    private List<Day> days;
    private int currentWeek;


    public Agreement(String _id, Trip trip, String status, List<Day> days, int currentWeek) {
        this._id = _id;
        this.trip = trip;
        this.status = status;
        this.days = days;
        this.currentWeek = currentWeek;
    }

    public int getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(int currentWeek) {
        this.currentWeek = currentWeek;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
