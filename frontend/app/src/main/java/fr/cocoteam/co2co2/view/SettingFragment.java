package fr.cocoteam.co2co2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import fr.cocoteam.co2co2.R;

public class SettingFragment extends Fragment implements View.OnClickListener {

    public android.widget.Button Button;

    SettingFragment.OnHeadlineSelectedListener callback;

    public void setOnHeadlineSelectedListener(SettingFragment.OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);


        Button button1 = view.findViewById(R.id.imageButtonback2);


        //set listeners
        button1.setOnClickListener(this);



        return view;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButtonback2:
                callback.onSettingOptionSelected("back");
                break;
        }

    }


    public interface OnHeadlineSelectedListener {
        boolean onSettingOptionSelected(String classe);
    }

}

