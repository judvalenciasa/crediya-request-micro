package co.com.crediyarequest.consumer;

import co.com.crediyarequest.model.application.gateways.UserServiceGateway;
import exceptions.BusinessException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceRestConsumer implements UserServiceGateway {
    private final WebClient client;

    @CircuitBreaker(name = "userExists", fallbackMethod = "existsByDocumentFallback")
    @Override
    public Mono<Boolean> existsByDocument(String document) {
        return client.get()
                .uri("/users/exists/{document}", document)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> Mono.error(new BusinessException("Usuario no encontrado: " + document)))
                .onStatus(status -> status.is5xxServerError(),
                        response -> Mono.error(new BusinessException("Error en el servicio de usuarios")))
                .bodyToMono(Boolean.class)
                .doOnNext(exists -> log.info("User exists check for document {}: {}", document, exists))
                .doOnError(error -> log.error("Error checking user existence for document {}: {}", document, error.getMessage()))
                .onErrorResume(error -> {
                    if (error instanceof BusinessException) {
                        return Mono.error(error);
                    }
                    return Mono.error(new BusinessException("Error de comunicación con el servicio de usuarios"));
                });
    }

    // Método fallback cuando el circuit breaker está abierto
    public Mono<Boolean> existsByDocumentFallback(String document, Exception ex) {
        log.warn("Circuit breaker open for user exists check, document: {}, error: {}", document, ex.getMessage());
        return Mono.error(new BusinessException("Servicio de usuarios no disponible temporalmente"));
    }
}
