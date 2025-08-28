package co.com.crediyarequest.r2dbc;

import co.com.crediyarequest.model.application.Application;
import co.com.crediyarequest.model.application.gateways.ApplicationRepository;
import co.com.crediyarequest.r2dbc.entity.ApplicationEntity;
import co.com.crediyarequest.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ApplicationReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Application/* change for domain model */,
        ApplicationEntity/* change for adapter model */,
        Long,
        ApplicationReactiveRepository
        > implements ApplicationRepository/* change for domain repository */ {
    public ApplicationReactiveRepositoryAdapter(ApplicationReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Application.class/* change for domain model */));
    }

    @Override
    public Mono<Application> saveApplication(Application application) {
        return null;
    }
}
