package co.com.crediyarequest.api.requestdto.application;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ApplicationCreateRequestDto(
        @NotNull(message = "Amount is required")
        @Digits(integer = 9, fraction = 2, message = "Amount must be a number with up to 9 digits before decimal and exactly 2 decimal places")
        double amount,

        @NotNull(message = "Term is required in moths")
        int term,

        @NotBlank(message = "Document is required")
        @Size(max = 100, message = "Name must not exceed 20 characters")
        String document
) {
}
