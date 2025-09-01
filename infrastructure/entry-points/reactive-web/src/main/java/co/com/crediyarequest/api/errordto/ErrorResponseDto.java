package co.com.crediyarequest.api.errordto;

public record ErrorResponseDto(
        String message,
        String code,
        String timestamp
) {
}
