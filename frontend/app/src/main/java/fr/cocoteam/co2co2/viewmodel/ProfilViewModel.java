package fr.cocoteam.co2co2.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.service.RetrofitInterface;
import fr.cocoteam.co2co2.utils.ViewModelInterface;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilViewModel extends ViewModelInterface {


    Realm realmInsance = Realm.getDefaultInstance();
    MutableLiveData<User> currentUser;


    public LiveData<User> getCurrentUser() {
        if (currentUser == null) {
            currentUser = new MutableLiveData<User>();
        }
        return currentUser;
    }

    public void loadData(){
        getCurrentUserProfil();
    }

    public void getCurrentUserProfil() {

        realmInsance.beginTransaction();
        currentUser.postValue(realmInsance.where(User.class).findFirst());
        realmInsance.commitTransaction();


    }

    }


