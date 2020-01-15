package fr.cocoteam.co2co2.viewmodel;

import android.net.UrlQuerySanitizer;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

import fr.cocoteam.co2co2.model.Trip;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.model.UserMatch;
import okhttp3.OkHttpClient;
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

    public List<UserMatch> generateRandomUser(int number){
        List<UserMatch> users = new ArrayList<>();
        for(int i =0;i<=number;i++){

            UserMatch tmpUser = new UserMatch("paulmea69@gmail.com","Paul",new Trip("Lyon","Paris","paulmea69@gmail.com","8:30"),18,true,063115547,"Salut c'est Paul");

            users.add(tmpUser);
        }
        return users;
    }





}
