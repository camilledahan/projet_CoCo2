package fr.cocoteam.co2co2.utils;

import androidx.recyclerview.widget.SortedList;

import fr.cocoteam.co2co2.model.User;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    static final String BASE_URL = "test";

    GsonConverterFactory gsonFactory = GsonConverterFactory.create();
    OkHttpClient okHttpClient = new OkHttpClient();

    Retrofit retrofitMatchUser = new Retrofit.Builder()
            .baseUrl(BASE_URL+":15445454")
            .client(okHttpClient)
            .addConverterFactory(gsonFactory)
            .build();

    Retrofit retrofitApiV2 = new Retrofit.Builder()
            .baseUrl("https://futurestud.io/v2/")
            .client(okHttpClient)
            .addConverterFactory(gsonFactory)
            .build();

    public Retrofit getRetrofitMatchUser() {
        return retrofitMatchUser;
    }


    public Retrofit getRetrofitApiV2() {
        return retrofitApiV2;
    }

}
