package co.com.crediyarequest.usecase.aplication;


import co.com.crediyarequest.model.application.Application;
import co.com.crediyarequest.model.application.gateways.ApplicationRepository;
import co.com.crediyarequest.model.loantype.gateways.LoanTypeRepository;
import exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ApplicationUseCase implements IApplicationUseCase {
    private final ApplicationRepository applicationRepository;
    private final LoanTypeRepository loanTypeRepository;

    @Override
    public Mono<Application> saveApplication(Application application) {
        return loanTypeRepository.existsByidlongType(application.getIdloanType())
                .flatMap(exists -> {
                    if (exists) {
                        application.setIdState(1L);
                        return applicationRepository.saveApplication(application);
                    } else {
                        return Mono.error(new BusinessException("El tipo de préstamo con ID " + application.getIdloanType() + " no existe"));
                    }
                })
                .onErrorResume(error -> {
                    // Si hay error en la consulta, lo propagamos
                    return Mono.error(error);
                });
    }
}
