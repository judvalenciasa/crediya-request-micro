package co.com.crediyarequest.api.exception;


public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String mesage) {
        super(mesage);
    }
}
