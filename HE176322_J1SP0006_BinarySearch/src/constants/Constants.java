package constants;

/**
 * Numbers the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest array size: the brief asks for a positive number.
    public static final int MIN_SIZE = 1;

    // Largest array size, so a typo such as 99999999 cannot exhaust memory.
    public static final int MAX_SIZE = 1000;

    // The index a search returns when the value is absent.
    public static final int NOT_FOUND = -1;

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
