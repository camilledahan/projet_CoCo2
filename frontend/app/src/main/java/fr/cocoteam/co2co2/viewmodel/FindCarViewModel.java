package fr.cocoteam.co2co2.viewmodel;

import android.net.UrlQuerySanitizer;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.cocoteam.co2co2.model.Trip;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.model.UserMatch;
import fr.cocoteam.co2co2.service.RetrofitInterface;
import fr.cocoteam.co2co2.utils.ViewModelInterface;
import io.realm.Realm;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindCarViewModel extends ViewModelInterface {


    MutableLiveData<List<UserMatch>> currentMatch;
    RetrofitInterface retrofit = instantiateRetrofit();
    Realm realmInstance = Realm.getDefaultInstance();


    public LiveData<List<UserMatch>> getCurrentUserMatch() {
        if (currentMatch == null) {
            currentMatch = new MutableLiveData<List<UserMatch>>();
        }
        return currentMatch;
    }

    public void generateRandomUser(int number){
        List<UserMatch> users = new ArrayList<>();
        for(int i =0;i<=number;i++){

            UserMatch tmpUser = new UserMatch("paulmea69@gmail.com","Paul",new Trip("Lyon","Paris","paulmea69@gmail.com","8:30"),18,true,063115547,"Salut c'est Paul");
            users.add(tmpUser);
        }
        currentMatch.postValue(users);
    }

    public List<UserMatch> getRandomUser(int number){
        List<UserMatch> users = new ArrayList<>();
        for(int i =0;i<=number;i++){
            UserMatch tmpUser = new UserMatch("paulmea69@gmail.com","Paul",new Trip("Lyon","Paris","paulmea69@gmail.com","8:30"),18,true,063115547,"Salut c'est Paul");
            users.add(tmpUser);
        }
        return users;
    }


    public void getAllMatches(){
        Call<List<UserMatch>> call = retrofit.getMatches("test");
        call.enqueue(new Callback<List<UserMatch>>() {
            @Override
            public void onResponse(Call<List<UserMatch>> call, Response<List<UserMatch>> response) {
                realmInstance.beginTransaction();
                if (!Realm.getDefaultInstance().isEmpty()){
                    Realm.getDefaultInstance().deleteAll();
                }
                Realm.getDefaultInstance().copyToRealm(response.body());
                realmInstance.commitTransaction();
                currentMatch.postValue(response.body());

            }

            @Override
            public void onFailure(Call<List<UserMatch>> call, Throwable t) {
                Log.e("Error getting user :", t.getMessage());
            }
        });
    }







}
