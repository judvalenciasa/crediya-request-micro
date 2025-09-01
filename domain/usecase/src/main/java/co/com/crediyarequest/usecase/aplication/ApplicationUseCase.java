package co.com.crediyarequest.usecase.aplication;


import co.com.crediyarequest.model.application.Application;
import co.com.crediyarequest.model.application.gateways.ApplicationRepository;
import co.com.crediyarequest.model.application.gateways.UserServiceGateway;
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
    private final UserServiceGateway userServiceGateway;



    @Override
    public Mono<Application> saveApplication(Application application) {
        return validateUserExistsService(application.getDocument())
                .then(Mono.zip(
                        determineLoanType(application.getAmount()),
                        findPendingReviewState()
                ))
                .flatMap(tuple -> {
                    Long loanTypeId = tuple.getT1();
                    Long stateId = tuple.getT2();

                    application.setLoantypeId(loanTypeId);
                    application.setStateId(stateId);

                    return applicationRepository.saveApplication(application);
                });
    }

    private Mono<Void> validateUserExistsService(String document) {
        return userServiceGateway.existsByDocument(document)
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.empty();
                    } else {
                        return Mono.error(new BusinessException("The user with document" + document + " does not exist"));
                    }
                });
    }

    private Mono<Long> determineLoanType(double amount) {
        return loanTypeRepository.findByAmountRange(amount)
                .map(LoanType::getIdloanType)
                .switchIfEmpty(Mono.error(new BusinessException("No valid loan type was found for the amount: " + amount)));
    }

    private Mono<Long> findPendingReviewState() {
        return stateRepository.findByName("Pendiente de revisión")
                .map(State::getIdState)
                .switchIfEmpty(Mono.error(new BusinessException("'Pending Review' status not found")));
    }
}
