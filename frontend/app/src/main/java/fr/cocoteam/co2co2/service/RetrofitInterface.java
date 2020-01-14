package fr.cocoteam.co2co2.service;
import java.util.HashMap;
import java.util.List;

import fr.cocoteam.co2co2.model.Trip;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.model.UserMatch;
import  retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.POST;

import retrofit2.http.Path;

public interface RetrofitInterface {


    @GET("/user")
    Call<User> executeUser();

    @POST("user/adduser")
    Call<User> sendUser(@Body User user);
    @POST("trip/addtrip")
    Call<Trip> sendTrip(@Body Trip trip);

    @GET("/user/{email}/")
    Call<User> getUser(@Path("email") String mail);

    @GET("/match/{email}")
    Call<List<UserMatch>> getMatches(@Path("email") String mail);


}