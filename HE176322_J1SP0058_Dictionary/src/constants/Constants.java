package constants;

/**
 * Numbers, file names and formats the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;
    // Menu option: add a word.
    public static final int MENU_ADD = 1;
    // Menu option: delete a word.
    public static final int MENU_DELETE = 2;
    // Menu option: translate a word.
    public static final int MENU_TRANSLATE = 3;
    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 4;

    // The dictionary file, at the project root (next to build.xml).
    public static final String DATA_FILE = "dictionary.txt";
    // Separates the English word from its meaning on one line of the file.
    public static final String SEPARATOR = "=";
    // The line of the file is split into at most this many parts.
    public static final int LINE_PARTS = 2;

    // Answer meaning "yes, update the meaning".
    public static final String YES = "Y";
    // Answer meaning "no, keep the old meaning".
    public static final String NO = "N";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
