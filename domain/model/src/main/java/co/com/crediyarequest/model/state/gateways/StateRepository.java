package co.com.crediyarequest.model.state.gateways;

import co.com.crediyarequest.model.state.State;
import reactor.core.publisher.Mono;

public interface StateRepository {
    Mono<State> findByName(String name);
}
