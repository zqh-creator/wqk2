package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration//注释的类变成配置类
@EnableWebSocket//实现webSocket连接的开关
// WebSocketConfigurer在spring Websocket框架中是一个接口，用于配置websocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
// registerWebSocketHandlers是接口中实现注册服务器中websocket端点的方法
//WebSocketHandlerRegistry是一个管理websocket端点和处理器的注册中心
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //为注册的websocket端点定义指定的路径和websocket处理器，websocket端点的本质是一个URL路径，假设服务器地址为端口8080，websocket端点的路径配置为“/chat”
        registry.addHandler(new WebSocketHandlerImpl(),"/chat").setAllowedOrigins("*");
    }
}
