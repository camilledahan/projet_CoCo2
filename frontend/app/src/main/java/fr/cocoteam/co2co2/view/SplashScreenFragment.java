package fr.cocoteam.co2co2.view;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.viewmodel.SplashScreenViewModel;

public class SplashScreenFragment extends Fragment {

    private SplashScreenViewModel mViewModel;
    private ProgressBar progressBar;

    public static SplashScreenFragment newInstance() {
        return new SplashScreenFragment();
    }

    public OnHeadlineSelectedListener callback;

    public void setOnHeadlineSelectedListener(SplashScreenFragment.OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        //bind view
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setProgress(0);

        //viewmodel
        mViewModel = ViewModelProviders.of(this).get(SplashScreenViewModel.class);
        mViewModel.loadData(acct.getEmail());


        //observe User mutableLiveData
        Observer<User> currentUserObserver = user -> {
            try {
                for(int i=0;i<5;i++){
                    progressBar.setProgress(i*20);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(user!=null){
                if (user.getEmail().isEmpty()){
                    callback.openNewUserFragment();
                }
                else {
                    callback.onDataLoaded();

                }
            }

        };

        mViewModel.getCurrentUser().observe(this,currentUserObserver);

        return view;
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
        void onDataLoaded();
        void openNewUserFragment();


    }



}
