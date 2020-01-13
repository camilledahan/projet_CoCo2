package fr.cocoteam.co2co2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.model.UserMatch;

public class MatchUserRecyclerViewAdapter extends RecyclerView.Adapter<MatchUserRecyclerViewAdapter.UserMatchViewHolder> {


    List<UserMatch> matchUsers;

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

        /*
        // update MyCustomEditTextListener every time we bind a new item
        // so that it knows what item in mDataset to update
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.mEditText.setText(mDataset[holder.getAdapterPosition()]);
         */

        holder.matchName.setText(matchUsers.get(position).getName());
        holder.matchTrip.setText(matchUsers.get(position).getTrip().toString());
    }

    @Override
    public int getItemCount() {
        return this.matchUsers.size();
    }

    public static class UserMatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView matchName;
        private TextView matchTrip;

        public UserMatchViewHolder(View itemView) {
            super(itemView);
            this.matchName = itemView.findViewById(R.id.match_name);
            this.matchTrip = itemView.findViewById(R.id.match_trip);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
