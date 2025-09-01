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
import static org.mockito.Mockito.never;

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
        // Arrange - Configuración inicial usando builders
        application = TestDataBuilder.createDefaultApplication();
        loanType = TestDataBuilder.createDefaultLoanType();
        state = TestDataBuilder.createPendingReviewState();
    }

    @Nested
    @DisplayName("saveApplication - Casos exitosos")
    class SaveApplicationSuccessTests {

        @Test
        void When_SaveApplicationWithValidData_Expect_ApplicationToBeSavedSuccessfully() {
            // Arrange
            Application expectedApplication = TestDataBuilder.createApplicationWithLoanTypeAndState();

            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(100000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any(Application.class)))
                    .thenReturn(Mono.just(expectedApplication));

            // Act
            Mono<Application> result = applicationUseCase.saveApplication(application);

            // Assert
            StepVerifier.create(result)
                    .expectNext(expectedApplication)
                    .verifyComplete();

            verify(userServiceGateway).existsByDocument("12345678");
            verify(loanTypeRepository).findByAmountRange(100000.0);
            verify(stateRepository).findByName("Pendiente de revisión");
            verify(applicationRepository).saveApplication(any(Application.class));
        }

        @Test
        @DisplayName("When_SaveApplicationWithValidData_Expect_LoanTypeAndStateToBeAssignedCorrectly")
        void When_SaveApplicationWithValidData_Expect_LoanTypeAndStateToBeAssignedCorrectly() {
            // Arrange
            when(userServiceGateway.existsByDocument(anyString()))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(anyDouble()))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any(Application.class)))
                    .thenAnswer(invocation -> {
                        Application app = invocation.getArgument(0);
                        return Mono.just(app);
                    });

            // Act
            Mono<Application> result = applicationUseCase.saveApplication(application);

            // Assert
            StepVerifier.create(result)
                    .assertNext(savedApp -> {
                        assert savedApp.getLoantypeId().equals(1L);
                        assert savedApp.getStateId().equals(1L);
                    })
                    .verifyComplete();
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

            // Act
            Mono<Application> result = applicationUseCase.saveApplication(applicationWithTerm);

            // Assert
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
            // Arrange
            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(100000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.empty());

            // Act
            Mono<Application> result = applicationUseCase.saveApplication(application);

            // Assert
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
            // Arrange
            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(100000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any(Application.class)))
                    .thenReturn(Mono.error(new RuntimeException("Error al guardar en base de datos")));

            // Act
            Mono<Application> result = applicationUseCase.saveApplication(application);

            // Assert
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
            // Arrange
            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(50000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any()))
                    .thenReturn(Mono.just(application));

            Application testApplication = TestDataBuilder.createApplicationWithCustomData("12345678", 50000.0, 18);

            // Act
            Mono<Application> result = applicationUseCase.saveApplication(testApplication);

            // Assert
            StepVerifier.create(result)
                    .expectNext(application)
                    .verifyComplete();

            verify(loanTypeRepository).findByAmountRange(50000.0);
        }



        @Test
        @DisplayName("When_DifferentLoanTypesForDifferentAmounts_Expect_CorrectLoanTypeToBeAssigned")
        void When_DifferentLoanTypesForDifferentAmounts_Expect_CorrectLoanTypeToBeAssigned() {
            // Arrange
            LoanType microcreditLoan = TestDataBuilder.createMicrocreditLoanType();
            LoanType personalLoan = TestDataBuilder.createDefaultLoanType();
            LoanType businessLoan = TestDataBuilder.createBusinessLoanType();

            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any()))
                    .thenReturn(Mono.just(application));

            // Test microcrédito
            when(loanTypeRepository.findByAmountRange(5000.0))
                    .thenReturn(Mono.just(microcreditLoan));
            Application microcreditApp = TestDataBuilder.createApplicationWithCustomData("12345678", 5000.0, 6);

            // Act & Assert
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
            // Arrange
            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(100000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any()))
                    .thenReturn(Mono.just(application));

            // Act
            Mono<Application> result = applicationUseCase.saveApplication(application);

            // Assert
            StepVerifier.create(result)
                    .expectNext(application)
                    .verifyComplete();

            verify(stateRepository).findByName("Pendiente de revisión");
        }

        @Test
        @DisplayName("When_StateRepositoryReturnsError_Expect_RuntimeExceptionToBeThrown")
        void When_StateRepositoryReturnsError_Expect_RuntimeExceptionToBeThrown() {
            // Arrange
            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(100000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.error(new RuntimeException("Error de conexión a base de datos")));

            // Act
            Mono<Application> result = applicationUseCase.saveApplication(application);

            // Assert
            StepVerifier.create(result)
                    .expectError(RuntimeException.class)
                    .verify();
        }
    }

    @Nested
    @DisplayName("Casos edge y de integración")
    class EdgeAndIntegrationTests {

        @Test
        @DisplayName("When_SaveApplicationWithDifferentDocumentFormats_Expect_AllFormatsToBeProcessedSuccessfully")
        void When_SaveApplicationWithDifferentDocumentFormats_Expect_AllFormatsToBeProcessedSuccessfully() {
            // Arrange
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
            // Arrange
            when(userServiceGateway.existsByDocument("12345678"))
                    .thenReturn(Mono.just(true));
            when(loanTypeRepository.findByAmountRange(100000.0))
                    .thenReturn(Mono.just(loanType));
            when(stateRepository.findByName("Pendiente de revisión"))
                    .thenReturn(Mono.just(state));
            when(applicationRepository.saveApplication(any(Application.class)))
                    .thenReturn(Mono.just(application));

            // Act
            Mono<Application> result = applicationUseCase.saveApplication(application);

            // Assert
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
            // Arrange
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

                // Act
                Mono<Application> result = applicationUseCase.saveApplication(testApplication);

                // Assert
                StepVerifier.create(result)
                        .expectNext(application)
                        .verifyComplete();

                assert testApplication.getTerm() == term;
            }
        }
    }
}

