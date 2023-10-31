package com.study.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // WebSocket 메시지 처리를 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 메세지 브로커를 구성한다.
     * 메세지 브로커는 클라이언트와 서버간의 메세지 교환을 조정하는 역할을 한다.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // /topic 으로 시작하는 목적지에 대한 메세지를 클라이언트로 전달한다.
        config.setApplicationDestinationPrefixes("/app"); // /app 접두사를 @MessageMapping이 적용된 메서드와 연결한다. ex) /app/hello는 GreetingController.greeting() 메서드가 처리하는 엔드포인트
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket"); // gs-guide-websocket 엔드포인트를 WebSocket 연결을 위해 등록. 클라이언트는 이 엔드포인트를 사용하여 WebSocket 연결을 설정하고 서버와 통신할 수 있다.
    }
}
