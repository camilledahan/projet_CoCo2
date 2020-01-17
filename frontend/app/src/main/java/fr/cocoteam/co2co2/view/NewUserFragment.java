package fr.cocoteam.co2co2.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.model.Trip;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.viewmodel.SplashScreenViewModel;
import fr.cocoteam.co2co2.viewmodel.UserViewModel;

import static android.app.Activity.RESULT_CANCELED;

public class NewUserFragment extends Fragment {

    private static final int RESULT_OK = 2;
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
  private static  final  int AUTOCOMPLETE_REQUEST_CODE = 1;


    private UserViewModel mViewModel;

    public NewUserFragment() {
    }

    public NewUserFragment.OnHeadlineSelectedListener callback;

    public void setOnHeadlineSelectedListener(NewUserFragment.OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_new_user, container, false);

        // Initialize the SDK
        Places.initialize(getContext(), "AIzaSyBGo7-H0aIO_KTtZGGez8vPxuShRHeV8AA");

        // Create a new Places client instance
        PlacesClient placesClient = Places.createClient(getContext());

// Set the fields to specify which types of place data to
// return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

// Start the autocomplete intent.




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

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        emailField.setText(acct.getEmail());
        nameField.setText(acct.getGivenName());
        surnameField.setText(acct.getFamilyName());
        emailField.setEnabled(false);
        nameField.setEnabled(false);
        surnameField.setEnabled(false);

        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


        destinationField.setOnClickListener(view12 -> {
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.OVERLAY, fields)
                    .build(getContext());
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        });
        onActivityResult(AUTOCOMPLETE_REQUEST_CODE,RESULT_OK,intent);
        //  int age = new Integer(ageField.getText().toString());

        validerButton.setOnClickListener(view1 -> {
           if(checkEnteredData()){
               addNewUser(acct.getEmail(),acct.getDisplayName(),acct.getFamilyName(),originField.getText().toString(),destinationField.getText().toString(),ageField.getText().toString(),phoneField.getText().toString(),
                       descriptionField.getText().toString(),isDriverBtn.isActivated(),heureDepart.getText().toString());

           }

        });
        Observer<Boolean> currentUserPostObserver = isUserPost ->{
            if(isUserPost){
                callback.openSplashScreen();
            }
        };


        mViewModel.isUserPost().observe(this,currentUserPostObserver);

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
        if (phoneField.getText().toString().isEmpty() && phoneField.getText().length()==10){
            Toast.makeText(getContext(),"Please enter you phone number", Toast.LENGTH_SHORT).show();

            return false;
        }

            return true;


    }

    public interface OnHeadlineSelectedListener {
        void openSplashScreen();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                destinationField.setText(place.getAddress());
                Log.i("NEW USER", "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("NEW USER", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


}
