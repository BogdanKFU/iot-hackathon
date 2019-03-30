package com.tutorialspoint.websocketapp;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @MessageMapping("/hello")
    @SendTo("/topic/coordinates")
    public void send(String name) {

    }
}