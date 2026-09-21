package constants;

/**
 * Numbers and formats the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;

    // Menu option: superlative equation.
    public static final int MENU_SUPERLATIVE = 1;

    // Menu option: quadratic equation.
    public static final int MENU_QUADRATIC = 2;

    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 3;

    // Position of coefficient A in the list of coefficients.
    public static final int INDEX_A = 0;

    // Position of coefficient B in the list of coefficients.
    public static final int INDEX_B = 1;

    // Position of coefficient C in the list of coefficients (quadratic equation only).
    public static final int INDEX_C = 2;

    // The 4 of delta = b*b - 4*a*c.
    public static final int DELTA_FACTOR = 4;

    // How many roots a quadratic with a != 0 and delta >= 0 has.
    public static final int TWO_ROOTS = 2;

    // Separator between the numbers of one line.
    public static final String SEPARATOR = ", ";

    // Text of an equation for toString(): the coefficients, then the roots.
    public static final String EQUATION_FORMAT = "%s -> %s";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
