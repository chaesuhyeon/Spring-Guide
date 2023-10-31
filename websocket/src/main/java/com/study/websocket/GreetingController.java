package com.study.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello") // 메세지가 /hello 목적지로 보내진 경우 greeting()가 호출 되도록 보장 한다. (클라이언트에서 /hello로 메세지를 보내면 이 메서드가 호출된다)
    @SendTo("/topic/greetings") // 반환 값은 @SendTo 어노테이션에 지정된 /topic/greetings 에 대한 모든 구독자에게 브로드 캐스트 된다.
    public Greeting greeting(HelloMessage message) throws Exception { // 클라이언트가 보낸 메시지의 내용을 HelloMassage 객체로 변환하여 처리한다.
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
