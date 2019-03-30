package ru.kpfu.group11501.amirbogdan.iothackathon.config;

import org.eclipse.paho.client.mqttv3.*;
import ru.kpfu.group11501.amirbogdan.iothackathon.messaging.MessageCallbackHandler;


public class HiveMqConfig {

    private static MqttClient client;

    static {
        connect();
        setCallback();
    }

    private static void connect() {
        String uri = PropertiesHolder.getProperties().getProperty("uri");
        try {
            client = new MqttClient(
                    uri,
                    MqttClient.generateClientId());
            client.connect();
        } catch (MqttException e) {
            System.out.println("Can't connect to broker");
            throw new IllegalStateException("Can't connect to broker");
        }
    }

    private static void setCallback() {
        client.setCallback(new MqttCallback() {

            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connection with broker lost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                MessageCallbackHandler.getMessageCallbacks()
                        .forEach(callback -> {
                            try {
                                callback.processMessage(topic, message);
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }
                        });
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
    }


    public static MqttClient getClient() {
        return client;
    }
}
