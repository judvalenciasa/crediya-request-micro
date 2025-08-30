package co.com.crediyarequest.usecase.aplication;


import co.com.crediyarequest.model.application.Application;
import co.com.crediyarequest.model.application.gateways.ApplicationRepository;
import co.com.crediyarequest.model.loantype.LoanType;
import co.com.crediyarequest.model.loantype.gateways.LoanTypeRepository;
import co.com.crediyarequest.model.state.State;
import co.com.crediyarequest.model.state.gateways.StateRepository;
import exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ApplicationUseCase implements IApplicationUseCase {
    private final ApplicationRepository applicationRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final StateRepository stateRepository;


    @Override
    public Mono<Application> saveApplication(Application application) {
        return Mono.zip(
                        determineLoanType(application.getAmount()),
                        findPendingReviewState()
                )
                .flatMap(tuple -> {
                    Long loanTypeId = tuple.getT1();
                    Long stateId = tuple.getT2();

                    application.setLoantypeId(loanTypeId);
                    application.setStateId(stateId);

                    return applicationRepository.saveApplication(application);
                });
    }

    private Mono<Long> determineLoanType(double amount) {
        return loanTypeRepository.findByAmountRange(amount)
                .map(LoanType::getIdloanType)
                .switchIfEmpty(Mono.error(new BusinessException("No se encontró un tipo de préstamo válido para el monto: " + amount)));
    }

    private Mono<Long> findPendingReviewState() {
        return stateRepository.findByName("Pendiente de revisión")
                .map(State::getIdState)
                .switchIfEmpty(Mono.error(new BusinessException("No se encontró el estado 'Pendiente de revisión'")));
    }
}
