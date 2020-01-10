package fr.cocoteam.co2co2.viewmodel;

import android.net.UrlQuerySanitizer;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;
import fr.cocoteam.co2co2.model.User;

public class FindCarViewModel extends ViewModel {


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
