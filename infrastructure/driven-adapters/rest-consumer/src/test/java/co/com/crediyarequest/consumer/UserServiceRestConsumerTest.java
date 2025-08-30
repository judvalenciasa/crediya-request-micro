package co.com.crediyarequest.consumer;

import exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.SocketPolicy;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;


@DisplayName("UserServiceRestConsumer Tests")
class UserServiceRestConsumerTest {

    private static UserServiceRestConsumer userServiceRestConsumer;
    private static MockWebServer mockBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();

        WebClient webClient = WebClient.builder()
                .baseUrl(mockBackEnd.url("/").toString())
                .build();

        userServiceRestConsumer = new UserServiceRestConsumer(webClient); // ← Corregido aquí
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    @DisplayName("Should return true when user exists")
    void validateTestGet() {
        // Given
        String document = "12345678";
        mockBackEnd.enqueue(new MockResponse()
                .setBody("true")
                .addHeader("Content-Type", "application/json"));

        // When & Then
        StepVerifier.create(userServiceRestConsumer.existsByDocument(document))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should throw BusinessException when user not found")
    void validateTestGet_UserNotFound() {
        // Given
        String document = "99999999";
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody("Usuario no encontrado")
                .addHeader("Content-Type", "application/json"));

        // When & Then
        StepVerifier.create(userServiceRestConsumer.existsByDocument(document))
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    @DisplayName("Should throw BusinessException when server error")
    void validateTestGet_ServerError() {
        // Given
        String document = "12345678";
        mockBackEnd.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Error interno del servidor")
                .addHeader("Content-Type", "application/json"));

        // When & Then
        StepVerifier.create(userServiceRestConsumer.existsByDocument(document))
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    @DisplayName("Should throw BusinessException when network error")
    void validateTestGet_NetworkError() {
        // Given
        String document = "12345678";
        mockBackEnd.enqueue(new MockResponse()
                .setSocketPolicy(SocketPolicy.DISCONNECT_AT_START));

        // When & Then
        StepVerifier.create(userServiceRestConsumer.existsByDocument(document))
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    @DisplayName("Should throw BusinessException when invalid JSON response")
    void validateTestGet_InvalidJsonResponse() {
        // Given
        String document = "12345678";
        mockBackEnd.enqueue(new MockResponse()
                .setBody("invalid json")
                .addHeader("Content-Type", "application/json"));

        // When & Then
        StepVerifier.create(userServiceRestConsumer.existsByDocument(document))
                .expectError(BusinessException.class)
                .verify();
    }

    @Test
    @DisplayName("Should return false when user does not exist")
    void validateTestGet_UserDoesNotExist() {
        // Given
        String document = "87654321";
        mockBackEnd.enqueue(new MockResponse()
                .setBody("false")
                .addHeader("Content-Type", "application/json"));

        // When & Then
        StepVerifier.create(userServiceRestConsumer.existsByDocument(document))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle timeout gracefully")
    void validateTestGet_Timeout() {
        // Given
        String document = "12345678";
        mockBackEnd.enqueue(new MockResponse()
                .setSocketPolicy(SocketPolicy.NO_RESPONSE));

        // When & Then
        StepVerifier.create(userServiceRestConsumer.existsByDocument(document))
                .expectError(BusinessException.class)
                .verify();
    }
}