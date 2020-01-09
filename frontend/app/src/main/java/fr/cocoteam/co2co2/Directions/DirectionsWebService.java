package fr.cocoteam.co2co2.Directions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import fr.cocoteam.co2co2.Listener.DirectionsServiceListener;

public class DirectionsService extends Thread {

    private static final String DISTANCE_MATRIX_DIRECTION_URL = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GOOGLE_DIRECTION_API_KEY = "AIzaSyBGo7-H0aIO_KTtZGGez8vPxuShRHeV8AA";
    private String origin;
    private String  destination;
    private DirectionsServiceListener listener;
    String direction;

    public DirectionsService(DirectionsServiceListener listener,String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
        this.listener= listener;
    }

    public String createURl(String origin, String destination){
        return DISTANCE_MATRIX_DIRECTION_URL + "origin=" + origin + "&destination=" + destination + "&key=" + GOOGLE_DIRECTION_API_KEY;

    }

    public void run(){
        try {
            URL url = new URL(createURl(origin,destination));
            InputStream is = url.openConnection().getInputStream();
            getDirection(is);
            this.listener.DirectionsServiceStart(direction);
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }

    }
public void getDirection(InputStream is){


    try {
        InputStreamReader isr = new InputStreamReader(is);
        StringBuilder buffer = new StringBuilder();
        BufferedReader br = new BufferedReader(isr);

        String data ;
        while ((data =br.readLine())!=null){
            buffer.append(data).append("\n");
        }

        is.close();

        direction = buffer.toString();
    } catch (IOException e) {
        e.printStackTrace();
    }

}

}
