package ua.dborisenko.astergazer.exception;

public class BlockNotFoundException extends NullPointerException {

    private static final long serialVersionUID = 1L;

    public BlockNotFoundException() {
        super();
    }

    public BlockNotFoundException(String message) {
        super(message);
    }
}
