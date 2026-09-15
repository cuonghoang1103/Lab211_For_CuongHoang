package constants;

/**
 * Numbers and names the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;
    // Menu option: zip a folder.
    public static final int MENU_COMPRESS = 1;
    // Menu option: unzip a file.
    public static final int MENU_EXTRACT = 2;
    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 3;

    // Extension added to the zip name when the user leaves it out.
    public static final String ZIP_EXTENSION = ".zip";
    // Bytes moved per read/write.
    public static final int BUFFER_SIZE = 8192;
    // read() returns this when the stream has no more bytes.
    public static final int END_OF_STREAM = -1;

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
