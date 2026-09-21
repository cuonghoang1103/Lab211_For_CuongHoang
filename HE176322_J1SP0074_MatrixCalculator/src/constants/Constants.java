package constants;

/**
 * Numbers and layouts the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;

    // Menu option 1: addition.
    public static final int MENU_ADD = 1;

    // Menu option 2: subtraction.
    public static final int MENU_SUBTRACT = 2;

    // Menu option 3: multiplication.
    public static final int MENU_MULTIPLY = 3;

    // Menu option 4: quit; also the largest option.
    public static final int MENU_QUIT = 4;

    // Smallest number of rows/columns of a matrix.
    public static final int MIN_SIZE = 1;

    // Largest number of rows/columns.
    public static final int MAX_SIZE = 20;

    // Number of the first matrix, shown in "Enter Row Matrix 1:".
    public static final int FIRST_MATRIX = 1;

    // Number of the second matrix, shown in "Enter Row Matrix 2:".
    public static final int SECOND_MATRIX = 2;

    // One cell as the brief's screen writes it: [2][3][2].
    public static final String CELL_FORMAT = "[%d]";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
