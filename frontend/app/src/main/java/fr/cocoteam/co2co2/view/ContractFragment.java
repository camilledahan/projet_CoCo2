package fr.cocoteam.co2co2.view;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.adapter.CarPooRecyclerViewAdapter;
import fr.cocoteam.co2co2.adapter.MatchUserRecyclerViewAdapter;
import fr.cocoteam.co2co2.viewmodel.ContractViewModel;

public class ContractFragment extends Fragment {

    private ContractViewModel mViewModel;

    private RecyclerView recyclerView;

    public static ContractFragment newInstance() {
        return new ContractFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contract_fragment, container, false);

        recyclerView = view.findViewById(R.id.carPoolRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        CarPooRecyclerViewAdapter carPooRecyclerViewAdapter = new CarPooRecyclerViewAdapter();
        recyclerView.setAdapter(carPooRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ContractViewModel.class);
        // TODO: Use the ViewModel
    }

}
