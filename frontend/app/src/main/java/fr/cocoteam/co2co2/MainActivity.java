package fr.cocoteam.co2co2;


import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


/*import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
*/
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.URISyntaxException;

import fr.cocoteam.co2co2.view.ContactFragment;
import fr.cocoteam.co2co2.view.FindCarFragment;
import fr.cocoteam.co2co2.view.MapFragment;
import fr.cocoteam.co2co2.view.ProfilFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    //private MQTT mqtt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        //connectMQTT();
        String messageString = "Hello World from Java!";

        System.out.println("== START PUBLISHER ==");


       MqttClient client = null;
        try {
            client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId(), new MemoryPersistence());

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            client.connect(connOpts);
            MqttMessage message = new MqttMessage();
            message.setPayload(messageString.getBytes());

            client.publish("iot_data", message);

            System.out.println("\tMessage '"+ messageString +"' to 'iot_data'");

        } catch (MqttException e) {
            e.printStackTrace();

        }

        System.out.println("== END PUBLISHER ==");
    }

    private boolean loadFragment (Fragment fragment){
        if (fragment != null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }

/*    private void connectMQTT(){

        this.mqtt = new MQTT();
        try {
            mqtt.setHost("localhost:1883");
        } catch(URISyntaxException e){
                e.printStackTrace();
                toast("Uri Exception");
        }
        FutureConnection connection = mqtt.futureConnection();
        connection.connect();
        String message = "test_message";
        connection.publish("test/topic",message.getBytes(), QoS.AT_LEAST_ONCE, true);
        //toast("Connected");
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.nav_map:
                fragment = new MapFragment();
                break;

            case R.id.nav_contact:
                fragment = new ContactFragment();
                break;

            case R.id.nav_find_car:
                fragment = new FindCarFragment();
                break;

            case R.id.nav_contract:
                fragment = new ContactFragment();

                break;

            case R.id.nav_profil:
                fragment = new ProfilFragment();
                break;
        }

        return  loadFragment(fragment);
    }


    private void toast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
