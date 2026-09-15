package utils;

import constants.Constants;
import constants.Message;

/**
 * Shared checks for what the user typed.
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Returns the text without surrounding spaces when it is not blank.
    public static String getNonBlank(String input, String error) throws Exception {
        // a missing or blank line is refused so main can ask again
        if (input == null || input.trim().isEmpty()) {
            throw new Exception(error);
        }
        return input.trim();
    }

    // Converts the mark (the brief: "If the mark of student is not a valid number,
    // request user to re-enter") and checks it lies in [0, 100].
    public static float getMark(String input) throws Exception {
        float mark;
        // parse first, so letters give the "number" message
        try {
            mark = Float.parseFloat(getNonBlank(input, Message.INVALID_NUMBER));
        } catch (NumberFormatException e) {
            // letters such as "abc"
            throw new Exception(Message.INVALID_NUMBER);
        }
        // "NaN" parses as a float but is not a number at all
        if (Float.isNaN(mark)) {
            throw new Exception(Message.INVALID_NUMBER);
        }
        // a number, but not a legal mark
        if (mark < Constants.MIN_MARK || mark > Constants.MAX_MARK) {
            throw new Exception(String.format(Message.INVALID_MARK,
                    Constants.MIN_MARK, Constants.MAX_MARK));
        }
        return mark;
    }

    // Converts the answer to the Y/N question.
    public static boolean getYesNo(String input) throws Exception {
        String answer = input == null ? "" : input.trim();
        // Y or y: the user wants to enter another student
        if (answer.equalsIgnoreCase(Constants.YES)) {
            return true;
        }
        // N or n: the user is done
        if (answer.equalsIgnoreCase(Constants.NO)) {
            return false;
        }
        throw new Exception(Message.INVALID_YES_NO);
    }
}
