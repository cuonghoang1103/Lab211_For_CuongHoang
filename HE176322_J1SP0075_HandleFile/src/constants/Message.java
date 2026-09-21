package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu (the brief's screen) -----
    // Title and options of the main menu.
    public static final String MENU = "============ File Processing =========\n"
            + "1. Check Path\n"
            + "2. Get file name with type java\n"
            + "3. Get file with size greater than input\n"
            + "4. Write more content to file\n"
            + "5. Read file and count characters\n"
            + "6. Exit";

    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Please choice one option:";

    // ----- screen titles -----
    // Title of option 1.
    public static final String TITLE_CHECK_PATH = "---------- Check Path ---------";

    // Title of option 2.
    public static final String TITLE_JAVA_FILES
            = "------- Get file name with type java --------";

    // Title of option 3.
    public static final String TITLE_BIG_FILES
            = "--------- Get file with size greater than input --------";

    // Title of option 4.
    public static final String TITLE_APPEND = "------ Write more content to file ----";

    // Title of option 5 (the brief's spelling "an count").
    public static final String TITLE_COUNT = "---- Read file an count characters ----";

    // ----- prompts -----
    // Prompt for a path.
    public static final String INPUT_PATH = "Enter Path:";

    // Prompt for the size n in KB.
    public static final String INPUT_SIZE = "Enter Size(Integer):";

    // Prompt for the content to append.
    public static final String INPUT_CONTENT = "Enter Content:";

    // ----- results -----
    // Option 1: the path is a file (the brief).
    public static final String PATH_TO_FILE = "Path to file";

    // Option 1: the path is a directory (the brief).
    public static final String PATH_TO_DIRECTORY = "Path to Directory";

    // Option 2 summary; %d is the number of .java files.
    public static final String RESULT_JAVA_FILES = "Result %d file!";

    // Option 3 summary; %d is the number of big files.
    public static final String RESULT_BIG_FILES = "Result %d files!";

    // Option 4 success.
    public static final String WRITE_DONE = "Write done";

    // Option 5 result; %d is the number of words.
    public static final String TOTAL = "Total:%d";

    // ----- errors -----
    // The brief's error for every function.
    public static final String PATH_NOT_EXIST = "Path doesn't exist";

    // The brief's error when n is not a number.
    public static final String INVALID_SIZE = "Value of size is digit";

    // The menu choice was not a number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // Menu choice out of range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // The path exists but cannot be read as a text file.
    public static final String CANNOT_READ = "Cannot read file";

    // The path exists but cannot be written as a file.
    public static final String CANNOT_WRITE = "Cannot write to file";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
