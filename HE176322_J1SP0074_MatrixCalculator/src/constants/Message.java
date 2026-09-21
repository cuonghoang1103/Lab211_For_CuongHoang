package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu (the brief's screen) -----
    // Title and options of the main menu.
    public static final String MENU = "=======Calculator program======\n"
            + "1. Addition Matrix\n"
            + "2. Subtraction Matrix\n"
            + "3. Multiplication Matrix\n"
            + "4. Quit";

    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Your choice:";

    // ----- screen titles -----
    // Title of option 1.
    public static final String TITLE_ADDITION = "-------- Addition --------";

    // Title of option 2.
    public static final String TITLE_SUBTRACTION = "-------- Subtraction --------";

    // Title of option 3.
    public static final String TITLE_MULTIPLICATION = "-------- Multiplication --------";

    // Title above the result.
    public static final String TITLE_RESULT = "-------- Result --------";

    // ----- prompts; %d is the matrix number, then row and column -----
    // Prompt for the number of rows of a matrix.
    public static final String INPUT_ROW = "Enter Row Matrix %d:";

    // Prompt for the number of columns of a matrix.
    public static final String INPUT_COLUMN = "Enter Column Matrix %d:";

    // Prompt for one value.
    public static final String INPUT_VALUE = "Enter Matrix%d[%d][%d]:";

    // ----- result symbols -----
    // Printed between the two matrixes of an addition.
    public static final String SYMBOL_ADD = "+";

    // Printed between the two matrixes of a subtraction.
    public static final String SYMBOL_SUBTRACT = "-";

    // Printed between the two matrixes of a multiplication.
    public static final String SYMBOL_MULTIPLY = "*";

    // Printed before the result matrix.
    public static final String SYMBOL_EQUAL = "=";

    // ----- validation errors -----
    // The menu choice was not a number.
    public static final String INVALID_CHOICE = "Your choice must be a number.";

    // A number outside its range; %d are the bounds.
    public static final String INVALID_RANGE = "Please input a number in [%d, %d].";

    // A row/column count that is not a number.
    public static final String INVALID_SIZE = "Size must be a positive number.";

    // The brief's own wording for a value that is not a number.
    public static final String INVALID_VALUE = "Values of matrix must be the number";

    // ----- business errors -----
    // Addition/subtraction with two different shapes.
    public static final String SIZE_NOT_SAME
            = "Two matrixes must have the same number of rows and columns.";

    // Multiplication where columns of matrix 1 differ from rows of matrix 2.
    public static final String SIZE_NOT_MULTIPLY
            = "Number of columns of matrix 1 must equal number of rows of matrix 2.";

    // Shown when the user quits.
    public static final String GOODBYE = "Bye.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
