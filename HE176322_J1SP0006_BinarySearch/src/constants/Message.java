package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // Prompt for the size of the array (the brief's own wording).
    public static final String INPUT_SIZE = "Enter number of array:";

    // Prompt for the value to search (the brief's own wording).
    public static final String INPUT_SEARCH = "Enter search value:";

    // The line typed was not a whole number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // The number is outside the allowed range; %d are the bounds.
    public static final String INVALID_RANGE = "Number must be between %d and %d.";

    // The array after sorting (the brief's screen); %s is the array as text.
    public static final String SORTED_ARRAY = "Sorted array: %s";

    // Result when the value is found; %d are the value and its index.
    public static final String FOUND = "Found %d at index: %d";

    // Result when the value is absent; %d is the value searched for.
    public static final String NOT_FOUND = "%d is not in the array.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
