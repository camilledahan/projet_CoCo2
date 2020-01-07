package fr.cocoteam.co2co2.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.service.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:3000";
    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

       Button button = view.findViewById(R.id.sendInfos);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getInfo();
           }
       });
        return view;
    }
    public void getInfo(){


        Call<String> call = retrofitInterface.executeUser();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {

                    Toast.makeText(getActivity(),response.body(),Toast.LENGTH_SHORT).show();



                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
