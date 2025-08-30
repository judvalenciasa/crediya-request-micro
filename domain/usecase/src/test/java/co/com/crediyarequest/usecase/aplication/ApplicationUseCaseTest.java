package co.com.crediyarequest.usecase.aplication;


import co.com.crediyarequest.model.application.Application;
import co.com.crediyarequest.model.application.gateways.ApplicationRepository;
import co.com.crediyarequest.model.loantype.LoanType;
import co.com.crediyarequest.model.loantype.gateways.LoanTypeRepository;
import co.com.crediyarequest.model.state.State;
import co.com.crediyarequest.model.state.gateways.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ApplicationUseCaseTest {

    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private LoanTypeRepository loanTypeRepository;
    @Mock
    private StateRepository stateRepository;

    private ApplicationUseCase applicationUseCase;

    @BeforeEach
    void setup() {
        applicationRepository = Mockito.mock(ApplicationRepository.class);
        loanTypeRepository = Mockito.mock(LoanTypeRepository.class);
        stateRepository = Mockito.mock(StateRepository.class);
        applicationUseCase = new ApplicationUseCase(applicationRepository, loanTypeRepository, stateRepository, null);
    }

    @Test
    void When_LoanTypeExists_Expect_ApplicationToBeSavedCorrectly() {
        // Given
        Application application = new Application();
        application.setLoantypeId(0L);
        application.setStateId(0L);
        application.setAmount(5000000.0);
        application.setTerm(12);
        application.setDocument("12345678");

        Application savedApplication = new Application();
        savedApplication.setIdRequest(1L);
        savedApplication.setLoantypeId(2L);
        savedApplication.setStateId(1L);
        savedApplication.setAmount(5000000.0);
        savedApplication.setTerm(12);
        savedApplication.setDocument("12345678");

        LoanType loanType = new LoanType();
        loanType.setIdloanType(2L);
        loanType.setName("VEHICULAR");

        State state = new State();
        state.setIdState(1L);
        state.setName("Pendiente de revisión");

        // When
        Mockito.when(loanTypeRepository.findByAmountRange(5000000.0)).thenReturn(Mono.just(loanType));
        Mockito.when(stateRepository.findByName("Pendiente de revisión")).thenReturn(Mono.just(state));
        Mockito.when(applicationRepository.saveApplication(Mockito.any(Application.class))).thenReturn(Mono.just(savedApplication));

        // Then
        StepVerifier.create(applicationUseCase.saveApplication(application))
                .expectNext(savedApplication)
                .verifyComplete();
    }
}


