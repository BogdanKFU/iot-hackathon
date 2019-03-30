package ru.kpfu.group11501.amirbogdan.iothackathon;

import jdk.nashorn.internal.parser.JSONParser;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import ru.kpfu.group11501.amirbogdan.iothackathon.config.HiveMqConfig;
import ru.kpfu.group11501.amirbogdan.iothackathon.config.PropertiesHolder;
import ru.kpfu.group11501.amirbogdan.iothackathon.messaging.MessageCallbackHandler;

import java.util.Properties;

public class Main {
    public static void main(String[] args) throws MqttException {
        final Properties prop = PropertiesHolder.getProperties();
        final String queue = prop.getProperty("queue");
        MqttClient mqttClient = HiveMqConfig.getClient();
        mqttClient.subscribe(queue);



        MessageCallbackHandler.addMessageCallback((topic, message)->{
            if (topic.equals(queue)){
                String result = new String(message.getPayload());
                System.out.println(result);
                JSONObject object = new JSONObject(result);
                //String id = object.getJSONObject("")
            }
        });
    }
}
