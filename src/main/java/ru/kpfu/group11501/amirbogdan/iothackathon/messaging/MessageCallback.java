package ru.kpfu.group11501.amirbogdan.iothackathon.messaging;

import org.eclipse.paho.client.mqttv3.MqttMessage;

@FunctionalInterface
public interface MessageCallback {
    void processMessage(String topic, MqttMessage message);
}
