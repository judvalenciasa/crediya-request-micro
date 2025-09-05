package co.com.crediyarequest.usecase.security;


import co.com.crediyarequest.model.security.gateways.AuthGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SecurityUseCase implements ISecurityUseCase {
    private final AuthGateway authGateway;

    @Override
    public Mono<Boolean> isTokenValid(String token){
        return authGateway.isTokenValid(token);
    }

    @Override
    public Mono<Boolean> hasPermission(String token, String path, String method) {
        return getRolId(token)
                .flatMap(roleId -> hasRoleAccess(roleId, path, method))
                .onErrorReturn(false);
    }

    public Mono<Long> getRolId(String token){
        return authGateway.getRolId(token);
    }


    public Mono<Boolean> hasRoleAccess(Long role, String path, String method){
        Long administrador = 21L;
        Long asesor = 23L;
        Long cliente = 22L;

        return Mono.fromCallable(() -> {
            Map<String, List<Long>> rules = Map.of(
                    "/api/v1/requests:POST", List.of(cliente),
                    "/api/v1/requests:GET", List.of(asesor),
                    "/api/v1/requests:PUT", List.of(asesor)
            );

            String key = path + ":" + method;

            if (rules.containsKey(key)) {
                return rules.get(key).contains(role);
            }

            for (Map.Entry<String, List<Long>> entry : rules.entrySet()) {
                if (entry.getKey().startsWith(path + ":") && entry.getKey().endsWith(":*")) {
                    return entry.getValue().contains(role);
                }
            }
            return false;
        });
    }


}
