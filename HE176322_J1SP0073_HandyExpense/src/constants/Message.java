package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // Title and options of the main menu (the brief's screen).
    public static final String MENU = "=======Handy Expense program======\n"
            + "1. Add an expense\n"
            + "2. Display all expenses\n"
            + "3. Delete an expense\n"
            + "4. Quit";

    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Your choice: ";

    // Title of option 1.
    public static final String TITLE_ADD = "-------- Add an expense--------";

    // Title of option 2.
    public static final String TITLE_DISPLAY = "---------Display all expenses------------";

    // Title of option 3.
    public static final String TITLE_DELETE = "--------Delete an expense------";

    // Prompt for the date.
    public static final String INPUT_DATE = "Enter Date: ";

    // Prompt for the amount.
    public static final String INPUT_AMOUNT = "Enter Amount: ";

    // Prompt for the content.
    public static final String INPUT_CONTENT = "Enter Content: ";

    // Prompt for the ID to delete.
    public static final String INPUT_ID = "Enter ID: ";

    // Header of the ID column.
    public static final String LABEL_ID = "ID";

    // Header of the date column.
    public static final String LABEL_DATE = "Date";

    // Header of the amount column.
    public static final String LABEL_AMOUNT = "Amount";

    // Header of the content column.
    public static final String LABEL_CONTENT = "Content";

    // The line under the table; %s is the total, already written as money.
    public static final String TOTAL_FORMAT = "Total: %s";

    // A value that must be a whole number was not one.
    public static final String INVALID_NUMBER = "You must input a number.";

    // Menu choice out of range; %d are the bounds.
    public static final String INVALID_RANGE = "Please input a number in [%d, %d].";

    // The date is not a real dd-MMM-yyyy date.
    public static final String INVALID_DATE
            = "Date must be in format dd-MMM-yyyy, e.g. 11-Apr-2009.";

    // The amount is not a number.
    public static final String INVALID_AMOUNT = "Amount must be a number.";

    // The amount is 0 or negative.
    public static final String AMOUNT_POSITIVE = "Amount must be greater than 0.";

    // The content was left blank.
    public static final String EMPTY_FIELD = "This field must not be empty.";

    // Shown after an expense is added.
    public static final String ADD_SUCCESS = "Add an expense successful";

    // The expense could not be saved to the file; %s is the reason.
    public static final String ADD_FAIL = "Add an expense fail: %s";

    // The brief: the ID does not exist (or the file could not be saved).
    public static final String DELETE_FAIL = "Delete an expense fail";

    // The brief: the expense was deleted.
    public static final String DELETE_SUCCESS = "Delete an expense successful";

    // Shown by option 2 when the book is empty.
    public static final String NO_EXPENSE = "There is no expense to display.";

    // The data file exists but cannot be read; %s is the reason.
    public static final String LOAD_FAIL = "Cannot read expenses.txt: %s";

    // Shown when the user quits.
    public static final String GOODBYE = "Bye.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
