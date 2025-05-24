package com.deepakraj.cofigurationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class 	CofigurationserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(CofigurationserverApplication.class, args);
	}

}
