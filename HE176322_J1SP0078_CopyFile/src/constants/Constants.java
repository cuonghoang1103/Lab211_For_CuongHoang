package constants;

/**
 * Numbers, file names and formats the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;

    // Menu option: read (or ask for) the config, check it and copy.
    public static final int MENU_COPY = 1;

    // Menu option: type a new config file.
    public static final int MENU_INPUT = 2;

    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 3;

    // The config file, at the project root (next to build.xml).
    public static final String CONFIG_FILE = "config.properties";

    // Key of the source folder in the config file.
    public static final String KEY_COPY_FOLDER = "COPY_FOLDER";

    // Key of the accepted file types in the config file.
    public static final String KEY_DATA_TYPE = "DATA_TYPE";

    // Key of the destination folder in the config file.
    public static final String KEY_PATH = "PATH";

    // Separates a key from its value on one line of the config file.
    public static final String KEY_VALUE_SEPARATOR = "=";

    // One line of the config file: the key, "=", the value.
    public static final String SETTING_FORMAT = "%s=%s";

    // A config line starting with this is a comment.
    public static final String COMMENT_PREFIX = "#";

    // Separates the file types inside DATA_TYPE.
    public static final String TYPE_SEPARATOR = ",";

    // The wildcard in front of a file type.
    public static final String WILDCARD = "*";

    // The dot in front of a file extension.
    public static final String EXTENSION_DOT = ".";

    // A file type without its dot, turned into an extension: "csv" -> ".csv".
    public static final String EXTENSION_FORMAT = ".%s";

    // How a Config turns into text: copy folder, data type, path.
    public static final String CONFIG_FORMAT = "COPY_FOLDER=%s, DATA_TYPE=%s, PATH=%s";

    // Bytes moved per read/write while copying a file.
    public static final int BUFFER_SIZE = 8192;

    // read() returns this when the stream has no more bytes.
    public static final int END_OF_STREAM = -1;

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
