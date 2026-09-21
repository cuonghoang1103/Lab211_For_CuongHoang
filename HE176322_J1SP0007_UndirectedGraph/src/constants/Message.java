package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // Prompt for the first vertex (the brief's screen; value on next line).
    public static final String INPUT_START = "Enter the start point:";

    // Prompt for the second vertex (the brief's screen).
    public static final String INPUT_END = "Enter the end point:";

    // Result when the two points are joined by an edge.
    public static final String IS_EDGE = "This is  an edge";

    // Result when the two points are not joined (same sentence with "not").
    public static final String NOT_EDGE = "This is not an edge";

    // The line typed was not a whole number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // The number is not a vertex label; %d are the bounds.
    public static final String INVALID_RANGE = "Value must be between %d and %d.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
