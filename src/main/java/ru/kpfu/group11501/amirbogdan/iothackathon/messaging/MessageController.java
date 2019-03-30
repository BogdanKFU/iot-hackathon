package ru.kpfu.group11501.amirbogdan.iothackathon.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.kpfu.group11501.amirbogdan.iothackathon.Coordinate;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/coordinates")
    public void send(Coordinate coordinate) {
        template.convertAndSend("/topic/coordinates", coordinate);
    }
}