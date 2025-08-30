package co.com.crediyarequest.r2dbc;

import co.com.crediyarequest.model.state.State;
import co.com.crediyarequest.model.state.gateways.StateRepository;
import co.com.crediyarequest.r2dbc.entity.StateEntity;
import co.com.crediyarequest.r2dbc.exception.HandleDatabaseError;
import co.com.crediyarequest.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class StateReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        State,
        StateEntity,
        Long,
        StateReactiveRepository
        > implements StateRepository {

    public StateReactiveRepositoryAdapter(StateReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, State.class));
    }

    @Override
    public Mono<State> findByName(String name) {
        return repository.findByName(name)
                .map(this::toEntity)
                .onErrorMap(error -> new HandleDatabaseError("Error searching rol for name: " + error.getMessage()));
    }
}
