package fr.cocoteam.co2co2.utils;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.cocoteam.co2co2.service.RetrofitInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewModelInterface extends ViewModel {


    public RetrofitInterface instantiateRetrofit(String baseUrl){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(RetrofitInterface.class);


    }

}
