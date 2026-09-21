package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title and options of the main menu (the brief's screen).
    public static final String MENU = "========== File Processing =========\n"
            + "1. Find person info\n"
            + "2. Copy Text to new file\n"
            + "3. Exit";

    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Enter your choice: ";

    // ----- screen titles (the brief's screens) -----
    // Title printed before the person-info form.
    public static final String TITLE_PERSON = "--------- Person info ---------";

    // Title printed above the list of persons found.
    public static final String TITLE_RESULT = "------------- Result ----------";

    // Title printed before the copy form.
    public static final String TITLE_COPY = "------------- Copy text --------------";

    // ----- prompts (the brief's screens, spacing included) -----
    // Prompt for the path of the person file.
    public static final String INPUT_PATH = "Enter Path:";

    // Prompt for the minimum salary.
    public static final String INPUT_MONEY = "Enter Money:";

    // Prompt for the file to copy from.
    public static final String INPUT_SOURCE = "Enter Source: ";

    // Prompt for the file to copy into.
    public static final String INPUT_DESTINATION = "Enter new file name: ";

    // ----- table (the brief's screen: columns separated by tabs) -----
    // Header of the result table.
    public static final String HEADER = "Name\t\tAddress\tMoney";

    // Line naming the person with the most money; %s is the name.
    public static final String LABEL_MAX = "Max: %s";

    // Line naming the person with the least money; %s is the name.
    public static final String LABEL_MIN = "Min: %s";

    // ----- validation errors -----
    // A value that must be a number was not a number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // A menu choice outside the menu; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // A path or file name was left blank.
    public static final String BLANK = "You must input something.";

    // The brief: "The amount not less than 0".
    public static final String MONEY_NEGATIVE = "Money must not be less than 0.";

    // ----- file errors (the brief's own wording, character for character) -----
    // The file does not exist (brief, getPerson and copyWordOneTimes).
    public static final String PATH_NOT_EXIST = "Path doesn't exist";

    // The file exists but cannot be read (the brief's curly apostrophe).
    public static final String CANNOT_READ = "Can’t read file";

    // The new file cannot be written (the brief's curly apostrophe).
    public static final String CANNOT_WRITE = "Can’t write file";

    // ----- results -----
    // Shown when nobody earns at least the minimum salary.
    public static final String NO_PERSON = "No person found.";

    // Shown after the words are copied (the brief's screen).
    public static final String COPY_DONE = "Copy done...";

    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
