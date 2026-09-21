package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- screen of the brief -----
    // Title of the program (the brief's first line).
    public static final String TITLE = "============ Writer Program ===============";

    // Question before Function 1 (write).
    public static final String ASK_WRITE = "Do you want to write file? (Y/N or y/n):";

    // Question before Function 2 (read).
    public static final String ASK_READ = "Do you want to read file? (Y/N or y/n):";

    // Prompt for the file path, used by both functions.
    public static final String INPUT_PATH = "Please enter file path: ";

    // Tells the user how to finish typing the content.
    public static final String SAVE_HINT = "Save file with content <save> or <SAVE>";

    // Prompt before the content lines.
    public static final String INPUT_CONTENT = "Please enter file content:";

    // Shown after the content of the file (the brief's last line).
    public static final String READ_SUCCESS = "Read file successfully.";

    // ----- validation errors -----
    // The answer to a Y/N question was not Y, N, y or n.
    public static final String INVALID_YES_NO = "Please answer Y or N.";

    // The file path was left blank.
    public static final String PATH_EMPTY = "Path must not be empty.";

    // ----- file errors -----
    // The path to read is not an existing file.
    public static final String FILE_NOT_EXIST = "File does not exist.";

    // writeFile returned false (missing folder, read-only file...).
    public static final String CANNOT_WRITE = "Could not write the file.";

    // The file exists but could not be read.
    public static final String CANNOT_READ = "Could not read the file.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
