package fr.cocoteam.co2co2.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fr.cocoteam.co2co2.model.Agreement;
import fr.cocoteam.co2co2.model.Day;
import fr.cocoteam.co2co2.model.Trip;
import fr.cocoteam.co2co2.model.UserMatch;
import fr.cocoteam.co2co2.utils.EnumContractStatus;
import fr.cocoteam.co2co2.utils.ViewModelInterface;
import io.realm.Realm;
import io.realm.RealmList;

public class ContractViewModel extends ViewModelInterface {
    // TODO: Implement the ViewModel

    MutableLiveData<List<Agreement>> currentAgreements;

    public LiveData<List<Agreement>> getCurrentAgreements() {
        if (currentAgreements == null) {
            currentAgreements = new MutableLiveData<List<Agreement>>();
            //allow to notify fragment even when the list is null
            currentAgreements.postValue(new ArrayList<>());
        }
        return currentAgreements;
    }

    public void createFakeAgreements(int nb){
        List<Agreement> agreements = new ArrayList<>();
        for(int i=0;i<nb; i++){
            RealmList<Day> days = new RealmList<>();
            days.add(new Day("Monday",true));
            days.add(new Day("Tuesday",true));
            days.add(new Day("Wednesday",false));
            days.add(new Day("Thurday",false));
            days.add(new Day("Friday",true));
            
            Agreement tmpAgr = new Agreement(
                    Integer.toString(nb),
                    new Trip("Marseille","Lyon","0","15:20"),
                    EnumContractStatus.TODO.toString(),
                    days,
                    54
            );
            agreements.add(tmpAgr);
        }
        currentAgreements.postValue(agreements);
    }


    //TODO crete ur method to start covoiturage bang bang
}
