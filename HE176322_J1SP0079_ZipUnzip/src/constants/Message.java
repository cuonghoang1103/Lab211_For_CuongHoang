package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title and options of the main menu.
    public static final String MENU = "========= Zipper program =========\n"
            + "1. Compression\n"
            + "2. Extraction\n"
            + "3. Exit";

    // Prompt for the menu choice (the brief's wording).
    public static final String INPUT_CHOICE = "Please choice one option: ";

    // The menu choice was not a number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // The menu choice is outside the menu; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // A path or a name was left blank.
    public static final String VALUE_BLANK = "You must input a value.";

    // ----- screens -----
    // Title of option 1.
    public static final String TITLE_COMPRESSION = "---------- Compression --------";

    // Title of option 2.
    public static final String TITLE_EXTRACTION = "---------- Extraction ---------";

    // Prompt for the folder to zip.
    public static final String INPUT_SOURCE_FOLDER = "Enter Source Folder: ";

    // Prompt for the folder that receives the zip / the unzipped files.
    public static final String INPUT_DESTINATION = "Enter Destination Folder: ";

    // Prompt for the zip file name.
    public static final String INPUT_NAME = "Enter Name: ";

    // Prompt for the zip file to unzip.
    public static final String INPUT_SOURCE_FILE = "Enter Source file: ";

    // ----- result -----
    // Title of the result.
    public static final String TITLE_RESULT = "------------ Result -----------";

    // One zipped/unzipped file; %s is its name inside the zip.
    public static final String FILE_NAME = "File name %s";

    // The job succeeded.
    public static final String SUCCESS = "Successfully";

    // The job failed (printed after the reason).
    public static final String FAILED = "Failed";

    // ----- reasons of a failure; %s is the path -----
    // The folder to zip does not exist.
    public static final String SOURCE_FOLDER_NOT_EXIST = "Source folder does not exist: %s";

    // The zip file to unzip does not exist.
    public static final String ZIP_NOT_EXIST = "Zip file does not exist: %s";

    // The destination folder is missing and cannot be created.
    public static final String CANNOT_CREATE_FOLDER = "Cannot create destination folder: %s";

    // The file is not a zip, or a zip that was never finished.
    public static final String NOT_ZIP = "Not a zip file: %s";

    // A valid zip that holds no entry at all.
    public static final String NO_ENTRY = "There is no file to extract in: %s";

    // Zip slip: an entry would be written outside the destination.
    public static final String ENTRY_OUTSIDE = "Entry is outside the destination folder: %s";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
