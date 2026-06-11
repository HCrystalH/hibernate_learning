package fa.training.exception;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable ex) {
        super(message,ex);
    }
}
