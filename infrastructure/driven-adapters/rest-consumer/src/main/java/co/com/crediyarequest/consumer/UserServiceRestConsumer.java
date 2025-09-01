package co.com.crediyarequest.consumer;

import co.com.crediyarequest.model.application.gateways.UserServiceGateway;
import exceptions.BusinessException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
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
        log.info("=== START: Calling user service for document: {} ===", document);

        return client.get()
                .uri("/api/v1/users /{documentNumber}", document)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> {
                            log.error("=== 4xx ERROR: User service returned 4xx for document: {} ===", document);
                            return Mono.error(new BusinessException("Usuario no encontrado: " + document));
                        })
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> {
                            log.error("=== 5xx ERROR: User service returned 5xx for document: {} ===", document);
                            return Mono.error(new BusinessException("Error en el servicio de usuarios"));
                        })
                .bodyToMono(UserResponse.class)
                .map(response -> {
                    log.info("=== SUCCESS: User service response for document {}: {} ===", document, response);
                    return response.getExists();
                })
                .doOnNext(exists -> log.info("=== RESULT: User exists check for document {}: {} ===", document, exists))
                .doOnError(error -> log.error("=== ERROR: Error checking user existence for document {}: {} ===", document, error.getMessage()))
                .onErrorResume(error -> {
                    log.error("=== ERROR RESUME: Error in user service call for document {}: {} ===", document, error.getMessage());
                    if (error instanceof BusinessException) {
                        return Mono.error(error);
                    }
                    return Mono.error(new BusinessException("Error de comunicación con el servicio de usuarios"));
                });
    }

    public Mono<Boolean> existsByDocumentFallback(String document, Exception ex) {
        log.warn("Circuit breaker open for user exists check, document: {}, error: {}", document, ex.getMessage());
        return Mono.error(new BusinessException("Servicio de usuarios no disponible temporalmente"));
    }
}
