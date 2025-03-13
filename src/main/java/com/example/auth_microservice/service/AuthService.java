package com.example.auth_microservice.service;

import java.util.Map;

/**
 * @author Jesús Jiménez Ocaña
 */
public interface AuthService {

    /**
     * Retrieves a token along with the current timestamp.
     *
     * @return {@code Map<String, String>} a map containing the token and the timestamp.
     */
    Map<String, String> getTokenWithTimestamp();
}
