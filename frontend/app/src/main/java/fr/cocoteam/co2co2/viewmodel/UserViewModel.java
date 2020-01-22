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
import fr.cocoteam.co2co2.service.RetriofitInterfaceTrip;
import fr.cocoteam.co2co2.service.RetrofitInterface;
import fr.cocoteam.co2co2.utils.ViewModelInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class UserViewModel extends ViewModel {
    private Retrofit retrofit;
    private Retrofit retrofitTrip;
    private RetrofitInterface retrofitInterface;
    private RetriofitInterfaceTrip retrofitInterfaceTrip;
    private String BASE_URL_TRIP = "https://busprojetmajeur.azurewebsites.net/";
    private String BASE_URL_USER = "http://userprojetmajeur.azurewebsites.net/";

    private MutableLiveData<Boolean> isUserPost;

    public LiveData<Boolean> isUserPost() {
        if (isUserPost == null) {
            isUserPost = new MutableLiveData<Boolean>();

        }
        return isUserPost;
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
            isUserPost.postValue(false);

            Log.d(TAG, "onFailure: "+t.getMessage());
        }
    });

    Call<String> callTrip = retrofitInterfaceTrip.sendTrip(trip);
    callTrip.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            if (response.code() == 200) {

                isUserPost.postValue(true);
                Log.d(TAG, "onResponse: " + response.body());
            }

        }

        @Override
        public void onFailure(Call<String> callTrip, Throwable t) {

            Log.d(TAG, "onFailure: "+t.getMessage());
        }
    });

}

    public void instantiateRetrofit(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_USER)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        retrofitTrip = new Retrofit.Builder()
                .baseUrl(BASE_URL_TRIP)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofitInterfaceTrip = retrofitTrip.create(RetriofitInterfaceTrip.class);

    }


}
