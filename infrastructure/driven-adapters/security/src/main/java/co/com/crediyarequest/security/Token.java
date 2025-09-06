package co.com.crediyarequest.security;

import co.com.crediyarequest.model.security.gateways.AuthGateway;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class Token implements AuthGateway {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Mono<Boolean> isTokenValid(String token) {
        return Mono.fromCallable(() -> {
            try {
                var claims = Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    @Override
    public Mono<Long> getRolId(String token){
        var claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long role = claims.get("role", Long.class);
        return Mono.just(role);
    }

    @Override
    public Mono<String> getCurrentToken() {
        return Mono.deferContextual(ctx -> {
            String token = ctx.getOrDefault("JWT_TOKEN", "");
            if (token.isEmpty()) {
                return Mono.empty();
            }
            return Mono.just(token);
        });
    }



}
