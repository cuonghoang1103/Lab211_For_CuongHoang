package utils;

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

    // Converts a vertex label and checks it lies in [min, max] - the same shape as the
    // Guide sample getChoice(input, min, max).
    public static int getInt(String input, int min, int max) throws Exception {
        int value = 0;

        // parse first, so letters give the "number" message
        try {
            value = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // letters, decimals or an empty line: not a whole number
            throw new Exception(Message.INVALID_NUMBER);
        }

        // a number, but not the label of a vertex
        if ((value < min) || (value > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return value;
    }
}
