package com.example.auth_microservice.config;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * @author Jesús Jiménez Ocaña
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class Credentials {
  @NotBlank
  private String username;
  @NotBlank
  private String password;
}
