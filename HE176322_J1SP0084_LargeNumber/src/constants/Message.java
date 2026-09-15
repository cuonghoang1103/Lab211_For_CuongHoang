package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // Prompt for the first number (the brief's screen, space before ':').
    public static final String INPUT_FIRST = "Enter the first number : ";
    // Prompt for the second number (the brief's screen).
    public static final String INPUT_SECOND = "Enter the second number: ";
    // The line typed is not a whole number made of digits only.
    public static final String INVALID_DIGIT = "You must input digit.";
    // The result line: first x second = product.
    public static final String RESULT = "%s x %s = %s";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
