package fr.cocoteam.co2co2.model;

import io.realm.RealmObject;

public class Day extends RealmObject {

    private String dayName;
    private String isSelected;

    public Day(){

    }

    public Day(String dayName, String isSelected) {
        this.dayName = dayName;
        this.isSelected = isSelected;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }
}
