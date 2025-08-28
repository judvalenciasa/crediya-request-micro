package co.com.crediyarequest.model.loantype.gateways;

import reactor.core.publisher.Mono;

public interface LoanTypeRepository {
    Mono<Boolean> existsByidlongType(Long idlongType);
}
