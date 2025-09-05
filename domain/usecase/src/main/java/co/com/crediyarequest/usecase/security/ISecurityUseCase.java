package co.com.crediyarequest.usecase.security;

import reactor.core.publisher.Mono;

public interface ISecurityUseCase {
    Mono<Boolean> isTokenValid(String token);
    Mono<Boolean> hasPermission(String token, String path, String method);
}
