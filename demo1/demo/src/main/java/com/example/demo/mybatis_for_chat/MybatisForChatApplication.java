//package com.example.demo.mybatis_for_chat;
//
//import com.example.demo.websocketAchieve.WebSocketConfig;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@SpringBootApplication
//@MapperScan("com.example.demo.mybatis_for_chat")
//@ComponentScan(basePackages = {"com.example.demo.mybatis_for_chat"})
//public class MybatisForChatApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(MybatisForChatApplication.class, args);
//		WebSocketConfig webSocketConfig=new WebSocketConfig();
//	}
//	@Bean
//	public CommandLineRunner testDatabaseConnection(DataSource dataSource) {
//		return args -> {
//			try (Connection connection = dataSource.getConnection()) {
//				if (connection!= null) {
//					System.out.println("数据库连接成功！");
//					// 这里也可以添加一些其他的数据库操作来进一步验证连接，比如执行简单查询等
//				}
//			} catch (SQLException e) {
//				System.out.println("数据库连接失败，请检查配置或网络等问题。");
//				e.printStackTrace();
//			}
//		};
//	}
//
//}
