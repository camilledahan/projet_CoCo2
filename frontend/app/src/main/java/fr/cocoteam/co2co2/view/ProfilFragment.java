package fr.cocoteam.co2co2.view;

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

import java.util.EventListener;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.viewmodel.ConnectionViewModel;
import fr.cocoteam.co2co2.viewmodel.ProfilViewModel;

import static android.content.ContentValues.TAG;

public class ProfilFragment extends Fragment implements View.OnClickListener{

    public android.widget.Button Button;

    OnHeadlineSelectedListener callback;

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


        Button button1 = view.findViewById(R.id.button_setting);

        Button button2 = view.findViewById(R.id.button_payment);
        Button button3 = view.findViewById(R.id.button_logout);




        //set listeners
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);


        return view;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_setting:
                callback.onProfilOptionSelected("setting");
                break;
            case R.id.button_payment:
                callback.onProfilOptionSelected("payment");
                break;

            case R.id.button_logout:
                callback.onProfilOptionSelected("logout");
                break;



        }

    }


    public interface OnHeadlineSelectedListener {
        boolean onProfilOptionSelected(String classe);
    }


}
