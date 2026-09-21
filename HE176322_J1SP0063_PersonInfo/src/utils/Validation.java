package utils;

import constants.Constants;
import constants.Message;

/**
 * Shared checks for what the user typed. A utility: no object, no field, no keyboard, no
 * print - it only answers "is this line valid?".
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Returns the text when it is not blank.
    public static String getNonBlank(String input, String error) throws Exception {
        String text = (input == null) ? "" : input.trim();

        // blank text is refused so main can ask again
        if (text.isEmpty()) {
            throw new Exception(error);
        }

        return text;
    }

    // Checks the salary text with the brief's three messages, in this order: blank, not a
    // number, not greater than zero.
    public static double checkSalary(String input) throws Exception {
        String text = (input == null) ? "" : input.trim();
        double salary = 0;

        // nothing typed: its own message in the brief
        if (text.isEmpty()) {
            throw new Exception(Message.SALARY_EMPTY);
        }

        // letters, "1e3", "NaN"...: not a plain decimal number
        if (!text.matches(Constants.SALARY_PATTERN)) {
            throw new Exception(Message.SALARY_NOT_DIGIT);
        }

        // the text is a plain number now, so it can be converted
        salary = Double.parseDouble(text);

        // the brief: zero and negative salaries are refused
        if (salary <= Constants.MIN_SALARY) {
            throw new Exception(Message.SALARY_NOT_POSITIVE);
        }

        return salary;
    }
}
