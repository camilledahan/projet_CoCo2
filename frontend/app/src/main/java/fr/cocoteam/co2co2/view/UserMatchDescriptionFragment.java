package fr.cocoteam.co2co2.view;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.viewmodel.UserMatchDescriptionViewModel;

public class UserMatchDescriptionFragment extends Fragment {

    private UserMatchDescriptionViewModel mViewModel;

    public static UserMatchDescriptionFragment newInstance() {
        return new UserMatchDescriptionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_match_description_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserMatchDescriptionViewModel.class);
        // TODO: Use the ViewModel
    }

}
