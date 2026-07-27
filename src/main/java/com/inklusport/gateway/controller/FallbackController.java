package com.inklusport.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class FallbackController {

    @RequestMapping("/fallback/auth")
    public Mono<Map<String, String>> authFallback() {
        return Mono.just(Map.of(
            "error", "Servicio de autenticación no disponible",
            "message", "Intente más tarde"
        ));
    }

    @RequestMapping("/fallback/users")
    public Mono<Map<String, String>> usersFallback() {
        return Mono.just(Map.of(
            "error", "Servicio de usuarios no disponible",
            "message", "Intente más tarde"
        ));
    }

    @RequestMapping("/fallback/sports")
    public Mono<Map<String, String>> sportsFallback() {
        return Mono.just(Map.of(
            "error", "Servicio de deportes no disponible",
            "message", "Intente más tarde"
        ));
    }
}