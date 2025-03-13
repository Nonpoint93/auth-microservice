package com.example.auth_microservice.config;

import static org.junit.jupiter.api.Assertions.*;

import com.example.auth_microservice.boot.AuthMicroserviceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AuthMicroserviceApplication.class)
public class ExternalServiceConfigTest {

  @Autowired private ExternalServiceConfig config;

  @Test
  void testConfigLoaded() {
    assertEquals("http://auth-vivelibre:8080/token", config.getUrl());
    assertEquals("auth-vivelibre", config.getCredentials().getUsername());
    assertEquals("password", config.getCredentials().getPassword());
  }
}
