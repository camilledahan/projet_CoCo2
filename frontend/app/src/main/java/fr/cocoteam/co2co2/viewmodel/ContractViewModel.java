package fr.cocoteam.co2co2.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fr.cocoteam.co2co2.model.Agreement;
import fr.cocoteam.co2co2.model.Day;
import fr.cocoteam.co2co2.model.Trip;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.model.UserMatch;
import fr.cocoteam.co2co2.service.RetrofitInterface;
import fr.cocoteam.co2co2.utils.EnumContractStatus;
import fr.cocoteam.co2co2.utils.ViewModelInterface;
import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContractViewModel extends ViewModelInterface {
    // TODO: Implement the ViewModel

    MutableLiveData<List<Agreement>> currentAgreements;
    MutableLiveData<Agreement> currentAgreement= new MutableLiveData<>();

    RetrofitInterface retrofit = instantiateRetrofit("http://matchservice.azurewebsites.net");

    public LiveData<List<Agreement>> getCurrentAgreements() {
        if (currentAgreements == null) {
            currentAgreements = new MutableLiveData<List<Agreement>>();
            //allow to notify fragment even when the list is null
            currentAgreements.postValue(new ArrayList<>());
        }
        return currentAgreements;
    }

    public LiveData<Agreement> getCurrentAgreement() {
        if (currentAgreement == null) {
            currentAgreement = new MutableLiveData<Agreement>();

        }
        return currentAgreement;
    }


//to start covoiturage
    public void updateCurrentAgreement(Agreement agreement) {

        currentAgreement.postValue(agreement);
    }



    public void getAllAgreement(String email){
        Call<List<Agreement>> call = retrofit.getAgreements(email);
        call.enqueue(new Callback<List<Agreement>>() {
            @Override
            public void onResponse(Call<List<Agreement>> call, Response<List<Agreement>> response) {
                //Log.i("Get Agreement", "Succed request,"+response.body().size()+" objects received");
                currentAgreements.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Agreement>> call, Throwable t) {
                Log.e("Get Agreement", t.getMessage());
            }
        });
    }

}
