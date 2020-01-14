package fr.cocoteam.co2co2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.cocoteam.co2co2.R;

public class PaymentaddcardFragment extends Fragment implements View.OnClickListener{

    public android.widget.Button Button;

    PaymentaddcardFragment.OnHeadlineSelectedListener callback;

    public void setOnHeadlineSelectedListener(PaymentaddcardFragment.OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }

    public static PaymentaddcardFragment newInstance() {
        return new PaymentaddcardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_addcard, container, false);


        android.widget.Button button1 = view.findViewById(R.id.imageButtonback3);
        android.widget.Button button2 = view.findViewById(R.id.button_AddCard);
        //set listeners
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        return view;

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButtonback3:
                callback.onPaymentaddcardOptionSelected("back");
                break;
            case R.id.button_AddCard:
                callback.onPaymentaddcardOptionSelected("addcard");
                break;

        }

    }


    public interface OnHeadlineSelectedListener {
        boolean onPaymentaddcardOptionSelected(String classe);
    }

}
