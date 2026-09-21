package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // Prompt for the text (the brief's own wording).
    public static final String INPUT_CONTENT = "Enter your content:";

    // The line typed was empty or only spaces: nothing to count.
    public static final String EMPTY_CONTENT = "Input must not be empty.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
