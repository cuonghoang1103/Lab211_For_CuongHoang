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
    // Label in front of the disk driver.
    public static final String LABEL_DISK = "Disk: ";
    // Label in front of the file extension.
    public static final String LABEL_EXTENSION = "Extension: ";
    // Label in front of the file name.
    public static final String LABEL_FILE_NAME = "File Name: ";
    // Label in front of the path to the file's folder.
    public static final String LABEL_PATH = "Path: ";
    // Label in front of the list of folders.
    public static final String LABEL_FOLDERS = "Folders: ";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
