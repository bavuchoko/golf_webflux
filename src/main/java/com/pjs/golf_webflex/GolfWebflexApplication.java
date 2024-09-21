package com.pjs.golf_webflex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class GolfWebflexApplication {

	public static void main(String[] args) {
		BlockHound.builder()
				.install();
		SpringApplication.run(GolfWebflexApplication.class, args);
	}

}
