package fr.cocoteam.co2co2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.MapView;

import java.util.List;

import fr.cocoteam.co2co2.R;
import fr.cocoteam.co2co2.model.Agreement;
import fr.cocoteam.co2co2.model.UserMatch;

public class CarPooRecyclerViewAdapter extends RecyclerView.Adapter<CarPooRecyclerViewAdapter.CarPoolViewHolder>{

    List<Agreement> agreements;




    @NonNull
    @Override
    public CarPoolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.agreement_item, parent, false);

        return new CarPoolViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull CarPoolViewHolder holder, int position) {

        Agreement agreement = agreements.get(position);

        holder.title.setText(agreement.getTrip().getDepart());
        holder.status.setText(agreement.getStatus());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class CarPoolViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private CheckBox checkMonday;
        private CheckBox checkTusday;
        private CheckBox checkWednesday;
        private CheckBox checkThursday;
        private CheckBox checkFriday;
        private TextView status;
        private MapView mapview;


        public CarPoolViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.carPoolTextView);
            this.checkMonday = itemView.findViewById(R.id.checkBoxMonday);
            this.checkTusday = itemView.findViewById(R.id.checkBoxTueday);
            this.checkWednesday = itemView.findViewById(R.id.checkBoxWednesday);
            this.checkThursday = itemView.findViewById(R.id.checkBoxThursday);
            this.checkFriday = itemView.findViewById(R.id.checkBoxFriday);
            this.status = itemView.findViewById(R.id.carpool_status);
            this.mapview = itemView.findViewById(R.id.mapView);

        }

        @Override
        public void onClick(View view) {
            Log.i("Match clicked",view.toString());
        }
    }

}
