package fr.cocoteam.co2co2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.model.UserMatch;
import fr.cocoteam.co2co2.view.SplashScreenFragment;

public class MatchUserRecyclerViewAdapter extends RecyclerView.Adapter<MatchUserRecyclerViewAdapter.UserMatchViewHolder> {


    List<UserMatch> matchUsers;

    OnHeadlineSelectedListener callback;

    public void setOnHeadlineSelectedListener(OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }

    public MatchUserRecyclerViewAdapter(List<UserMatch> matchUsers){
        this.matchUsers = matchUsers;
    }

    @Override
    public UserMatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_match_item, parent, false);

        return new UserMatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserMatchViewHolder holder, int position) {

        UserMatch userMatch = matchUsers.get(position);
        holder.bind(userMatch);

        holder.matchName.setText(matchUsers.get(position).getName());
        holder.matchTrip.setText(matchUsers.get(position).getTrip().getDepart() + " à " + matchUsers.get(position).getTrip().getArrivee());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean expanded = userMatch.isExpanded();
                userMatch.setExpanded(!expanded);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.matchUsers.size();
    }

    public static class UserMatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView matchName;
        private TextView matchTrip;
        private LinearLayout subItem;

        public UserMatchViewHolder(View itemView) {
            super(itemView);
            this.matchName = itemView.findViewById(R.id.match_name);
            this.matchTrip = itemView.findViewById(R.id.match_trip);
            this.subItem = itemView.findViewById(R.id.sub_item);
        }

        private void bind(UserMatch userMatch){
            boolean expanded = userMatch.isExpanded();

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

            //genre.setText("Genre: " + movie.getGenre());
            //year.setText("Year: " + movie.getYear());
        }

        @Override
        public void onClick(View view) {
            Log.i("Match clicked",view.toString());
        }
    }

    public interface OnHeadlineSelectedListener {
        void onItemClicked(UserMatch userMatch);
    }

}
