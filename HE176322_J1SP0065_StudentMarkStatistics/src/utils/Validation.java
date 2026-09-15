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

    // Returns the text without surrounding spaces.
    public static String getText(String input) {
        // a missing line is treated like an empty one
        if (input == null) {
            return "";
        }
        return input.trim();
    }

    // Converts a mark and checks it lies in [0, 10], with the brief's three messages.
    public static double getMark(String input, String subject) throws Exception {
        double mark;
        // letters, an empty line: not a number
        try {
            mark = Double.parseDouble(getText(input));
        } catch (NumberFormatException e) {
            // the brief: "Maths is digit"
            throw new Exception(String.format(Message.MARK_NOT_DIGIT, subject));
        }
        // "NaN" parses but is not a mark
        if (Double.isNaN(mark)) {
            throw new Exception(String.format(Message.MARK_NOT_DIGIT, subject));
        }
        // the brief: above 10 -> "Maths is less than equal ten"
        if (mark > Constants.MAX_MARK) {
            throw new Exception(String.format(Message.MARK_TOO_BIG, subject));
        }
        // the brief: below 0 -> "Maths is greater than equal zero"
        if (mark < Constants.MIN_MARK) {
            throw new Exception(String.format(Message.MARK_TOO_SMALL, subject));
        }
        return mark;
    }

    // Tells whether the answer to the Y/N question is one of the two legal letters
    // (either case).
    public static boolean isYesOrNo(String input) {
        String answer = getText(input);
        return answer.equalsIgnoreCase(Constants.YES)
                || answer.equalsIgnoreCase(Constants.NO);
    }

    // Tells whether the answer is Y (either case).
    public static boolean isYes(String input) {
        return getText(input).equalsIgnoreCase(Constants.YES);
    }
}
