package exceptions;

/**
 * The exception class the brief names ExceptionHandle: every config error travels as one
 * whose message is one line of the brief's error box. Its name ends with "Exception"
 * (checklist 1.3).
 *
 * @author HE176322
 */
// brief: ExceptionHandle
public class HandleException extends Exception {

    // Creates the exception with no message.
    public HandleException() {
        super();
    }

    // Creates the exception with the message main will print.
    public HandleException(String message) {
        super(message);
    }
}
