package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title (the brief's box) and options of the main menu.
    public static final String MENU = "============ Copy Program =========\n"
            + "1. Copy File\n"
            + "2. Input Configure File\n"
            + "3. Exit";

    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Your choice: ";

    // The menu choice was not a number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // The menu choice is outside the menu; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // ----- box 2 and 3 of the brief: no config file -----
    // The config file does not exist yet.
    public static final String CONFIG_NOT_FOUND = "File Configure is not found!";

    // Title of the config form.
    public static final String TITLE_INPUT = "---- Input Configure File -----";

    // Prompt for COPY_FOLDER.
    public static final String INPUT_COPY_FOLDER = "Copy Folder:";

    // Prompt for DATA_TYPE.
    public static final String INPUT_DATA_TYPE = "Data Type:";

    // Prompt for PATH.
    public static final String INPUT_PATH = "Path:";

    // The config file could not be written.
    public static final String CANNOT_CREATE = "File Configure cannot create";

    // Printed after every config error; then the program stops.
    public static final String SYSTEM_SHUTDOWN = "System shutdown";

    // ----- box 4 of the brief: checking the config -----
    // Title of the check.
    public static final String TITLE_CHECK = "---- Check Configure File -----";

    // The config file exists but cannot be read.
    public static final String CANNOT_READ = "Can't read File Configure";

    // COPY_FOLDER is blank.
    public static final String SOURCE_NOT_INPUT = "Folder Source is not input";

    // COPY_FOLDER is not an existing folder.
    public static final String SOURCE_NOT_FOUND = "Can't find folder Source";

    // DATA_TYPE is blank.
    public static final String DATA_TYPE_NOT_INPUT = "Data type is not input";

    // PATH is blank.
    public static final String DESTINATION_NOT_INPUT = "Folder Destination is not input";

    // PATH does not exist and cannot be created.
    public static final String CANNOT_MAKE_DESTINATION = "Can't make folder Destination";

    // PATH is the source folder itself (not in the brief: copying a file onto itself
    // empties it, so this case must be refused).
    public static final String SAME_FOLDER
            = "Folder Source and Folder Destination are the same";

    // ----- box 5 of the brief: the copy -----
    // Printed before the list of copied files.
    public static final String COPY_RUNNING = "Copy is running...";

    // Title of the list of copied files.
    public static final String TITLE_FILE_NAME = "------------ File Name ------------";

    // Printed after the list of copied files.
    public static final String COPY_FINISHED = "Copy is finished...";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
