package utils;

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

    // Converts a menu choice and checks it lies in [min, max]; the two failures are
    // caught separately so "9" is not reported as "not a number".
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice;
        // parse first, so letters give the "number" message
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // letters or an empty line: not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }
        // then the range, so 9 gives the "range" message
        if (choice < min || choice > max) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }
        return choice;
    }
}
