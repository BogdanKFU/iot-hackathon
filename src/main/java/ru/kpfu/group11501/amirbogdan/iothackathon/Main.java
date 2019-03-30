package ru.kpfu.group11501.amirbogdan.iothackathon;

import jdk.nashorn.internal.parser.JSONParser;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import ru.kpfu.group11501.amirbogdan.iothackathon.config.HiveMqConfig;
import ru.kpfu.group11501.amirbogdan.iothackathon.config.PropertiesHolder;
import ru.kpfu.group11501.amirbogdan.iothackathon.messaging.MessageCallbackHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws MqttException {
        final Properties prop = PropertiesHolder.getProperties();
        final String queue1 = prop.getProperty("queue1");
        final String queue2 = prop.getProperty("queue2");
        final String queue3 = prop.getProperty("queue3");
        MqttClient mqttClient = HiveMqConfig.getClient();
        mqttClient.subscribe(queue1);
        mqttClient.subscribe(queue2);
        mqttClient.subscribe(queue3);
        Map<String, ArrayList<Double>> messages = new HashMap();

        MessageCallbackHandler.addMessageCallback((topic, message)->{
            String id = "";
            if (topic.equals(queue1) || topic.equals(queue2) || topic.equals(queue3)){
                String result = new String(message.getPayload());
                JSONObject object = new JSONObject(result);
                id = object.getString("tagPackId");
                ArrayList<Double> list;
                if (messages.containsKey(id)){
                    list = messages.get(id);
                }
                else {
                    list = new ArrayList<Double>();
                    list.add((double) 0);
                    list.add((double) 0);
                    list.add((double) 0);

                }
                if (topic.equals(queue1)){
                    list.set(0, Double.parseDouble(object.getString("distance")));
                }
                else if (topic.equals(queue2)){
                    list.set(1, Double.parseDouble(object.getString("distance")));
                }
                else if (topic.equals(queue3)){
                    list.set(2, Double.parseDouble(object.getString("distance")));
                }
                messages.put(id, list);
            }
            if (!messages.get(id).contains((double)0)){
                ArrayList<Double> list = messages.get(id);
                Coordinate coordinate = Util.getCoordinate(list.get(0), list.get(1), list.get(2));
                coordinate.setX(Math.abs(coordinate.getX()));
                coordinate.setY(Math.abs(coordinate.getY()));
                messages.remove(id);
                System.out.println(coordinate);
            }
        });
    }
}
