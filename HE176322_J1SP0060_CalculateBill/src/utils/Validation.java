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

    // Converts a whole number and checks it lies in [min, max].
    public static int getInt(String input, int min, int max) throws Exception {
        int value;
        // parse first, so letters give the "number" message
        try {
            value = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // letters, a decimal or an empty line: not a whole number
            throw new Exception(Message.INVALID_NUMBER);
        }
        // a number, but outside the bounds of this field
        if (value < min || value > max) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }
        return value;
    }
}
