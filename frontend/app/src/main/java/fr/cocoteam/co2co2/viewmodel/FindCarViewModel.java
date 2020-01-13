package fr.cocoteam.co2co2.viewmodel;

import android.net.UrlQuerySanitizer;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;
import fr.cocoteam.co2co2.model.User;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindCarViewModel extends ViewModel {


    static final String BASE_URL = "test";

    GsonConverterFactory gsonFactory = GsonConverterFactory.create();
    OkHttpClient okHttpClient = new OkHttpClient();

    /*Retrofit retrofitMatchUser = new Retrofit.Builder()
            .baseUrl(BASE_URL+":15445454")
            .client(okHttpClient)
            .addConverterFactory(gsonFactory)
            .build();
*/
    //TODO fais appelle au service pour récuperer tous les matchs

    public List<User> generateRandomUser(int number){
        List<User> users = new ArrayList<>();
        for(int i =0;i<=number;i++){
            User tmpUser = new User("Name"+i,"Lyon","Paris");
            users.add(tmpUser);
        }
        return users;
    }





}
