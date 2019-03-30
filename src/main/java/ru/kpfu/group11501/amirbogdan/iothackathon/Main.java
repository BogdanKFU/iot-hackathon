package ru.kpfu.group11501.amirbogdan.iothackathon;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import ru.kpfu.group11501.amirbogdan.iothackathon.config.HiveMqConfig;
import ru.kpfu.group11501.amirbogdan.iothackathon.config.PropertiesHolder;
import ru.kpfu.group11501.amirbogdan.iothackathon.messaging.MessageCallbackHandler;
import ru.kpfu.group11501.amirbogdan.iothackathon.messaging.MyStompSessionHandler;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws MqttException, ExecutionException, InterruptedException {
        final Properties prop = PropertiesHolder.getProperties();
        final String queue = prop.getProperty("queue");
        MqttClient mqttClient = HiveMqConfig.getClient();
        mqttClient.subscribe(queue);

        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession session = stompClient.connect("ws://localhost:9000/coordinates/", new MyStompSessionHandler()).get();
        MessageCallbackHandler.addMessageCallback((topic, message)->{
            if (topic.equals(queue)){
                System.out.println(new String(message.getPayload()));
                session.send("/app/coordinates", new String(message.getPayload()));
            }
        });
    }
}
