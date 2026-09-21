package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu (the brief's screen) -----
    // Title and options of the main menu.
    public static final String MENU = "========= Bubble Sort program =========\n"
            + "1. Input Element\n"
            + "2. Sort Ascending\n"
            + "3. Sort Descending\n"
            + "4. Exit";

    // Prompt for the menu choice, word for word from the brief.
    public static final String INPUT_CHOICE = "Please choice one option:";

    // ----- option 1 -----
    // Title of option 1.
    public static final String TITLE_INPUT = "----- Input Element -----";

    // Line printed before the length is asked.
    public static final String TITLE_LENGTH = "Input Length Of Array";

    // Prompt for the length of the array.
    public static final String INPUT_LENGTH = "Enter Number: ";

    // Prompt for one element; %d is its position from 1.
    public static final String INPUT_ELEMENT = "Enter Number %d: ";

    // ----- options 2 and 3 -----
    // Title of option 2.
    public static final String TITLE_ASCENDING = "----- Ascending -----";

    // Title of option 3.
    public static final String TITLE_DESCENDING = "----- Descending -----";

    // ----- errors -----
    // Wrong length - the brief's exact text, including its missing space ("numberand"),
    // so the screen matches the brief character for character.
    public static final String INVALID_LENGTH
            = "Please input numberand number is greater than zero";

    // Length above the cap; %d is the cap.
    public static final String LENGTH_TOO_BIG = "Length must not be greater than %d.";

    // An element or a menu choice that is not an integer.
    public static final String INVALID_NUMBER = "Please input number";

    // Menu choice outside the menu; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // Option 2 or 3 chosen before option 1.
    public static final String ARRAY_EMPTY = "Please input the array first (option 1).";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
