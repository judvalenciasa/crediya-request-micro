package co.com.crediyarequest.r2dbc;

import co.com.crediyarequest.r2dbc.entity.LoanTypeEntity;
import co.com.crediyarequest.r2dbc.entity.StateEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public interface StateReactiveRepository extends ReactiveCrudRepository<StateEntity, Long>, ReactiveQueryByExampleExecutor<StateEntity> {

    @Query("SELECT * FROM estados WHERE nombre = :name LIMIT 1")
    Mono<StateEntity> findByName(@Param("name") String name);
}
