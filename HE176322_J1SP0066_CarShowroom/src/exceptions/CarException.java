package exceptions;

/**
 * The brief's ExceptionCar: "inherits Exception class; pass the message content to the
 * constructor". Named CarException because the checklist (1.3) wants the name of an
 * exception class to END with "Exception".
 *
 * @author HE176322
 */
// brief: ExceptionCar
public class CarException extends Exception {

    // Creates the exception with the reason of the refusal.
    public CarException(String message) {
        super(message);
    }
}
