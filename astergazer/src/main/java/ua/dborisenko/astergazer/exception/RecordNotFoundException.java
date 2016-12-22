package ua.dborisenko.astergazer.exception;

public class RecordNotFoundException extends NullPointerException {

    private static final long serialVersionUID = 1L;

    public RecordNotFoundException() {
        super();
    }
    public RecordNotFoundException(String message) {
        super(message);
    }
}
