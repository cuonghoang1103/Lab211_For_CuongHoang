package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // Prompt for the size of the array (the brief's own wording, on its own line).
    public static final String INPUT_SIZE = "Enter number of array:";

    // The line typed was not a whole number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // The number is outside the allowed range; the two %d are the bounds.
    public static final String INVALID_RANGE = "Number must be between %d and %d.";

    // The array before sorting (the brief's screen); %s is the array as text.
    public static final String UNSORTED_ARRAY = "Unsorted array: %s";

    // The array after sorting (the brief's screen); %s is the array as text.
    public static final String SORTED_ARRAY = "Sorted array: %s";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
