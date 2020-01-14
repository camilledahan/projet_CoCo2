package fr.cocoteam.co2co2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.cocoteam.co2co2.R;

public class PaymentFragment extends Fragment implements View.OnClickListener {

    public android.widget.Button Button;

    PaymentFragment.OnHeadlineSelectedListener callback;

    public void setOnHeadlineSelectedListener(PaymentFragment.OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);


        android.widget.Button button1 = view.findViewById(R.id.imageButtonback1);

        //set listeners
        button1.setOnClickListener(this);


        return view;

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButtonback1:
                callback.onPaymentOptionSelected("back");
                break;

        }

    }


    public interface OnHeadlineSelectedListener {
        boolean onPaymentOptionSelected(String classe);
    }


}

