package com.example.auth_microservice.controller;

import com.example.auth_microservice.service.AuthService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jesús Jiménez Ocaña
 */
@RestController
@RequestMapping("/api/v1")
public class AuthController {

  @Autowired private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @GetMapping("/token")
  public ResponseEntity<Map<String, String>> getToken() {
    Map<String, String> response = this.authService.getTokenWithTimestamp();
    return ResponseEntity.ok(response);
  }
}
