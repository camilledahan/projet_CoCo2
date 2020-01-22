package fr.cocoteam.co2co2.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import fr.cocoteam.co2co2.model.Trip;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.service.RetrofitInterface;
import fr.cocoteam.co2co2.utils.ViewModelInterface;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashScreenViewModel extends ViewModelInterface {

    RetrofitInterface retrofit = instantiateRetrofit("http://13.80.79.115:8081/");
    Realm realmInsance = Realm.getDefaultInstance();
    MutableLiveData<User> currentUser;

    public LiveData<User> getCurrentUser() {
        if (currentUser == null) {
            currentUser = new MutableLiveData<User>();
        }
        return currentUser;
    }


    public void loadData(String email){
        getCurrentUserProfil(email);
        getUserMatch();

    }


    private void getCurrentUserProfil(String email) {


        Call<User> call = retrofit.getUser(email);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    realmInsance.beginTransaction();
                    if (!Realm.getDefaultInstance().isEmpty()) {
                        Realm.getDefaultInstance().deleteAll();
                    }

                    Realm.getDefaultInstance().copyToRealm(response.body());
                    realmInsance.commitTransaction();
                    currentUser.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error getting user :", t.getMessage());
            }
        });


    }


    private void getUserMatch(){

    }
}
