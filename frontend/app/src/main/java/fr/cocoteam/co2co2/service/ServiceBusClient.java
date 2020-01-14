package fr.cocoteam.co2co2.service;

// Include the following imports to use Service Bus APIs

import com.google.gson.GsonBuilder;

import com.microsoft.azure.servicebus.*;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.google.gson.Gson;

import static java.nio.charset.StandardCharsets.*;

import java.util.*;


public class ServiceBusClient {


    private final String connexionString = "Endpoint=sb://busprojetmajeur.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=CD1OBVNN2ekcGpN/FwfGo9COr+qxCP0Sf+Uj580adr4=";
    private final String queueName = "main_queue";

    private Gson GSON = new GsonBuilder()
            .setLenient()
            .create();

    public ServiceBusClient() {
    }

    public void run() throws Exception {
        // Create a QueueClient instance and then asynchronously send messages.
        // Close the sender once the send operation is complete.
        QueueClient sendClient = new QueueClient(new ConnectionStringBuilder(connexionString, queueName), ReceiveMode.PEEKLOCK);
        this.sendMessagesAsync(sendClient);

        sendClient.close();
    }

    public void sendMessagesAsync(QueueClient sendClient) {

        String msg = "{'name' = 'android', 'firstName' = 'coucou'}";

            final String messageId = "0";
            Message message = new Message(GSON.toJson(msg, Map.class).getBytes(UTF_8));
            message.setContentType("application/json");
            message.setLabel("mobile");
            message.setMessageId(messageId);
            //message.setTimeToLive(Duration.ofMinutes(2));
            sendClient.sendAsync(message).thenRunAsync(() -> {
                System.out.printf("\n\tMessage acknowledged: Id = %s", message.getMessageId());
                sendClient.closeAsync();
            });




    }

}
