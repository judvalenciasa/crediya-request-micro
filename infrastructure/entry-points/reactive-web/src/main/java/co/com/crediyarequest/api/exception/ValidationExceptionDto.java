package co.com.crediyarequest.api.exception;

import org.springframework.validation.Errors;

public class ValidationExceptionDto extends RuntimeException{
    private final Errors errors;

    public ValidationExceptionDto(Errors errors) {
        super("Error de validación");
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
