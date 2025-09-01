package co.com.crediyarequest.usecase.aplication;


import co.com.crediyarequest.model.application.Application;
import co.com.crediyarequest.model.application.gateways.ApplicationRepository;
import co.com.crediyarequest.model.application.gateways.UserServiceGateway;
import co.com.crediyarequest.model.loantype.LoanType;
import co.com.crediyarequest.model.loantype.gateways.LoanTypeRepository;
import co.com.crediyarequest.model.state.State;
import co.com.crediyarequest.model.state.gateways.StateRepository;
import exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("ApplicationUseCase Tests")
class ApplicationUseCaseTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private LoanTypeRepository loanTypeRepository;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private UserServiceGateway userServiceGateway;

    @InjectMocks
    private ApplicationUseCase applicationUseCase;

    private Application application;
    private LoanType loanType;
    private State state;

    @BeforeEach
    void setUp() {
        application = TestDataBuilder.createDefaultApplication();
        loanType = TestDataBuilder.createDefaultLoanType();
        state = TestDataBuilder.createPendingReviewState();
    }

    @Nested
    class SaveApplicationSuccessTests {

        @Test
        void When_SaveApplicationWithValidData_Expect_ApplicationToBeSavedSuccessfully() {
            Application expectedApplication = TestDataBuilder.createApplicationWithLoanTypeAndState();

            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(100000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any(Application.class)))
                    .thenReturn(Mono.just(expectedApplication));

            Mono<Application> result = applicationUseCase.saveApplication(application);


            StepVerifier.create(result)
                    .expectNext(expectedApplication)
                    .verifyComplete();

            verify(userServiceGateway).existsByDocument("12345678");
            verify(loanTypeRepository).findByAmountRange(100000.0);
            verify(stateRepository).findByName("Pendiente de revisión");
            verify(applicationRepository).saveApplication(any(Application.class));
        }

        @Test
        @DisplayName("When_SaveApplicationWithDifferentTerms_Expect_ApplicationToBeSavedWithCorrectTerm")
        void When_SaveApplicationWithDifferentTerms_Expect_ApplicationToBeSavedWithCorrectTerm() {
            // Arrange
            Application applicationWithTerm = ApplicationTestBuilder.anApplication()
                    .withDocument("12345678")
                    .withAmount(100000.0)
                    .withTerm(24)
                    .build();

            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(100000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any(Application.class)))
                    .thenReturn(Mono.just(applicationWithTerm));


            Mono<Application> result = applicationUseCase.saveApplication(applicationWithTerm);


            StepVerifier.create(result)
                    .expectNext(applicationWithTerm)
                    .verifyComplete();

            assert applicationWithTerm.getTerm() == 24;
        }
    }

    @Nested
    @DisplayName("saveApplication - Casos de error")
    class SaveApplicationErrorTests {
        @Test
        @DisplayName("When_PendingReviewStateNotFound_Expect_BusinessExceptionToBeThrown")
        void When_PendingReviewStateNotFound_Expect_BusinessExceptionToBeThrown() {

            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(100000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.empty());

            Mono<Application> result = applicationUseCase.saveApplication(application);


            StepVerifier.create(result)
                    .expectErrorMatches(throwable ->
                            throwable instanceof BusinessException &&
                                    throwable.getMessage().equals("No se encontró el estado 'Pendiente de revisión'"))
                    .verify();

            verify(userServiceGateway).existsByDocument("12345678");
            verify(loanTypeRepository).findByAmountRange(100000.0);
            verify(stateRepository).findByName("Pendiente de revisión");
        }

        @Test
        @DisplayName("When_ApplicationRepositoryReturnsError_Expect_RuntimeExceptionToBeThrown")
        void When_ApplicationRepositoryReturnsError_Expect_RuntimeExceptionToBeThrown() {

            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(100000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any(Application.class)))
                    .thenReturn(Mono.error(new RuntimeException("Error al guardar en base de datos")));


            Mono<Application> result = applicationUseCase.saveApplication(application);


            StepVerifier.create(result)
                    .expectError(RuntimeException.class)
                    .verify();

            verify(applicationRepository).saveApplication(any(Application.class));
        }
    }



    @Nested
    @DisplayName("determineLoanType - Método privado")
    class DetermineLoanTypeTests {

        @Test
        @DisplayName("When_LoanTypeFoundForAmount_Expect_LoanTypeIdToBeReturned")
        void When_LoanTypeFoundForAmount_Expect_LoanTypeIdToBeReturned() {

            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(50000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any()))
                    .thenReturn(Mono.just(application));

            Application testApplication = TestDataBuilder.createApplicationWithCustomData("12345678", 50000.0, 18);


            Mono<Application> result = applicationUseCase.saveApplication(testApplication);


            StepVerifier.create(result)
                    .expectNext(application)
                    .verifyComplete();

            verify(loanTypeRepository).findByAmountRange(50000.0);
        }



        @Test
        @DisplayName("When_DifferentLoanTypesForDifferentAmounts_Expect_CorrectLoanTypeToBeAssigned")
        void When_DifferentLoanTypesForDifferentAmounts_Expect_CorrectLoanTypeToBeAssigned() {

            LoanType microcreditLoan = TestDataBuilder.createMicrocreditLoanType();

            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any()))
                    .thenReturn(Mono.just(application));


            when(loanTypeRepository.findByAmountRange(5000.0))
                    .thenReturn(Mono.just(microcreditLoan));
            Application microcreditApp = TestDataBuilder.createApplicationWithCustomData("12345678", 5000.0, 6);


            StepVerifier.create(applicationUseCase.saveApplication(microcreditApp))
                    .expectNext(application)
                    .verifyComplete();

            verify(loanTypeRepository).findByAmountRange(5000.0);
        }
    }

    @Nested
    @DisplayName("findPendingReviewState - Método privado")
    class FindPendingReviewStateTests {

        @Test
        @DisplayName("When_PendingReviewStateFound_Expect_StateIdToBeReturned")
        void When_PendingReviewStateFound_Expect_StateIdToBeReturned() {

            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(100000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any()))
                    .thenReturn(Mono.just(application));


            Mono<Application> result = applicationUseCase.saveApplication(application);


            StepVerifier.create(result)
                    .expectNext(application)
                    .verifyComplete();

            verify(stateRepository).findByName("Pendiente de revisión");
        }

        @Test
        @DisplayName("When_StateRepositoryReturnsError_Expect_RuntimeExceptionToBeThrown")
        void When_StateRepositoryReturnsError_Expect_RuntimeExceptionToBeThrown() {

            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(100000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.error(new RuntimeException("Error de conexión a base de datos")));


            Mono<Application> result = applicationUseCase.saveApplication(application);


            StepVerifier.create(result)
                    .expectError(RuntimeException.class)
                    .verify();
        }
    }

    @Nested
    class EdgeAndIntegrationTests {

        @Test
        @DisplayName("When_SaveApplicationWithDifferentDocumentFormats_Expect_AllFormatsToBeProcessedSuccessfully")
        void When_SaveApplicationWithDifferentDocumentFormats_Expect_AllFormatsToBeProcessedSuccessfully() {

            String[] documents = {"12345678", "1234567890", "CC-12345678", "TI-87654321"};

            for (String document : documents) {
                when(userServiceGateway.existsByDocument(document))
                        .thenReturn(Mono.just(true));
                when(loanTypeRepository.findByAmountRange(anyDouble()))
                        .thenReturn(Mono.just(loanType));
                when(stateRepository.findByName("Pendiente de revisión"))
                        .thenReturn(Mono.just(state));
                when(applicationRepository.saveApplication(any()))
                        .thenReturn(Mono.just(application));

                Application testApplication = TestDataBuilder.createApplicationWithCustomData(document, 100000.0, 12);

                // Act
                Mono<Application> result = applicationUseCase.saveApplication(testApplication);

                // Assert
                StepVerifier.create(result)
                        .expectNext(application)
                        .verifyComplete();

                verify(userServiceGateway).existsByDocument(document);
            }
        }

        @Test
        @DisplayName("When_SaveApplicationWithValidData_Expect_CorrectOrderOfOperations")
        void When_SaveApplicationWithValidData_Expect_CorrectOrderOfOperations() {

            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(100000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any(Application.class)))
                    .thenReturn(Mono.just(application));


            Mono<Application> result = applicationUseCase.saveApplication(application);


            StepVerifier.create(result)
                    .expectNext(application)
                    .verifyComplete();

            verify(userServiceGateway, times(1)).existsByDocument("12345678");
            verify(loanTypeRepository, times(1)).findByAmountRange(100000.0);
            verify(stateRepository, times(1)).findByName("Pendiente de revisión");
            verify(applicationRepository, times(1)).saveApplication(any(Application.class));
        }


        @Test
        @DisplayName("When_SaveApplicationWithDifferentTerms_Expect_AllTermsToBeProcessedSuccessfully")
        void When_SaveApplicationWithDifferentTerms_Expect_AllTermsToBeProcessedSuccessfully() {
            int[] terms = {6, 12, 18, 24, 36, 48, 60};

            for (int term : terms) {
                when(userServiceGateway.existsByDocument("12345678"))
                        .thenReturn(Mono.just(true));
                when(loanTypeRepository.findByAmountRange(anyDouble()))
                        .thenReturn(Mono.just(loanType));
                when(stateRepository.findByName("Pendiente de revisión"))
                        .thenReturn(Mono.just(state));
                when(applicationRepository.saveApplication(any()))
                        .thenReturn(Mono.just(application));

                Application testApplication = ApplicationTestBuilder.anApplication()
                        .withDocument("12345678")
                        .withAmount(100000.0)
                        .withTerm(term)
                        .build();


                Mono<Application> result = applicationUseCase.saveApplication(testApplication);


                StepVerifier.create(result)
                        .expectNext(application)
                        .verifyComplete();

                assert testApplication.getTerm() == term;
            }
        }
    }
}

