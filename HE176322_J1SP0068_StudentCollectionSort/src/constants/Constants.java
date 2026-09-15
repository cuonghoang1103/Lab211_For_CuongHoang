package constants;

/**
 * Fixed values the program logic compares with.
 *
 * @author HE176322
 */
public final class Constants {

    // Lowest legal mark.
    public static final float MIN_MARK = 0f;
    // Highest legal mark (the brief's example uses 100).
    public static final float MAX_MARK = 100f;
    // Answer "yes" (y is accepted too).
    public static final String YES = "Y";
    // Answer "no" (n is accepted too).
    public static final String NO = "N";

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
