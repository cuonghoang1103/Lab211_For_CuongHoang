package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- title and prompts (the brief's screen, character for character) -----
    // Title printed when the program starts.
    public static final String TITLE = "=====Management Person programer=====";
    // Title printed before the three questions about one person.
    public static final String TITLE_INPUT = "Input Information of Person";
    // Prompt for the name.
    public static final String INPUT_NAME = "Please input name:";
    // Prompt for the address.
    public static final String INPUT_ADDRESS = "Please input address:";
    // Prompt for the salary.
    public static final String INPUT_SALARY = "Please input salary:";

    // ----- validation errors -----
    // The name was left blank (not in the brief; kept from the reference).
    public static final String NAME_EMPTY = "You must input name.";
    // The address was left blank (not in the brief; kept from the reference).
    public static final String ADDRESS_EMPTY = "You must input address.";
    // Brief, Function 1: the salary was left blank.
    public static final String SALARY_EMPTY = "You must input Salary.";
    // Brief, Function 1: the salary is not a number.
    public static final String SALARY_NOT_DIGIT = "You must input digit.";
    // Brief, Function 1: the salary is zero or negative.
    public static final String SALARY_NOT_POSITIVE = "Salary is greater than zero";
    // Brief, Function 3: the array to sort is missing or has a hole.
    public static final String CANNOT_SORT = "Can't Sort Person";

    // ----- result labels (the brief's screen) -----
    // Title of one person's block in the result.
    public static final String TITLE_PERSON = "Information of Person you have entered:";
    // Label in front of the name.
    public static final String LABEL_NAME = "Name:";
    // Label in front of the address.
    public static final String LABEL_ADDRESS = "Address:";
    // Label in front of the salary.
    public static final String LABEL_SALARY = "Salary:";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
