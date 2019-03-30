package ru.kpfu.group11501.amirbogdan.iothackathon.messaging;

import java.util.ArrayList;
import java.util.List;

public class MessageCallbackHandler {
    private static List<MessageCallback> messageCallbacks = new ArrayList<>();

    public static List<MessageCallback> getMessageCallbacks() {
        return messageCallbacks;
    }

    public static void addMessageCallback(MessageCallback callback) {
        messageCallbacks.add(callback);
    }
}
