package fr.cocoteam.co2co2.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.model.Trip;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.viewmodel.UserViewModel;

public class NewUserFragment extends Fragment {

    Button validerButton ;
    EditText nameField ;
    EditText surnameField ;
    EditText emailField ;
    EditText descriptionField ;
    EditText phoneField ;
    EditText destinationField ;
    EditText ageField ;
    EditText originField ;
    Button isDriverBtn ;
    EditText heureDepart;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public NewUserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_new_user, container, false);
          validerButton = view.findViewById(R.id.valider);
         nameField = view.findViewById(R.id.name);
         surnameField = view.findViewById(R.id.surname);
         emailField = view.findViewById(R.id.email);
         descriptionField = view.findViewById(R.id.description);
         phoneField = view.findViewById(R.id.phone);
         destinationField = view.findViewById(R.id.destination);
         ageField = view.findViewById(R.id.age);
         originField = view.findViewById(R.id.origin);
         isDriverBtn = view.findViewById(R.id.isDriver);
         heureDepart = view.findViewById(R.id.heure_depart);

     //  int age = new Integer(ageField.getText().toString());

        validerButton.setOnClickListener(view1 -> {
           if(checkEnteredData()){
               addNewUser(emailField.getText().toString(),nameField.getText().toString(),surnameField.getText().toString(),originField.getText().toString(),destinationField.getText().toString(),ageField.getText().toString(),phoneField.getText().toString(),
                       descriptionField.getText().toString(),isDriverBtn.isActivated(),heureDepart.getText().toString());
           }

        });
        return view;
    }

    public void addNewUser(String email, String name,String surname, String depart, String arrivee, String age,String telephone,String Description, boolean isDriver,String heure_depart){

        User user = new User(name,surname,null,age,isDriver,telephone,Description,null,email);
        Trip trip = new Trip(depart,arrivee,user.getEmail(),heure_depart);
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.postUser(user,trip);

    }

  public boolean checkEnteredData(){
        if(emailField.getText().toString().isEmpty() || !emailField.getText().toString().trim().matches(emailPattern) ){

            Toast.makeText(getContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(nameField.getText().toString().isEmpty()) {
            Toast.makeText(getContext(),"Please enter your name", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (surnameField.getText().toString().isEmpty()){
            Toast.makeText(getContext(),"Please enter you surname", Toast.LENGTH_SHORT).show();

            return false;
        }
        if (phoneField.getText().toString().isEmpty()){
            Toast.makeText(getContext(),"Please enter you phone number", Toast.LENGTH_SHORT).show();

            return false;
        }

            return true;


    }


}
