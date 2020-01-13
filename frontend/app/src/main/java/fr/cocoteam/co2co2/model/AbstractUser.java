package fr.cocoteam.co2co2.model;

public class AbstractUser {

    private String name;
    private Trip trip;
    private int age;
    private  int telephone;

    public AbstractUser(String name, Trip trip) {
        this.name = name;
        this.trip=trip;

    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }



    public Trip getTrip(){
        return this.trip;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
