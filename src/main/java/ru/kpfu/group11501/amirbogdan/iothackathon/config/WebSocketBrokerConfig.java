package ru.kpfu.group11501.amirbogdan.iothackathon.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan("ru.kpfu.group11501.amirbogdan.iothackathon")
public class WebSocketBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
        stompEndpointRegistry.addEndpoint("/coordinates")
                .setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy))
                .setAllowedOrigins("*");
        stompEndpointRegistry.addEndpoint( "/coordinates").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/topic", "/queue");
    }


}

