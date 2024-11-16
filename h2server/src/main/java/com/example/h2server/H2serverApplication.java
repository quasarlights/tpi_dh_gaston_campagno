package com.example.h2server;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.SQLException;

@SpringBootApplication
public class H2serverApplication {

	public static void main(String[] args) {
		SpringApplication.run(H2serverApplication.class, args);
	}

	@PostConstruct
	public void startH2Server() throws SQLException {
		// Arrancar el servidor H2 en un hilo separado
		new Thread(() -> {
			try {
				Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
				server.start();
				System.out.println("H2 TCP Server started...");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
