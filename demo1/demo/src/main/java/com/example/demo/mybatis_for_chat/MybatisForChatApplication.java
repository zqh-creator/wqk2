package com.example.demo.mybatis_for_chat;

import com.example.demo.websocketAchieve.WebSocketConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mybatis_for_chat")
public class MybatisForChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisForChatApplication.class, args);
		WebSocketConfig webSocketConfig=new WebSocketConfig();
	}

}
