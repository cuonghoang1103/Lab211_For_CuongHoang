package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title and options of the main menu.
    public static final String MENU = "======== Worker Management =========\n"
            + "1. Add Worker\n"
            + "2. Up salary\n"
            + "3. Down salary\n"
            + "4. Display Information salary\n"
            + "5. Exit";

    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Enter your choice:";

    // ----- screen titles (the brief's own) -----
    // Title of the add-worker form.
    public static final String TITLE_ADD = "--------- Add Worker ----------";

    // Title of the up/down salary form.
    public static final String TITLE_CHANGE = "------- Up/Down Salary --------";

    // Title of the salary table.
    public static final String TITLE_DISPLAY
            = "--------------------Display Information Salary-----------------------";

    // ----- prompts (the brief's own) -----
    // Prompt for the worker code.
    public static final String INPUT_CODE = "Enter Code:";

    // Prompt for the worker name.
    public static final String INPUT_NAME = "Enter Name:";

    // Prompt for the worker age.
    public static final String INPUT_AGE = "Enter Age:";

    // Prompt for the starting salary (the brief has a space after it).
    public static final String INPUT_SALARY = "Enter Salary: ";

    // Prompt for the work location.
    public static final String INPUT_LOCATION = "Enter work location:";

    // Prompt for the amount to raise or cut (the brief: "Enter Salary:").
    public static final String INPUT_AMOUNT = "Enter Salary:";

    // ----- table header labels -----
    // Header of the code column.
    public static final String LABEL_CODE = "Code";

    // Header of the name column.
    public static final String LABEL_NAME = "Name";

    // Header of the age column.
    public static final String LABEL_AGE = "Age";

    // Header of the salary column.
    public static final String LABEL_SALARY = "Salary";

    // Header of the status column.
    public static final String LABEL_STATUS = "Status";

    // Header of the date column.
    public static final String LABEL_DATE = "Date";

    // ----- input errors -----
    // A value that must be a number was not a number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // Menu choice outside the allowed range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // ----- business errors (the brief's rules) -----
    // The brief: Code(id) cannot be null.
    public static final String CODE_NULL = "Code cannot be null.";

    // The brief: the code cannot be duplicated; %s is the code.
    public static final String CODE_DUPLICATE = "Code [%s] already exists.";

    // The brief: age must be in range 18 to 50; %d are the bounds.
    public static final String AGE_RANGE = "Age must be in range %d to %d";

    // The brief: salary must be greater than 0.
    public static final String SALARY_POSITIVE = "Salary must be greater than 0";

    // The brief: Code(id) must exist in DB; %s is the code.
    public static final String CODE_NOT_EXIST = "Code [%s] does not exist.";

    // The brief: amount of money must be > 0.
    public static final String AMOUNT_POSITIVE = "Amount of money must be > 0";

    // ----- results -----
    // Shown after a worker is added; %s is the code.
    public static final String ADD_SUCCESS = "Worker [%s] has been added.";

    // Shown after a salary is raised or cut.
    public static final String CHANGE_SUCCESS = "Salary has been adjusted.";

    // Shown when no salary has been adjusted yet.
    public static final String NO_HISTORY = "No salary has been adjusted yet.";

    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
