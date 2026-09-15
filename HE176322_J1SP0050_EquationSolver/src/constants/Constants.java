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

    // The 4 of delta = b*b - 4*a*c.
    public static final int DELTA_FACTOR = 4;
    // How many roots a quadratic with a != 0 and delta >= 0 has.
    public static final int TWO_ROOTS = 2;
    // Separator between the numbers of one line.
    public static final String SEPARATOR = ", ";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
