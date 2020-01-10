package fr.cocoteam.co2co2.view;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.adapter.MatchUserRecyclerViewAdapter;
import fr.cocoteam.co2co2.viewmodel.FindCarViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindCarFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FindCarViewModel findCarViewModel;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

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
        recyclerView.setAdapter(new MatchUserRecyclerViewAdapter(findCarViewModel.generateRandomUser(40)));

        // SwipeRefreshLayout
        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onRefresh() {
        //TODO
    }
}
