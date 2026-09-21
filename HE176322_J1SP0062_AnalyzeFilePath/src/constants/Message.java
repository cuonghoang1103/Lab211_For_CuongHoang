package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- title and prompt (the brief's screen) -----
    // Title printed when the program starts.
    public static final String TITLE = "===== Analysis Path Program =====";

    // Prompt for the path; the brief puts the input on the next line.
    public static final String INPUT_PATH = "Please input Path:";

    // ----- validation errors -----
    // Nothing was typed (kept from the reference program).
    public static final String PATH_EMPTY = "Path must not be empty.";

    // The text is not a Windows path to a file.
    public static final String PATH_INVALID
            = "Path must be like C:\\Windows\\test.txt";

    // ----- result (the brief's screen) -----
    // Title printed above the five answers.
    public static final String TITLE_RESULT = "----- Result Analysis -----";

    // Line of the disk driver; %s is the disk.
    public static final String RESULT_DISK = "Disk: %s";

    // Line of the file extension; %s is the extension.
    public static final String RESULT_EXTENSION = "Extension: %s";

    // Line of the file name; %s is the name without its extension.
    public static final String RESULT_FILE_NAME = "File Name: %s";

    // Line of the path to the file's folder; %s is that path.
    public static final String RESULT_PATH = "Path: %s";

    // Line of the list of folders; %s is the list, such as "[Windows]".
    public static final String RESULT_FOLDERS = "Folders: %s";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
