package com.example.auth_microservice.boot;

import com.example.auth_microservice.config.ExternalServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Jesús Jiménez Ocaña
 */
@SpringBootApplication(scanBasePackages = "com.example.auth_microservice")
public class AuthMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthMicroserviceApplication.class, args);
	}

}
