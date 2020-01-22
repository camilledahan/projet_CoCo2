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
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindCarViewModel extends ViewModelInterface {


    MutableLiveData<List<UserMatch>> currentMatch;
    RetrofitInterface retrofit = instantiateRetrofit("http://13.80.79.115:8080/");
    Realm realmInstance = Realm.getDefaultInstance();


    public LiveData<List<UserMatch>> getCurrentUserMatch() {
        if (currentMatch == null) {
            currentMatch = new MutableLiveData<List<UserMatch>>();
            //allow to notify fragment even when the list is null
            currentMatch.postValue(new ArrayList<>());
        }
        return currentMatch;
    }

    public void updateMatchStatus(UserMatch match,String email){
        Call<ResponseBody> call = retrofit.updateMatch(email, match);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("update user","Response");
                getAllMatches(email);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("update user","Error");
            }
       });
    }


    public void getFriends(){
        realmInstance.beginTransaction();
        realmInstance.where(UserMatch.class).equalTo("added",true);
        realmInstance.commitTransaction();
    }

    private void saveUserToLocalDb(List<UserMatch> matches) {
        realmInstance.beginTransaction();
        Realm.getDefaultInstance().copyToRealmOrUpdate(matches);
        realmInstance.commitTransaction();
        Log.i("DB","Saved matches to db");
    }


    public void getAllMatches(String email){
        Call<List<UserMatch>> call = retrofit.getMatches(email);
        call.enqueue(new Callback<List<UserMatch>>() {
            @Override
            public void onResponse(Call<List<UserMatch>> call, Response<List<UserMatch>> response) {
                saveUserToLocalDb(response.body());
                currentMatch.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<UserMatch>> call, Throwable t) {
                Log.e("Error getting user :", t.getMessage());
            }
        });
    }







}
