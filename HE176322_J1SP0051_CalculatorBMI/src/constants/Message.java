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
    // Label before the value in memory after each step.
    public static final String LABEL_MEMORY = "Memory:";
    // Label before the final value when "=" is typed.
    public static final String LABEL_RESULT = "Result:";

    // ----- BMI calculator -----
    // Title of the BMI calculator.
    public static final String TITLE_BMI = "----- BMI Calculator -----";
    // Prompt for the weight in kg.
    public static final String INPUT_WEIGHT = "Enter Weight(kg): ";
    // Prompt for the height in cm.
    public static final String INPUT_HEIGHT = "Enter Height(cm): ";
    // Weight or height is not a positive number (the brief's screen).
    public static final String INVALID_BMI = "BMI is digit";
    // Label before the BMI number.
    public static final String LABEL_BMI_NUMBER = "BMI Number: ";
    // Label before the BMI status.
    public static final String LABEL_BMI_STATUS = "BMI Status: ";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
