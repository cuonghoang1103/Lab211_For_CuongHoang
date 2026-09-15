package exceptions;

/**
 * The brief's ExceptionCar: "inherits Exception class; pass the message content to the
 * constructor".
 *
 * @author HE176322
 */
public class ExceptionCar extends Exception {

    // Creates the exception with the reason of the refusal.
    public ExceptionCar(String message) {
        super(message);
    }
}
