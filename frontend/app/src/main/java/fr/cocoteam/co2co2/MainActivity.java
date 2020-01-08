package fr.cocoteam.co2co2;


import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


/*import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
*/
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Enumeration;

import fr.cocoteam.co2co2.util.MqttHelper;
import fr.cocoteam.co2co2.view.ContactFragment;
import fr.cocoteam.co2co2.view.FindCarFragment;
import fr.cocoteam.co2co2.view.MapFragment;
import fr.cocoteam.co2co2.view.ProfilFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    MqttHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

            String[] permissions = {
                    android.Manifest.permission.INTERNET,
                    android.Manifest.permission.ACCESS_NETWORK_STATE,
                    android.Manifest.permission.ACCESS_WIFI_STATE,
                    android.Manifest.permission.WAKE_LOCK
            };

            ActivityCompat.requestPermissions(this, permissions,0);



        //connectMQTT();
        String messageString = "Hello World from Java!";

        System.out.println("== START PUBLISHER ==");

        startMqtt();



        /*try {
            mqttAndroidClient = new MqttAndroidClient(this, serverUri, clientId, new MqttClientPersistence() {
                @Override
                public void open(String clientId, String serverURI) throws MqttPersistenceException {

                }

                @Override
                public void close() throws MqttPersistenceException {

                }

                @Override
                public void put(String key, MqttPersistable persistable) throws MqttPersistenceException {

                }

                @Override
                public MqttPersistable get(String key) throws MqttPersistenceException {
                    return null;
                }

                @Override
                public void remove(String key) throws MqttPersistenceException {

                }

                @Override
                public Enumeration keys() throws MqttPersistenceException {
                    return null;
                }

                @Override
                public void clear() throws MqttPersistenceException {

                }

                @Override
                public boolean containsKey(String key) throws MqttPersistenceException {
                    return false;
                }
            });


            MqttConnectOptions connOpts = new MqttConnectOptions();

            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            connOpts.setKeepAliveInterval(30);
            connOpts.setCleanSession(true);

            mqttClient.connect(connOpts);

            /* MqttMessage message = new MqttMessage();
            message.setPayload(messageString.getBytes());
            mqttClient.publish(subscriptionTopic, message);
*/
            /*try {
                mqttClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w("Mqtt","Subscribed!");
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w("Mqtt", "Subscribed fail!");
                    }
                });

            } catch (MqttException ex) {
                System.err.println("Exceptionst subscribing");
                ex.printStackTrace();
            }

            System.out.println("\tMessage '"+ messageString +"' to 'iot_data'");

        } catch (MqttException e) {
            e.printStackTrace();

        } */

        System.out.println("== END PUBLISHER ==");
    }

    private void startMqtt(){
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug",mqttMessage.toString());
                //dataReceived.setText(mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
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
