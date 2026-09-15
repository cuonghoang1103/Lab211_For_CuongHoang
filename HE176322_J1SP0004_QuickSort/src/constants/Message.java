package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // Prompt for the size of the array (the brief's own wording).
    public static final String INPUT_SIZE = "Enter number of array:";
    // The line typed was not a whole number.
    public static final String INVALID_NUMBER = "You must input a number.";
    // The number is outside the allowed range; %d are the bounds.
    public static final String INVALID_RANGE = "Number must be between %d and %d.";
    // Label in front of the array before sorting.
    public static final String LABEL_UNSORTED = "Unsorted array: ";
    // Label in front of the array after sorting.
    public static final String LABEL_SORTED = "Sorted array: ";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
