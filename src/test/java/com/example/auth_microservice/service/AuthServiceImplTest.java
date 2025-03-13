package com.example.auth_microservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.auth_microservice.config.Credentials;
import com.example.auth_microservice.config.ExternalServiceConfig;
import com.example.auth_microservice.models.auth.response.AuthResponse;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuthServiceImplTest {

  @Mock private ExternalServiceConfig externalServiceConfig;

  @Mock private RestTemplate restTemplate;

  @InjectMocks private AuthServiceImpl authServiceImpl;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    Credentials credentials = new Credentials();
    credentials.setUsername("auth-vivelibre");
    credentials.setPassword("password");

    when(externalServiceConfig.getUrl()).thenReturn("http://localhost:8080/token");
    when(externalServiceConfig.getCredentials()).thenReturn(credentials);
  }

  @Test
  public void testGetTokenWithTimestamp_ReturnsValidResponse() {

    AuthResponse mockAuthResponse = new AuthResponse();
    mockAuthResponse.setToken("mocked-token");
    ResponseEntity<AuthResponse> mockResponseEntity = ResponseEntity.ok(mockAuthResponse);
    when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(AuthResponse.class)))
        .thenReturn(mockResponseEntity);
    Map<String, String> result = authServiceImpl.getTokenWithTimestamp();
    verify(restTemplate).postForEntity(anyString(), any(HttpEntity.class), eq(AuthResponse.class));
    assertEquals("mocked-token", result.get("token"));
  }

  @Test
  public void testGetTokenWithTimestamp_ThrowsExceptionWhenTokenMissing() {
    ResponseEntity<AuthResponse> mockResponseEntity = ResponseEntity.ok(new AuthResponse());
    when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(AuthResponse.class)))
            .thenReturn(mockResponseEntity);
    IllegalStateException exception =
        assertThrows(
            IllegalStateException.class,
            () -> {
              authServiceImpl.getTokenWithTimestamp();
            });
    verify(restTemplate).postForEntity(anyString(), any(HttpEntity.class), eq(AuthResponse.class));
    assertEquals("Token is missing in the response", exception.getMessage());
  }

  @Test
  public void testGetTokenWithTimestamp_ThrowsExceptionWhenServiceFails() {
    when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(AuthResponse.class)))
        .thenThrow(new RuntimeException("Service Unavailable"));
    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              authServiceImpl.getTokenWithTimestamp();
            });
    verify(restTemplate).postForEntity(anyString(), any(HttpEntity.class), eq(AuthResponse.class));
    assertEquals("Service Unavailable", exception.getMessage());
  }
}
