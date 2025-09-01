package co.com.crediyarequest.model.application.gateways;

import reactor.core.publisher.Mono;

public interface  UserServiceGateway {
    Mono<Boolean> existsByDocument(String document);
}
