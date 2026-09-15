package constants;

/**
 * Fixed values the program logic compares with.
 *
 * @author HE176322
 */
public final class Constants {

    // Answer "yes" to a Y/N question (y is accepted too).
    public static final String YES = "Y";
    // Answer "no" to a Y/N question (n is accepted too).
    public static final String NO = "N";
    // Stop word that ends the content, lower-case form (the brief).
    public static final String SAVE_LOWER = "save";
    // Stop word that ends the content, upper-case form (the brief).
    public static final String SAVE_UPPER = "SAVE";
    // Value of FileReader.read() at the end of the file.
    public static final int END_OF_FILE = -1;

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
