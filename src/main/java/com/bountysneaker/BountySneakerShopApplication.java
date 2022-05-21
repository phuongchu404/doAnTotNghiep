package com.bountysneaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.bountysneaker"}) 
public class BountySneakerShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BountySneakerShopApplication.class, args);
	}

}
