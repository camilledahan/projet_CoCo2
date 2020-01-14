package fr.cocoteam.co2co2.viewmodel;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import fr.cocoteam.co2co2.model.Trip;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.service.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class UserViewModel extends ViewModel {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://userprojetmajeur.azurewebsites.net/";

    private MutableLiveData<User> user;

    public LiveData<User> getUsers() {
        if (user == null) {
            user = new MutableLiveData<User>();
            getInfo();
        }
        return user;
    }

public void postUser(User user, Trip trip){
    instantiateRetrofit();


    Call<User> call = retrofitInterface.sendUser(user);
    call.enqueue(new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.code() == 200) {

                Log.d(TAG, "onResponse: "+response.body());


            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {

            Log.d(TAG, "onFailure: "+t.getMessage());
        }
    });

    Call<Trip> callTrip = retrofitInterface.sendTrip(trip);
    callTrip.enqueue(new Callback<Trip>() {
        @Override
        public void onResponse(Call<Trip> callTrip, Response<Trip> response) {
            if (response.code() == 200) {

                Log.d(TAG, "onResponse: "+response.body());


            }
        }

        @Override
        public void onFailure(Call<Trip> callTrip, Throwable t) {

            Log.d(TAG, "onFailure: "+t.getMessage());
        }
    });

}

    public void   getInfo(){
        instantiateRetrofit();


        Call<User> call = retrofitInterface.executeUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {

                    Log.d(TAG, "onResponse: "+response.body());
                    user.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }



    public void instantiateRetrofit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);


    }


}
