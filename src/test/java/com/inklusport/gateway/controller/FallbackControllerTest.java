package com.inklusport.gateway.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FallbackControllerTest {

    private final FallbackController controller = new FallbackController();

    @Test
    void authFallback_devuelve503() {
        ResponseEntity<Map<String, String>> response = controller.authFallback().block();

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals("Servicio de autenticación no disponible", response.getBody().get("error"));
    }

    @Test
    void usersFallback_devuelve503() {
        ResponseEntity<Map<String, String>> response = controller.usersFallback().block();

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals("Servicio de usuarios no disponible", response.getBody().get("error"));
    }

    @Test
    void sportsFallback_devuelve503() {
        ResponseEntity<Map<String, String>> response = controller.sportsFallback().block();

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals("Servicio de deportes no disponible", response.getBody().get("error"));
    }

    @Test
    void aiFallback_devuelve503() {
        ResponseEntity<Map<String, String>> response = controller.aiFallback().block();

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertEquals("Asistente de IA no disponible", response.getBody().get("error"));
    }
}
