package co.com.crediyarequest.model.security.gateways;

import reactor.core.publisher.Mono;


public interface AuthGateway {
    Mono<Boolean> isTokenValid(String token);
    Mono<Long> getRolId(String token);
    Mono<String> getCurrentToken();
}
