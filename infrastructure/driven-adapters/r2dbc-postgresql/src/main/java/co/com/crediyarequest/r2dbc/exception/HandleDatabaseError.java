package co.com.crediyarequest.r2dbc.exception;


public class HandleDatabaseError extends RuntimeException {

    public HandleDatabaseError(String mesage) {
        super(mesage);
    }
}