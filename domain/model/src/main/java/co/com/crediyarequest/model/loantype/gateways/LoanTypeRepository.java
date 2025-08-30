package co.com.crediyarequest.model.loantype.gateways;

import co.com.crediyarequest.model.loantype.LoanType;
import reactor.core.publisher.Mono;

public interface LoanTypeRepository {
    Mono<Boolean> existsByidlongType(Long idlongType);
    Mono<LoanType> findByAmountRange(double amount);
}
