package co.com.crediyarequest.r2dbc;

import co.com.crediyarequest.model.loantype.LoanType;
import co.com.crediyarequest.model.loantype.gateways.LoanTypeRepository;
import co.com.crediyarequest.r2dbc.entity.LoanTypeEntity;
import co.com.crediyarequest.r2dbc.exception.HandleDatabaseError;
import co.com.crediyarequest.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class LoanTypeReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        LoanType/* change for domain model */,
        LoanTypeEntity/* change for adapter model */,
        Long,
        LoanTypeReactiveRepository
> implements LoanTypeRepository {
    public LoanTypeReactiveRepositoryAdapter(LoanTypeReactiveRepository repository, ObjectMapper mapper) {

        super(repository, mapper, d -> mapper.map(d, LoanType.class/* change for domain model */));

    }

    @Override
    public Mono<Boolean> existsByidlongType(Long idloanType) {
        return repository.existsById(idloanType);
    }

    @Override
    public Mono<LoanType> findByAmountRange(double amount) {
        return repository.findByAmountRange(amount)
                .map(this::toEntity)
                .onErrorMap(error -> new HandleDatabaseError("Error searching range of amount: " + error.getMessage()));
    }
}
