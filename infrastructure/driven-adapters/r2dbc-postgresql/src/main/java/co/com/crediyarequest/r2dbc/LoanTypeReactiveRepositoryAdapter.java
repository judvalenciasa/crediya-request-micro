package co.com.crediyarequest.r2dbc;

import co.com.crediyarequest.model.loantype.LoanType;
import co.com.crediyarequest.model.loantype.gateways.LoanTypeRepository;
import co.com.crediyarequest.r2dbc.entity.LoanTypeEntity;
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
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, LoanType.class/* change for domain model */));
    }

    @Override
    public Mono<Boolean> existsByidlongType(Long idlongType) {
        return repository.existsById(idlongType);
    }


}
