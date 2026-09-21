package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu (the brief's screen) -----
    // Title and options of the main menu.
    public static final String MENU = "============ Word Program =========\n"
            + "1. Count Word In File\n"
            + "2. Find File By Word\n"
            + "3. Exit";

    // Prompt for the menu choice (the brief's screen shows none).
    public static final String INPUT_CHOICE = "Your choice: ";

    // ----- screen titles -----
    // Title of option 1.
    public static final String TITLE_COUNT_WORD = "-------- Count Word --------";

    // Title of option 2.
    public static final String TITLE_FIND_FILE = "-------- Find File By Word --------";

    // Title above the found file names.
    public static final String TITLE_FILE_NAME = "------------ File Name ------------";

    // ----- prompts -----
    // Prompt for a file or folder path.
    public static final String INPUT_PATH = "Enter Path:";

    // Prompt for the word.
    public static final String INPUT_WORD = "Enter Word:";

    // ----- results -----
    // Option 1 result, the brief's own label; %d is the count.
    public static final String COUNT_RESULT = "Bout: %d";

    // Option 2 when no file contains the word.
    public static final String NO_FILE_FOUND = "(no file contains this word)";

    // ----- errors; %s is the path typed -----
    // The word was left blank.
    public static final String WORD_BLANK = "Word must not be blank.";

    // Option 1: nothing at the path.
    public static final String FILE_NOT_FOUND = "File not found: %s";

    // Option 1: the path is a folder.
    public static final String NOT_A_FILE = "Not a file: %s";

    // Option 2: nothing at the path.
    public static final String FOLDER_NOT_FOUND = "Folder not found: %s";

    // Option 2: the path is a file.
    public static final String NOT_A_FOLDER = "Not a folder: %s";

    // A file that exists but cannot be read.
    public static final String CANNOT_READ_FILE = "Cannot read file: %s";

    // A folder that exists but cannot be listed.
    public static final String CANNOT_READ_FOLDER = "Cannot read folder: %s";

    // The menu choice was not a number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // Menu choice out of range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
