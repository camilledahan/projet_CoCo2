package fr.cocoteam.co2co2.service;
import java.util.HashMap;

import fr.cocoteam.co2co2.model.User;
import  retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @GET("/user/{email}/")
    Call<User> getUser(@Path("email") String mail);

}