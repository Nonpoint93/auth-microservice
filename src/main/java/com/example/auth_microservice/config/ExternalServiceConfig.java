package com.example.auth_microservice.config;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author Jesús Jiménez Ocaña
 */
@Component
@ConfigurationProperties(prefix = "external-service")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class ExternalServiceConfig {
    @NotBlank
    private String url;
    private Credentials credentials;
}
