package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // The menu of bases, printed before every conversion.
    public static final String MENU = "======= CHANGE BASE NUMBER SYSTEM =======\n"
            + "1. Binary (base 2)\n"
            + "2. Decimal (base 10)\n"
            + "3. Hexadecimal (base 16)\n"
            + "0. Exit\n"
            + "=========================================";

    // Prompt for the input base (0 closes the program).
    public static final String INPUT_BASE_IN = "Choose the INPUT base: ";

    // Prompt for the output base.
    public static final String INPUT_BASE_OUT = "Choose the OUTPUT base: ";

    // Prompt for the value to convert.
    public static final String INPUT_VALUE = "Enter the input value: ";

    // A menu choice that is not a whole number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // A menu number out of range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // The value was left empty.
    public static final String EMPTY_VALUE = "You must input something.";

    // A digit that does not exist in the base; value, base name.
    public static final String INVALID_VALUE = "%s is not a valid %s number.";

    // The value does not fit in a long.
    public static final String TOO_BIG = "The value is too big for this program.";

    // One value with its base.
    public static final String VALUE_FORMAT = "%s (%s)";

    // The result line: input = output.
    public static final String RESULT = "%s = %s";

    // Printed when the user chooses 0.
    public static final String GOODBYE = "Goodbye.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
