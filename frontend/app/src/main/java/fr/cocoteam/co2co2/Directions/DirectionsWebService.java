package fr.cocoteam.co2co2.Directions;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fr.cocoteam.co2co2.DirectionModel.Distance;
import fr.cocoteam.co2co2.DirectionModel.Duration;
import fr.cocoteam.co2co2.DirectionModel.Route;
import fr.cocoteam.co2co2.Listener.DirectionsServiceListener;

public class DirectionsWebService  {

    private static final String DISTANCE_MATRIX_DIRECTION_URL = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GOOGLE_DIRECTION_API_KEY = "AIzaSyBGo7-H0aIO_KTtZGGez8vPxuShRHeV8AA";
    private String origin;
    private String  destination;
    private DirectionsServiceListener listener;

    public DirectionsWebService(DirectionsServiceListener listener, String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
        this.listener= listener;
    }

    public String createURl(String origin, String destination){
        return DISTANCE_MATRIX_DIRECTION_URL + "origin=" + origin + "&destination=" + destination + "&key=" + GOOGLE_DIRECTION_API_KEY;

    }

    public void execute()  {
        new DownloadRawData().execute();
    }


    @SuppressLint("StaticFieldLeak")
    private class DownloadRawData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(createURl(origin, destination));
                InputStream is = url.openConnection().getInputStream();
                StringBuilder buffer = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }
                return buffer.toString();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String res) {
            try {
                parseJSon(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }


    }





    private void parseJSon(String data) throws JSONException {
        if (data == null)
            return ;
        List<Route> routes = new ArrayList<>();
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
            Route route = new Route();

            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
            JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
            JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
            JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
            JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");
            route.distance = new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"));
            route.duration = new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"));
            route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
            route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
            route.points = decodePolyLine(overview_polylineJson.getString("points"));

            routes.add(route);
        }
            listener.onDirectionFinderSuccess(routes);
    }

    private List<LatLng> decodePolyLine(final String poly) {
        List<LatLng> decoded = new ArrayList<>();
        try {
            int len = poly.length();
            int index = 0;
            int lat = 0;
            int lng = 0;

            while (index < len) {
                int b;
                int shift = 0;
                int result = 0;
                do {
                    b = poly.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = poly.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                decoded.add(new LatLng(
                        lat / 100000d, lng / 100000d
                ));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return decoded;
    }

}
