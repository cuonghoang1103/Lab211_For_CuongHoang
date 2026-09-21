package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title and options of the main menu (the brief's screen).
    public static final String MENU = "========= Calculator Program =========\n"
            + "1. Normal Calculator\n"
            + "2. BMI Calculator\n"
            + "3. Exit";

    // Prompt for the menu choice (the brief's own wording).
    public static final String INPUT_CHOICE = "Please choice one option: ";

    // Menu choice not a whole number from min to max; %d are the bounds.
    public static final String INVALID_CHOICE = "Please input a number from %d to %d.";

    // ----- normal calculator -----
    // Title of the normal calculator.
    public static final String TITLE_NORMAL = "----- Normal Calculator -----";

    // Prompt for a number.
    public static final String INPUT_NUMBER = "Enter number: ";

    // Prompt for an operator.
    public static final String INPUT_OPERATOR = "Enter Operator: ";

    // A number field that is not numeric data.
    public static final String INVALID_NUMBER = "Number is digit";

    // An operator outside the list (the brief's screen).
    public static final String INVALID_OPERATOR = "Please input (+, -, *, /, ^)";

    // Division by zero (the brief: "Use if to catch ArithmeticException").
    public static final String DIVIDE_BY_ZERO = "Can not divide by zero";

    // The value in memory after each step; %s is the value.
    public static final String LABEL_MEMORY = "Memory:%s";

    // The final value when "=" is typed; %s is the value.
    public static final String LABEL_RESULT = "Result:%s";

    // ----- BMI calculator -----
    // Title of the BMI calculator.
    public static final String TITLE_BMI = "----- BMI Calculator -----";

    // Prompt for the weight in kg.
    public static final String INPUT_WEIGHT = "Enter Weight(kg): ";

    // Prompt for the height in cm.
    public static final String INPUT_HEIGHT = "Enter Height(cm): ";

    // Weight or height is not a positive number (the brief's screen).
    public static final String INVALID_BMI = "BMI is digit";

    // The BMI number, two decimals.
    public static final String LABEL_BMI_NUMBER = "BMI Number: %.2f";

    // The BMI status words.
    public static final String LABEL_BMI_STATUS = "BMI Status: %s";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
