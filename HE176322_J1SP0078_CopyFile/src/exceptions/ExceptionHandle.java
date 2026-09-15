package exceptions;

/**
 * The exception class the brief names: every config error travels as an ExceptionHandle
 * whose message is one line of the brief's error box.
 *
 * @author HE176322
 */
public class ExceptionHandle extends Exception {

    // Creates the exception with no message.
    public ExceptionHandle() {
        super();
    }

    // Creates the exception with the message main will print.
    public ExceptionHandle(String message) {
        super(message);
    }
}
