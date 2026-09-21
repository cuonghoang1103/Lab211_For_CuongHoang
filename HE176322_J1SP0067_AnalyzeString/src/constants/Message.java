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

    // ----- result lines -----
    // The brief: "Display the number of characters in the string"; %d is the count.
    public static final String RESULT_LENGTH = "Number of characters: %d";

    // Line of the perfect square numbers; %s is the list.
    public static final String RESULT_SQUARE = "Perfect Square Numbers: %s";

    // Line of the odd numbers; %s is the list.
    public static final String RESULT_ODD = "Odd Numbers: %s";

    // Line of the even numbers; %s is the list.
    public static final String RESULT_EVEN = "Even Numbers: %s";

    // Line of all numbers; %s is the list.
    public static final String RESULT_ALL_NUMBERS = "All Numbers: %s";

    // Line of the uppercase characters; %s is the characters.
    public static final String RESULT_UPPER = "Uppercase Characters: %s";

    // Line of the lowercase characters; %s is the characters.
    public static final String RESULT_LOWER = "Lowercase Characters: %s";

    // Line of the special characters; %s is the characters.
    public static final String RESULT_SPECIAL = "Special Characters: %s";

    // Line of all non-digit characters; %s is the characters.
    public static final String RESULT_ALL_CHARS = "All Characters: %s";

    // ----- validation errors -----
    // The string was left blank.
    public static final String INPUT_EMPTY = "Input must not be empty.";

    // A run of digits does not fit an Integer; %d is the largest int.
    public static final String NUMBER_TOO_BIG = "Each number in the string must be at most %d.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
