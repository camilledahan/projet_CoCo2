package fr.cocoteam.co2co2.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.cocoteam.co2co2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindCarFragment extends Fragment {


    public FindCarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_car, container, false);
    }

}
