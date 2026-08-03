package com.inklusport.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class FallbackController {

    @RequestMapping("/fallback/auth")
    public Mono<ResponseEntity<Map<String, String>>> authFallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
            "error", "Servicio de autenticación no disponible",
            "message", "Intente más tarde"
        )));
    }

    @RequestMapping("/fallback/users")
    public Mono<ResponseEntity<Map<String, String>>> usersFallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
            "error", "Servicio de usuarios no disponible",
            "message", "Intente más tarde"
        )));
    }

    @RequestMapping("/fallback/sports")
    public Mono<ResponseEntity<Map<String, String>>> sportsFallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
            "error", "Servicio de deportes no disponible",
            "message", "Intente más tarde"
        )));
    }

    @RequestMapping("/fallback/ai")
    public Mono<ResponseEntity<Map<String, String>>> aiFallback() {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
            "error", "Asistente de IA no disponible",
            "message", "El chatbot está temporalmente fuera de servicio. Intente más tarde."
        )));
    }
}
