package fr.cocoteam.co2co2.utils;

import android.util.Log;

import java.net.URLConnection;
import java.net.URISyntaxException;
import java.nio.channels.Channel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class AmqpProtocol {

    private ConnexionFactory factory = new ConnexionFactory();
    private BlockingDeque queue = new LinkedBlockingDeque();


    private void setupConnectionFactory() {
        String uri = "CLOUDAMQP_URL";
        try {
            factory.setAutomaticRecoveryEnabled(false);
            factory.setUri(uri);
        } catch (KeyManagementException | NoSuchAlgorithmException | URISyntaxException e1) {
            e1.printStackTrace();
        }



    public void publishMessage(String message) {
        try {
            queue.putLast(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

        public void publishToAMQP()
        {
            publishThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        try {
                            Connection connection = factory.newConnection();
                            Channel ch = connection.createChannel();
                            ch.confirmSelect();

                            while (true) {
                                String message = queue.takeFirst();
                                try{
                                    ch.basicPublish("amq.fanout", "chat", null, message.getBytes());
                                    ch.waitForConfirmsOrDie();
                                } catch (Exception e){
                                    queue.putFirst(message);
                                    throw e;
                                }
                            }
                        } catch (InterruptedException e) {
                            break;
                        } catch (Exception e) {
                            Log.d("", "Connection broken: " + e.getClass().getName());
                            try {
                                Thread.sleep(5000); //sleep and then try again
                            } catch (InterruptedException e1) {
                                break;
                            }
                        }
                    }
                }
            });
            publishThread.start();
        }
}
