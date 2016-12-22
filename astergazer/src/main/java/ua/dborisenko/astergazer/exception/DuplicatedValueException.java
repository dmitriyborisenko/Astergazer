package ua.dborisenko.astergazer.exception;

public class DuplicatedValueException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public DuplicatedValueException() {
        super();
    }
    public DuplicatedValueException(String message) {
        super(message);
    }
    
    public DuplicatedValueException(Throwable cause) {
        super(cause);
    }

    public DuplicatedValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
