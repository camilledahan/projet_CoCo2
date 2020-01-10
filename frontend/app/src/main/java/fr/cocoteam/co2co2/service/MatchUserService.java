package fr.cocoteam.co2co2.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.model.UserMatch;
import retrofit2.Callback;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MatchUserService {

    @GET("matches")
    @FormUrlEncoded
    //void getAllMatches(@Query("userId"), Callback<UserMatch> callback);

}
