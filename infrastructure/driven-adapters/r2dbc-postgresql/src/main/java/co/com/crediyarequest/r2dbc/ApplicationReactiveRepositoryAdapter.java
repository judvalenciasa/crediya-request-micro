package co.com.crediyarequest.r2dbc;

import co.com.crediyarequest.model.application.Application;
import co.com.crediyarequest.model.application.gateways.ApplicationRepository;
import co.com.crediyarequest.r2dbc.entity.ApplicationEntity;
import co.com.crediyarequest.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import org.springframework.transaction.reactive.TransactionalOperator;

@Repository
public class ApplicationReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Application/* change for domain model */,
        ApplicationEntity/* change for adapter model */,
        Long,
        ApplicationReactiveRepository
        > implements ApplicationRepository/* change for domain repository */ {

    private final TransactionalOperator transactionalOperator;

    public ApplicationReactiveRepositoryAdapter(ApplicationReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {

        super(repository, mapper, d -> mapper.map(d, Application.class/* change for domain model */));
        this.transactionalOperator = transactionalOperator;
    }


    @Override
    public Mono<Application> saveApplication(Application application) {
        return transactionalOperator.transactional(
                save(application)
        );
    }
}
