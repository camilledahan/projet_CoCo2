package fr.cocoteam.co2co2.model;

import io.realm.RealmObject;

public class Day extends RealmObject {

    private String dayName;
    private boolean isSelected;

    public Day(){

    }

    public Day(String dayName, boolean isSelected) {
        this.dayName = dayName;
        this.isSelected = isSelected;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
