package fr.cocoteam.co2co2.view;


import android.nfc.Tag;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.viewmodel.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view = inflater.inflate(R.layout.fragment_contact, container, false);


        return view;
    }

}
