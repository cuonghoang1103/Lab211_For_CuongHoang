package constants;

/**
 * Numbers, extensions and patterns the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;

    // Menu option 1: check a path.
    public static final int MENU_CHECK_PATH = 1;

    // Menu option 2: list the .java files of a directory.
    public static final int MENU_JAVA_FILES = 2;

    // Menu option 3: list the files bigger than n KB.
    public static final int MENU_BIG_FILES = 3;

    // Menu option 4: append content to a file.
    public static final int MENU_APPEND = 4;

    // Menu option 5: count the words of a file.
    public static final int MENU_COUNT = 5;

    // Menu option 6: exit; also the largest option.
    public static final int MENU_EXIT = 6;

    // Bytes in one kilobyte: the size of option 3 is typed in KB.
    public static final long KILOBYTE = 1024L;

    // Extension of the files option 2 lists.
    public static final String JAVA_EXTENSION = ".java";

    // Words are separated by one or more whitespace characters.
    public static final String WORD_SEPARATOR = "\\s+";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
