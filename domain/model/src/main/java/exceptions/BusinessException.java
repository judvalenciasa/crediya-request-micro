package exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String mesage) {
        super(mesage);
    }
}
