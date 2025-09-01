package co.com.crediyarequest.r2dbc;


import co.com.crediyarequest.r2dbc.entity.LoanTypeEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LoanTypeReactiveRepository extends ReactiveCrudRepository<LoanTypeEntity, Long>, ReactiveQueryByExampleExecutor<LoanTypeEntity> {
    @Query("SELECT * FROM tipo_prestamos WHERE :amount >= monto_minimo AND :amount <= monto_maximo LIMIT 1")
    Mono<LoanTypeEntity> findByAmountRange(@Param("amount") double amount);
}
