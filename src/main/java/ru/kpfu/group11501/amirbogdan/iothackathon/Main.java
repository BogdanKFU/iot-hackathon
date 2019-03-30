package ru.kpfu.group11501.amirbogdan.iothackathon;

import jdk.nashorn.internal.parser.JSONParser;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import ru.kpfu.group11501.amirbogdan.iothackathon.config.HiveMqConfig;
import ru.kpfu.group11501.amirbogdan.iothackathon.config.PropertiesHolder;
import ru.kpfu.group11501.amirbogdan.iothackathon.messaging.MessageCallbackHandler;
import ru.kpfu.group11501.amirbogdan.iothackathon.messaging.MyStompSessionHandler;

import java.util.ArrayList;
import java.util.List;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class Main {

    public static void main(String[] args) {
        final Properties prop = PropertiesHolder.getProperties();
        final String queue1 = prop.getProperty("queue1");
        final String queue2 = prop.getProperty("queue2");
        final String queue3 = prop.getProperty("queue3");
       /*
        MqttClient mqttClient = HiveMqConfig.getClient();

        mqttClient.subscribe(queue1);
        mqttClient.subscribe(queue2);
        mqttClient.subscribe(queue3);
        Map<String, ArrayList<Double>> messages = new HashMap();
        */
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        WebSocketClient transport = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(transport);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        try {
            StompSession session = stompClient.connect("ws://localhost:8080/iot-data/", new MyStompSessionHandler()).get();
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                Coordinate coordinate = new Coordinate(random.nextInt(4), random.nextInt(7));
                session.send("/app/coordinates", coordinate);
                Thread.sleep(2000);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        /*
        MessageCallbackHandler.addMessageCallback((topic, message) -> {
            String id = "";
            if (topic.equals(queue1) || topic.equals(queue2) || topic.equals(queue3)) {
                String result = new String(message.getPayload());
                JSONObject object = new JSONObject(result);
                id = object.getString("tagPackId");
                ArrayList<Double> list;
                if (messages.containsKey(id)) {
                    list = messages.get(id);
                } else {
                    list = new ArrayList<Double>();
                    list.add((double) 0);
                    list.add((double) 0);
                    list.add((double) 0);

                }
                if (topic.equals(queue1)) {
                    list.set(0, Double.parseDouble(object.getString("distance")));
                } else if (topic.equals(queue2)) {
                    list.set(1, Double.parseDouble(object.getString("distance")));
                } else if (topic.equals(queue3)) {
                    list.set(2, Double.parseDouble(object.getString("distance")));
                }
                messages.put(id, list);
            }
            if (!messages.get(id).contains((double) 0)) {
                ArrayList<Double> list = messages.get(id);
                Coordinate coordinate = Util.getCoordinate(list.get(0), list.get(1), list.get(2));
                coordinate.setX(Math.abs(coordinate.getX()));
                coordinate.setY(Math.abs(coordinate.getY()));
                messages.remove(id);
                System.out.println(coordinate);

            }
            session.send("/app/coordinates", message);
        });
        */
    }
}