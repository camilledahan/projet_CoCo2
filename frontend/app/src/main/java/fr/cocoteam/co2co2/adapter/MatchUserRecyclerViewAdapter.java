package fr.cocoteam.co2co2.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.model.User;
import fr.cocoteam.co2co2.model.UserMatch;
import fr.cocoteam.co2co2.view.MapFragment;
import fr.cocoteam.co2co2.view.SplashScreenFragment;
import fr.cocoteam.co2co2.viewmodel.FindCarViewModel;

import static com.android.volley.VolleyLog.TAG;

public class MatchUserRecyclerViewAdapter extends RecyclerView.Adapter<MatchUserRecyclerViewAdapter.UserMatchViewHolder> {


    List<UserMatch> matchUsers;

    OnHeadlineSelectedListener callback;
    FindCarViewModel findCarViewModel;
    GoogleSignInAccount acct;

    public void setOnHeadlineSelectedListener(OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }

    public MatchUserRecyclerViewAdapter(List<UserMatch> matchUsers, FindCarViewModel findCarViewModel, GoogleSignInAccount acct){
        this.matchUsers = matchUsers;
        this.findCarViewModel = findCarViewModel;
        this.acct=acct;
    }

    @Override
    public UserMatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_match_item, parent, false);

        return new UserMatchViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(UserMatchViewHolder holder, int position) {

        UserMatch userMatch = matchUsers.get(position);
        holder.bind(userMatch);

        holder.matchName.setText(userMatch.getName());
        holder.matchTrip.setText(userMatch.getTrip().getDepart() + " à " +userMatch.getTrip().getArrivee());
        holder.horraireTrip.setText(userMatch.getTrip().getHeure());
        holder.matchDescription.setText(userMatch.getDescription());
        holder.matchAge.setText(String.valueOf(userMatch.getAge()) + " ans");
        if(userMatch.getMe_has_accepted_user()==1){
            holder.likeMatchSwitcher.setImageResource(R.drawable.like);
        } else {
            holder.likeMatchSwitcher.setImageResource(R.drawable.add_match_icon);
        }
        holder.likeMatchSwitcher.setOnClickListener(view -> {
            if(userMatch.getMe_has_accepted_user()==1){
                 holder.likeMatchSwitcher.setImageResource(R.drawable.add_match_icon);
                 userMatch.setMe_has_accepted_user(0);

            } else {
                 holder.likeMatchSwitcher.setImageResource(R.drawable.like);
                 userMatch.setMe_has_accepted_user(1);
            }
            findCarViewModel.updateMatchStatus(userMatch,acct.getEmail());
            //findCarViewModel.getFriends();
        });

        holder.itemView.setOnClickListener(view -> {
            boolean expanded = userMatch.isExpanded();
            userMatch.setExpanded(!expanded);
            notifyItemChanged(position);
        });
       if(holder.mMap!=null){

           holder.mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
               @Override
               public void onMapLoaded() {
                   LatLng latLngOrigin= stringToLatLng(userMatch.getTrip().getCoords_arr());
                   LatLng latLngDepart =stringToLatLng(userMatch.getTrip().getCoords_dep());
                   holder.mMap.addMarker(new MarkerOptions().position(latLngOrigin).icon(BitmapDescriptorFactory.fromResource(R.drawable.travail)));
                   holder.mMap.addMarker(new MarkerOptions().position(latLngDepart).icon(BitmapDescriptorFactory.fromResource(R.drawable.home)));


                   holder.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngDepart,11));
               }
           });

        }



    }

    private LatLng stringToLatLng(String coordString){
        String[] lat = coordString.split(",");
        Double latitude= Double.valueOf(lat[0]);
        Double longitude= new Double(lat[1]);
        return new LatLng(latitude,longitude);
    }
    @Override
    public int getItemCount() {
        return this.matchUsers.size();
    }

    public static class UserMatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, OnMapReadyCallback {

        private TextView matchName;
        private TextView matchTrip;
        private TextView horraireTrip;
        private ImageView likeMatchSwitcher;
        private MapView mapView;
        private GoogleMap mMap;
        private Context context;
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

        public UserMatchViewHolder(View itemView, Context context) {
            super(itemView);
            this.matchName = itemView.findViewById(R.id.match_name);
            this.matchTrip = itemView.findViewById(R.id.match_trip);
            this.horraireTrip = itemView.findViewById(R.id.match_horraire);
            this.likeMatchSwitcher = itemView.findViewById(R.id.like_image_switcher);
            this.matchAge = itemView.findViewById(R.id.match_age);
            this.matchDescription = itemView.findViewById(R.id.match_description);
            this.subItem = itemView.findViewById(R.id.sub_item);
            this.mapView =itemView.findViewById(R.id.map_match);
            this.context=context;
            if(mapView!=null){
                mapView.onCreate(null);
                mapView.onResume();
                mapView.getMapAsync(this);
            }

        }

        private void bind(UserMatch userMatch){
            boolean expanded = userMatch.isExpanded();

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

        }

        @Override
        public void onClick(View view) {
            Log.i("Match clicked",view.toString());
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(context);
            mMap=googleMap;
           mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
           mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.76,4.83),13));




        }
    }

    public interface OnHeadlineSelectedListener {
        void onItemClicked(UserMatch userMatch);
    }

}
