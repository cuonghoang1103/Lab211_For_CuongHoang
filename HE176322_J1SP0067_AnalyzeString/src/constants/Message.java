package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- screen of the brief -----
    // Title of the program.
    public static final String TITLE = "===== Analysis String program ====";
    // Prompt for the string to analyse.
    public static final String INPUT_STRING = "Input String: ";
    // Title of the result block.
    public static final String TITLE_RESULT = "-----Result Analysis------";

    // ----- result labels -----
    // The brief: "Display the number of characters in the string".
    public static final String LABEL_LENGTH = "Number of characters: ";
    // Label of the perfect square numbers.
    public static final String LABEL_SQUARE = "Perfect Square Numbers: ";
    // Label of the odd numbers.
    public static final String LABEL_ODD = "Odd Numbers: ";
    // Label of the even numbers.
    public static final String LABEL_EVEN = "Even Numbers: ";
    // Label of all numbers.
    public static final String LABEL_ALL_NUMBERS = "All Numbers: ";
    // Label of the uppercase characters.
    public static final String LABEL_UPPER = "Uppercase Characters: ";
    // Label of the lowercase characters.
    public static final String LABEL_LOWER = "Lowercase Characters: ";
    // Label of the special characters.
    public static final String LABEL_SPECIAL = "Special Characters: ";
    // Label of all non-digit characters.
    public static final String LABEL_ALL_CHARS = "All Characters: ";

    // ----- validation errors -----
    // The string was left blank.
    public static final String INPUT_EMPTY = "Input must not be empty.";
    // A run of digits does not fit an Integer; %d is the largest int.
    public static final String NUMBER_TOO_BIG = "Each number in the string must be at most %d.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
