package fr.cocoteam.co2co2.view;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.adapter.MatchUserRecyclerViewAdapter;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.model.UserMatch;
import fr.cocoteam.co2co2.viewmodel.FindCarViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindCarFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FindCarViewModel findCarViewModel;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MatchUserRecyclerViewAdapter matchUserRecyclerViewAdapter;
    public List<UserMatch> userMatches = new ArrayList<>();
    private TextView noMatchFoundTextView;

    public FindCarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_car, container, false);

        //VIEWMODEL
        findCarViewModel = ViewModelProviders.of(getActivity()).get(FindCarViewModel.class);

        //BIND
        recyclerView = view.findViewById(R.id.matchRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        matchUserRecyclerViewAdapter = new MatchUserRecyclerViewAdapter(userMatches, findCarViewModel);
        recyclerView.setAdapter(matchUserRecyclerViewAdapter);

        noMatchFoundTextView = view.findViewById(R.id.noMatchFoundTextView);

        // SwipeRefreshLayout
        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        //observe User mutableLiveData
        Observer<List<UserMatch>> currentUserMatchObserver = matches -> {
            if (matches!=null){
                userMatches.clear();
                userMatches.addAll(matches);
                matchUserRecyclerViewAdapter.notifyDataSetChanged();
                updateUI(matches);
            }

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(),"Matches refreshed.",Toast.LENGTH_LONG).show();
            }

        };

        findCarViewModel.getCurrentUserMatch().observe(this,currentUserMatchObserver);

        //Get matches
        findCarViewModel.getAllMatches();

        return view;
    }

    @Override
    public void onRefresh() {
        findCarViewModel.getAllMatches();
    }


    private void updateUI(List<UserMatch> users){
        if(users.size()==0){
            noMatchFoundTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        else {
            noMatchFoundTextView.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
