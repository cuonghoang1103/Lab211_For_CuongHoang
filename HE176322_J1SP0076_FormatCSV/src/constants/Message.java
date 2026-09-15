package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu (the brief's screen) -----
    // Title and options of the main menu.
    public static final String MENU = "======= Format CSV Program =======\n"
            + "1. Import CSV\n"
            + "2. Format Address\n"
            + "3. Format Name\n"
            + "4. Export CSV\n"
            + "5. Exit";
    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Please choice one option:";

    // ----- screen titles -----
    // Title of option 1.
    public static final String TITLE_IMPORT = "--------- Import CSV -------";
    // Title of option 2.
    public static final String TITLE_FORMAT_ADDRESS = "--------- Format Address -------";
    // Title of option 3.
    public static final String TITLE_FORMAT_NAME = "--------- Format Name -------";
    // Title of option 4.
    public static final String TITLE_EXPORT = "--------- Export CSV ------";

    // ----- prompts -----
    // Prompt for a file path (import and export).
    public static final String INPUT_PATH = "Enter Path:";

    // ----- results -----
    // Option 1 success.
    public static final String IMPORT_DONE = "Import: Done";
    // Options 2 and 3 success.
    public static final String FORMAT_DONE = "Format: Done";
    // Option 4 success.
    public static final String EXPORT_DONE = "Export: Done";

    // ----- errors -----
    // Option 1: nothing at the path.
    public static final String PATH_NOT_EXIST = "Path doesn't exist";
    // Option 1: the path exists but is not a readable file (a folder).
    public static final String CANNOT_READ = "Cannot read file";
    // Option 4: the file cannot be written (a folder, read-only...).
    public static final String CANNOT_WRITE = "Cannot write file";
    // Options 2-4 before any import.
    public static final String NO_DATA = "No CSV file has been imported";
    // The menu choice was not a number.
    public static final String INVALID_NUMBER = "You must input a number.";
    // Menu choice out of range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
