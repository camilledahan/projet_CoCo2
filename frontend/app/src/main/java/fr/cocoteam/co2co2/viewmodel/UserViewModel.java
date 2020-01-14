package fr.cocoteam.co2co2.viewmodel;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import fr.cocoteam.co2co2.model.User;
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
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://userprojetmajeur.azurewebsites.net/";

    private MutableLiveData<String> user;

    public LiveData<String> getUsers() {
        if (user == null) {
            user = new MutableLiveData<String>();
            getInfo();
        }
        return user;
    }



    public String getInfo(){
        instantiateRetrofit();

        final String[] info = new String[1];
        /*Call<String> call = retrofitInterface.executeUser(1);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    Log.d(TAG, "onResponse: "+response.body());
                    user.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });*/
        return info[0];
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
