package constants;

/**
 * Every message and label the program shows on screen, copied from the brief's screens.
 *
 * @author HE176322
 */
public final class Message {

    // Title and options of the main menu.
    public static final String MENU = "========= Equation Program =========\n"
            + "1. Calculate Superlative Equation\n"
            + "2. Calculate Quadratic Equation\n"
            + "3. Exit";

    // Prompt for the menu choice (the brief's wording).
    public static final String INPUT_CHOICE = "Please choice one option: ";

    // Title of option 1 (the brief titles it "Calculate Equation").
    public static final String TITLE_SUPERLATIVE = "----- Calculate Equation -----";

    // Title of option 2.
    public static final String TITLE_QUADRATIC = "----- Calculate Quadratic Equation -----";

    // Prompt for A.
    public static final String INPUT_A = "Enter A: ";

    // Prompt for B.
    public static final String INPUT_B = "Enter B: ";

    // Prompt for C.
    public static final String INPUT_C = "Enter C: ";

    // A coefficient or a menu choice was not a number (the brief).
    public static final String INVALID_NUMBER = "Please input number";

    // Menu choice outside the allowed range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // The equation has no solution (the brief: null).
    public static final String NO_SOLUTION = "The equation has no solution.";

    // Every x is a solution (the brief: empty list).
    public static final String INFINITE_SOLUTIONS
            = "The equation has infinitely many solutions.";

    // One root, three decimals.
    public static final String ONE_SOLUTION = "Solution: x = %.3f";

    // Two roots, three decimals.
    public static final String TWO_SOLUTIONS = "Solution: x1 = %.3f and x2 = %.3f";

    // Line of the odd numbers on the superlative screen; %s is the list of numbers.
    public static final String LABEL_ODD = "Number is Odd:%s";

    // Line of the odd numbers on the quadratic screen (the brief differs); %s is the list.
    public static final String LABEL_ODD_QUADRATIC = "Odd Number(s):%s";

    // Line of the even numbers; %s is the list of numbers.
    public static final String LABEL_EVEN = "Number is Even:%s";

    // Line of the perfect squares; %s is the list of numbers.
    public static final String LABEL_SQUARE = "Number is Perfect Square:%s";

    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
