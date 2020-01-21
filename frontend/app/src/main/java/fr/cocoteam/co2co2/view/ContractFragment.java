package fr.cocoteam.co2co2.view;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.adapter.CarPooRecyclerViewAdapter;
import fr.cocoteam.co2co2.model.Agreement;
import fr.cocoteam.co2co2.viewmodel.ContractViewModel;

public class ContractFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,CarPooRecyclerViewAdapter.OnHeadlineSelectedListener {

    private ContractViewModel mViewModel;

    private TextView noAgreementTextView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CarPooRecyclerViewAdapter carPooRecyclerViewAdapter;
    public List<Agreement> userAgreements = new ArrayList<>();

    public ContractFragment.OnHeadlineSelectedListener callback;

    public void setOnHeadlineSelectedListener(ContractFragment.OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }

    public ContractFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contract, container, false);

        mViewModel = ViewModelProviders.of(getActivity()).get(ContractViewModel.class);

        //BIND
        noAgreementTextView = view.findViewById(R.id.noCarPoolTextView);

        //Refresh layout
        swipeRefreshLayout = view.findViewById(R.id.carPoolRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        //recyclerView
        recyclerView = view.findViewById(R.id.agreementRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        carPooRecyclerViewAdapter = new CarPooRecyclerViewAdapter(userAgreements, mViewModel);
        recyclerView.setAdapter(carPooRecyclerViewAdapter);
        carPooRecyclerViewAdapter.setOnHeadlineSelectedListener(this);

        Observer<List<Agreement>> currentAgreementsObserver = agrs -> {
            if (agrs!=null){
                userAgreements.clear();
                userAgreements.addAll(agrs);
                carPooRecyclerViewAdapter.notifyDataSetChanged();
                updateUI();
            }

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"Pool refreshed.",Toast.LENGTH_LONG).show();
            }
        };

        mViewModel.getCurrentAgreements().observe(this,currentAgreementsObserver);

        //get all agreements
        mViewModel.createFakeAgreements(10);

        return view;
    }

    private void updateUI() {
        if(userAgreements.size()==0){
            noAgreementTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else {
            noAgreementTextView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onRefresh() {
        mViewModel.createFakeAgreements(5);
    }

    @Override
    public void onAgreementSelected() {
        callback.onAgreementSelected();

    }
    public interface OnHeadlineSelectedListener {
        void onAgreementSelected();
    }
}
