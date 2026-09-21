package constants;

/**
 * Numbers and patterns the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;

    // Menu option 1: count a word in a file.
    public static final int MENU_COUNT_WORD = 1;

    // Menu option 2: find the files that contain a word.
    public static final int MENU_FIND_FILE = 2;

    // Menu option 3: exit; also the largest option.
    public static final int MENU_EXIT = 3;

    // What separates two words: any run of characters that are not a letter (Vietnamese
    // letters included, \p{L}) or a digit (\p{N}).
    public static final String WORD_SPLIT = "[^\\p{L}\\p{N}]+";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
