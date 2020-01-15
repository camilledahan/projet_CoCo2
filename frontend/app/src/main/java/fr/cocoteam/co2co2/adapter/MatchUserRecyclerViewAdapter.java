package fr.cocoteam.co2co2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.model.UserMatch;
import fr.cocoteam.co2co2.view.SplashScreenFragment;
import fr.cocoteam.co2co2.viewmodel.FindCarViewModel;

public class MatchUserRecyclerViewAdapter extends RecyclerView.Adapter<MatchUserRecyclerViewAdapter.UserMatchViewHolder> {


    List<UserMatch> matchUsers;

    OnHeadlineSelectedListener callback;
    FindCarViewModel findCarViewModel;

    public void setOnHeadlineSelectedListener(OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }

    public MatchUserRecyclerViewAdapter(List<UserMatch> matchUsers, FindCarViewModel findCarViewModel){
        this.matchUsers = matchUsers;
        this.findCarViewModel = findCarViewModel;
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

        holder.matchName.setText(userMatch.getName());
        //TODO Get trip with adress
        holder.matchTrip.setText(userMatch.getTrip().getDepart() + " à " );
        holder.horraireTrip.setText(userMatch.getTrip().getHeure());
        holder.matchDescription.setText(userMatch.getDescription());
        holder.matchAge.setText(String.valueOf(userMatch.getAge()) + " ans");
        holder.likeMatchSwitcher.setOnClickListener(view -> {
            if(userMatch.getAdded()){
                 holder.likeMatchSwitcher.setImageResource(R.drawable.add_match_icon);
                 userMatch.setAdded(false);

            } else {
                 holder.likeMatchSwitcher.setImageResource(R.drawable.like);
                 userMatch.setAdded(true);
            }
             findCarViewModel.updateMatchStatus(userMatch);
            findCarViewModel.getFriends();
        });

        holder.itemView.setOnClickListener(view -> {
            boolean expanded = userMatch.isExpanded();
            userMatch.setExpanded(!expanded);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return this.matchUsers.size();
    }

    public static class UserMatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView matchName;
        private TextView matchTrip;
        private TextView horraireTrip;
        private ImageView likeMatchSwitcher;
        //TODO profile image
        // private ImageView matchProfilmage;
        private TextView matchAge;
        //TODO rating bar
        // private RatingBar ratingBar;
        private TextView matchDescription;
        //TODO image view for match preference
        // private ImageView smokingImage;
        // private ImageView detourImage;
        // private ImageView driveImage;

        private LinearLayout subItem;

        public UserMatchViewHolder(View itemView) {
            super(itemView);
            this.matchName = itemView.findViewById(R.id.match_name);
            this.matchTrip = itemView.findViewById(R.id.match_trip);
            this.horraireTrip = itemView.findViewById(R.id.match_horraire);
            this.likeMatchSwitcher = itemView.findViewById(R.id.like_image_switcher);
            this.matchAge = itemView.findViewById(R.id.match_age);
            this.matchDescription = itemView.findViewById(R.id.match_description);
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
