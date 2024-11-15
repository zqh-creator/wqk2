package com.example.demo;


import com.example.demo.websocketAchieve.WebSocketConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@MapperScan("com.example.demo.mybatis_for_chat.mapper")
@EnableWebSocket
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

}
