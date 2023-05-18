package com.example.gatling.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    /**
     * 서버가 1대인 경우, 외부 메시지 브로커 없이 간단하게 구현할 때 사용한다.
     * Gatling WebSocket 부하 테스트를 위한 깡통 서버이므로 해당 메소드를 오버라이드하여 심플하게 구현한다.
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");     // 클라이언트 구독 주소 Prefix
        registry.setApplicationDestinationPrefixes("/app");         // 서버 발행 주소 Prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/connect")
                .setAllowedOrigins("*");
    }
}