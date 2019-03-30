package ru.kpfu.group11501.amirbogdan.iothackathon.messaging;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @MessageMapping("/hello")
    @SendTo("/topic/coordinates")
    public String send(String name) {
        return "HELLO!";
    }
}