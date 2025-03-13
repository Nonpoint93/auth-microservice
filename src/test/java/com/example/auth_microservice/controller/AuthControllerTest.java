package com.example.auth_microservice.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.auth_microservice.boot.AuthMicroserviceApplication;
import com.example.auth_microservice.config.TestSecurityConfig;
import com.example.auth_microservice.service.AuthService;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@ContextConfiguration(classes = {AuthMicroserviceApplication.class, TestSecurityConfig.class})
public class AuthControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean
  private AuthService authService;

  @Test
  public void testGetToken_ReturnsTokenAndTimestamp() throws Exception {
    Map<String, String> mockResponse =
        Map.of(
            "token", "mocked-token",
            "timestamp", "2025-03-13T14:00:00Z");
    when(authService.getTokenWithTimestamp()).thenReturn(mockResponse);

    mockMvc
        .perform(get("/api/v1/token"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("mocked-token"))
        .andExpect(jsonPath("$.timestamp").value("2025-03-13T14:00:00Z"));

    verify(authService).getTokenWithTimestamp();
  }

  @Test
  public void testGetToken_InvalidResponse() throws Exception {
    when(authService.getTokenWithTimestamp()).thenReturn(Map.of());

    mockMvc
        .perform(get("/api/v1/token"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").doesNotExist())
        .andExpect(jsonPath("$.timestamp").doesNotExist());
    verify(authService).getTokenWithTimestamp();
  }
}
