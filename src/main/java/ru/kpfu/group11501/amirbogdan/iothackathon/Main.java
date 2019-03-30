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
import java.util.concurrent.ExecutionException;

@Component
public class Main {

    public static void main(String[] args) throws MqttException, ExecutionException, InterruptedException {
        final Properties prop = PropertiesHolder.getProperties();
        final String queue = prop.getProperty("queue");
        MqttClient mqttClient = HiveMqConfig.getClient();
        mqttClient.subscribe(queue);

        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport( new StandardWebSocketClient()) );
        WebSocketClient transport = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(transport);

        StompSession session = stompClient.connect("ws://localhost:8080/coordinates/", new MyStompSessionHandler()).get();

        session.send("/app/coordinates", );
        MessageCallbackHandler.addMessageCallback((topic, message) -> {
            if (topic.equals(queue)) {
                String result = new String(message.getPayload());
                System.out.println(result);
                session.send("/app/coordinates", new String(message.getPayload()));
            }
        });
    }
}