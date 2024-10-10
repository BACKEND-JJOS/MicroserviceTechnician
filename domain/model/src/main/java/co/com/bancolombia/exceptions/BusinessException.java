package co.com.bancolombia.exceptions;

public class BusinessException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "A business error occurred";

    public BusinessException(String message) {
        super(message);
    }
}
