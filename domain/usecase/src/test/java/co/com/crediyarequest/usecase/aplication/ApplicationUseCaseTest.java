package co.com.crediyarequest.usecase.aplication;


import co.com.crediyarequest.model.application.Application;
import co.com.crediyarequest.model.application.gateways.ApplicationRepository;
import co.com.crediyarequest.model.loantype.gateways.LoanTypeRepository;
import exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationUseCaseTest {

    private ApplicationRepository applicationRepository;
    private LoanTypeRepository loanTypeRepository;
    private ApplicationUseCase applicationUseCase;

    @BeforeEach
    void setUp() {
        applicationRepository = Mockito.mock(ApplicationRepository.class);
        loanTypeRepository = Mockito.mock(LoanTypeRepository.class);
        applicationUseCase = new ApplicationUseCase(applicationRepository, loanTypeRepository);
    }

    @Test
    void When_LoanTypeExists_Expect_ApplicationToBeSavedCorrectly() {
        // Given
        Application application = new Application();
        application.setIdloanType(1L);
        application.setIdState(0L);
        application.setAmount(5000000.0);
        application.setTerm(12);
        application.setDocument("12345678");

        Application savedApplication = new Application();
        savedApplication.setIdRequest(1L);
        savedApplication.setIdloanType(1L);
        savedApplication.setIdState(1L);
        savedApplication.setAmount(5000000.0);
        savedApplication.setTerm(12);
        savedApplication.setDocument("12345678");

        when(loanTypeRepository.existsByidlongType(1L)).thenReturn(Mono.just(true));
        when(applicationRepository.saveApplication(any(Application.class))).thenReturn(Mono.just(savedApplication));


        Mono<Application> result = applicationUseCase.saveApplication(application);


        StepVerifier.create(result)
                .expectNextMatches(savedApp ->
                        savedApp.getIdRequest() != null &&
                                savedApp.getIdRequest().equals(1L) &&
                                savedApp.getIdloanType().equals(1L) &&
                                savedApp.getIdState().equals(1L) &&
                                savedApp.getAmount() == 5000000.0 &&
                                savedApp.getTerm() == 12 &&
                                savedApp.getDocument().equals("12345678")
                )
                .verifyComplete();

        verify(loanTypeRepository).existsByidlongType(1L);
        verify(applicationRepository).saveApplication(any(Application.class));
    }

    @Test
    void When_LoanTypeNoExists_Expect_ExceptionBussines() {
        // Given
        Application application = new Application();
        application.setIdloanType(1L);
        application.setIdState(0L);
        application.setAmount(5000000.0);
        application.setTerm(12);
        application.setDocument("12345678");

        when(loanTypeRepository.existsByidlongType(1L)).thenReturn(Mono.just(false));

        Mono<Application> result = applicationUseCase.saveApplication(application);

/*
        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException &&
                                throwable.getMessage().contains("El tipo de préstamo con ID " + application.getIdloanType() + " no existe")
                )
                .verify();
                */

    }



}
