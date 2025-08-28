package co.com.crediyarequest.api.handler;


import co.com.crediyarequest.api.errordto.ErrorResponseDto;
import co.com.crediyarequest.api.exception.ValidationExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class GlobalExceptionHandler {
    public Mono<ServerResponse> handleError(Throwable error) {

        if (error instanceof ValidationExceptionDto) {
            return handleCustomValidationException((ValidationExceptionDto) error);
        }

        return handleGenericError(error);
    }

    private Mono<ServerResponse> handleCustomValidationException(ValidationExceptionDto ex) {
        String message = ex.getErrors().getAllErrors().get(0).getDefaultMessage();

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                message,
                "VAL002",
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        );
        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .bodyValue(errorResponse);
    }

    private Mono<ServerResponse> handleGenericError(Throwable ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "Ha ocurrido un error inesperado" + ex.getMessage(),
                "GEN001",
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        );
        return ServerResponse
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyValue(errorResponse);
    }

}
