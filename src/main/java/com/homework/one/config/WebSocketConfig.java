package com.homework.one.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //将"/websocket-example"路径注册为STOMP端点
    //端点的作用——客户端在订阅或发布消息到目的地址前，要连接该端点。
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //setAllowedOrigins()方法表示允许连接的域名，withSockJS()方法表示支持以SockJS方式连接服务器。
        stompEndpointRegistry.addEndpoint("/webSocket-endPoint")
                .setAllowedOrigins("*").withSockJS();
    }

    //消息代理将会处理前缀为"/topic"的消息
    //应用程序以/app为前缀，代理目的地以/topic为前缀
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //定义了一个客户端订阅地址的前缀信息，也就是客户端接收服务端发送消息的前缀信息
        registry.enableSimpleBroker("/topic","/user");
        //定义了服务端接收地址的前缀，也即客户端给服务端发消息的地址前缀
        registry.setApplicationDestinationPrefixes("/app");
        //表示给指定用户发送一对一的主题前缀是"/user"
        registry.setUserDestinationPrefix("/user");
    }

  /*  @Override
    public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(final WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {
                    @Override
                    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
                        // 客户端与服务器端建立连接后，此处记录谁上线了
                        String username = session.getPrincipal().getName();
                        //log.info("online: " + username);
                        System.out.println("online: " + username);
                        super.afterConnectionEstablished(session);
                    }

                    @Override
                    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                        // 客户端与服务器端断开连接后，此处记录谁下线了
                        String username = session.getPrincipal().getName();
                        // log.info("offline: " + username);
                        System.out.println("offline: " + username);
                        super.afterConnectionClosed(session, closeStatus);
                    }
                };
            }
        });
    }*/


}
