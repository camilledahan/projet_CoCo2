package fr.cocoteam.co2co2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import fr.cocoteam.co2co2.R;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private ImageButton backButton;
    private Button button_valider;

    OnHeadlineSelectedListener callback;

    public void setOnHeadlineSelectedListener(OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }
    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        backButton = view.findViewById(R.id.imageButtonback2);
        button_valider = view.findViewById(R.id.button_valider);

        //set listeners
        backButton.setOnClickListener(this);

        button_valider.setOnClickListener(this);


        return view;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButtonback2:
                callback.onSettingOptionSelected("back");
                break;
            case R.id.button_valider:
                callback.onSettingOptionSelected("valider");
                break;
        }

    }


    public interface OnHeadlineSelectedListener {
        boolean onSettingOptionSelected(String classe);
    }

}

