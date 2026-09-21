package constants;

/**
 * Numbers and symbols the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Menu choice that closes the program (input base only).
    public static final int MENU_EXIT = 0;

    // Smallest menu number of a base.
    public static final int BASE_MIN = 1;

    // Largest menu number of a base.
    public static final int BASE_MAX = 3;

    // Digit symbols by value: index 10 is 'A'.
    public static final String DIGITS = "0123456789ABCDEF";

    // Sign of a negative value.
    public static final String MINUS = "-";

    // Optional sign of a positive value.
    public static final String PLUS = "+";

    // The digit zero, the one value that the division loop cannot write.
    public static final String ZERO = "0";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
