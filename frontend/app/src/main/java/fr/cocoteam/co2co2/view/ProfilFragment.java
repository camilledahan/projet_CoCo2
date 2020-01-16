package fr.cocoteam.co2co2.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
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

import java.util.EventListener;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.viewmodel.ConnectionViewModel;
import fr.cocoteam.co2co2.viewmodel.ProfilViewModel;
import fr.cocoteam.co2co2.viewmodel.SplashScreenViewModel;

import static android.content.ContentValues.TAG;

public class ProfilFragment extends Fragment implements View.OnClickListener{

  private ImageButton SettingButton;
  private Button Buttonlogout;
    private ProfilViewModel mViewModel;

    OnHeadlineSelectedListener callback;

    //private ProfilViewModel viewModel;

    //public String UserName;
    //public String UserEmail;
    //public String UserDescription;
    //private TextView name,phone ,description;



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
        mViewModel.loadData();


        SettingButton = view.findViewById(R.id.imageButtonSetting);
        Buttonlogout = view.findViewById(R.id.button_logout);
        /*
        name = findViewById(R.id.name );
        phone = findViewById(R.id.phone );
        description = findViewById(R.id.description );

*/

        //set listeners
        SettingButton.setOnClickListener(this);

        Buttonlogout.setOnClickListener(this);
/*
        //observe User mutableLiveData

        Observer<User> currentUserObserver;
        currentUserObserver = user -> {
            try {
                 JSONObject jsonObject = new JSONObject(response)
                UserName = (String) User.getString("name");
                UserEmail = (String) User.getString("email");
                UserDescription = (String) User.getString("description");

                    public void run(){
                        Log.v("Profile",""+UserName+"\n"+UserEmail+UserDescription+"\n");
                        Toast.makeText(getApplicationContext(),
                                "Name: " + UserName + "\nEmail: " + UserEmail,
                                Toast.LENGTH_LONG).show();
                    };

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };

        ViewModel.getCurrentUser().observe(this,currentUserObserver);
        */


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


}
