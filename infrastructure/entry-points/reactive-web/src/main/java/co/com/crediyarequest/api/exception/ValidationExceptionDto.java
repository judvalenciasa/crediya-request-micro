package co.com.crediyarequest.api.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class ValidationExceptionDto extends RuntimeException{
    private final Errors errors;

    public ValidationExceptionDto(Errors errors) {
        super("Error de validación");
        this.errors = errors;
    }

}
