package fr.cocoteam.co2co2.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EventListener;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.viewmodel.ConnectionViewModel;
import fr.cocoteam.co2co2.viewmodel.ProfilViewModel;
import fr.cocoteam.co2co2.viewmodel.SplashScreenViewModel;
import io.realm.Realm;
import io.realm.RealmQuery;

import static android.content.ContentValues.TAG;

public class ProfilFragment extends Fragment implements View.OnClickListener{

    private ImageView settingButton;
    private Button buttonlogout;
    private ProfilViewModel mViewModel;
    private Context context;

    OnHeadlineSelectedListener callback;


    private TextView name,surname,age,email,phone ,description,trip;



    public void setOnHeadlineSelectedListener(ProfilFragment.OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }

    public static ProfilFragment newInstance() {
        return new ProfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        mViewModel = ViewModelProviders.of(this).get(ProfilViewModel.class);
        //mViewModel.loadData();

        settingButton = view.findViewById(R.id.imageButtonSetting);
        buttonlogout = view.findViewById(R.id.button_logout);

        name = view.findViewById(R.id.user_name);
        surname =view.findViewById(R.id.user_surname);
        age = view.findViewById(R.id.user_age);
        email = view.findViewById(R.id.user_email);
        phone = view.findViewById(R.id.user_phone);
        description = view.findViewById(R.id.user_description);
        trip = view.findViewById(R.id.user_trip);
     //   ImageView imageView =view.findViewById(R.id.imageButtonSetting);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());


    /*    URL url = null;
        if(acct.getPhotoUrl()!=null){
            try {
                url = new URL(acct.getPhotoUrl().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            settingButton.setImageBitmap(bmp);

        }*/





        //set listeners
        settingButton.setOnClickListener(this);
        buttonlogout.setOnClickListener(this);

        //observe User mutableLiveData

        Observer<User> currentUserObserver;
        currentUserObserver = user -> {
             updateUI(user);
        };

        mViewModel.getCurrentUser().observe(this,currentUserObserver);

        mViewModel.getCurrentUserProfil();

     return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButtonSetting:
                callback.onProfilOptionSelected("setting");
                break;
            case R.id.button_logout:
                callback.onProfilOptionSelected("logout");
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public interface OnHeadlineSelectedListener {
        boolean onProfilOptionSelected(String classe);
    }


    private void updateUI(User user){
        name.setText(user.getName());
        surname.setText(user.getSurname());
        age.setText(user.getAge());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        description.setText(user.getDescription());
        trip.setText(user.getTrip().getDepart()+ " to "+ user.getTrip().getArrivee());
    }


}
