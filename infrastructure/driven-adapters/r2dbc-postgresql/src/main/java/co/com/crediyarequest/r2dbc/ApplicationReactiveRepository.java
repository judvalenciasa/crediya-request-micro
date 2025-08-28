package co.com.crediyarequest.r2dbc;

import co.com.crediyarequest.r2dbc.entity.ApplicationEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface ApplicationReactiveRepository extends ReactiveCrudRepository<ApplicationEntity, Long>, ReactiveQueryByExampleExecutor<ApplicationEntity> {

}
