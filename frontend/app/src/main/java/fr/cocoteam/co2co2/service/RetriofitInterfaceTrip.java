package fr.cocoteam.co2co2.service;

import fr.cocoteam.co2co2.model.Trip;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetriofitInterfaceTrip {

    @POST("trip")
    Call<String> sendTrip(@Body Trip trip);
}
