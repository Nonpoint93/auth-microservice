package com.example.auth_microservice.service;

import com.example.auth_microservice.config.ExternalServiceConfig;
import com.example.auth_microservice.models.auth.request.AuthRequest;
import com.example.auth_microservice.models.auth.response.AuthResponse;
import java.time.Instant;
import java.util.Map;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jesús Jiménez Ocaña
 */
@Service
@Builder
public class AuthServiceImpl implements AuthService {

  private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private static final String TOKEN = "token";
  private static final String TIMESTAMP = "timestamp";

  private final ExternalServiceConfig externalServiceConfig;
  private final RestTemplate restTemplate;

  public AuthServiceImpl(final ExternalServiceConfig externalServiceConfig, final RestTemplate restTemplate) {
    this.externalServiceConfig = externalServiceConfig;
    this.restTemplate = restTemplate;
  }

    /**
     * Builds an HttpEntity containing the authentication request with JSON headers.
     *
     * @return {@code HttpEntity<AuthRequest>} an HttpEntity containing the AuthRequest with the
     *     username and password.
     */
  private HttpEntity<AuthRequest> buildRequestEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    return new HttpEntity<>(
        AuthRequest.builder()
            .username(this.externalServiceConfig.getCredentials().getUsername())
            .password(this.externalServiceConfig.getCredentials().getPassword())
            .build(),
        headers);
  }

  /**
   * Extracts the token from the given response entity.
   *
   * @param response the response entity containing the authentication response.
   * @return {@code String} the token extracted from the response.
   * @throws IllegalStateException if the token is missing in the response.
   */
  private String extractToken(final ResponseEntity<AuthResponse> response) {
    if (ObjectUtils.isEmpty(response.getBody())
        || ObjectUtils.isEmpty(response.getBody().getToken())) {
      throw new IllegalStateException("Token is missing in the response");
    }
    logger.info("Token successfully retrieved and processed");
    return response.getBody().getToken();
  }

  /** {@inheritDoc} */
  public Map<String, String> getTokenWithTimestamp() {
    logger.info("Request to external service for token initiated");
    return Map.of(
        TOKEN,
        extractToken(
            this.restTemplate.postForEntity(
                this.externalServiceConfig.getUrl(), buildRequestEntity(), AuthResponse.class)),
        TIMESTAMP,
        Instant.now().toString());
  }
}
