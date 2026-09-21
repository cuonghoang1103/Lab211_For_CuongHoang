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

    // Converts a line into a whole number (the value to push).
    public static int getInt(String input) throws Exception {
        // a missing line is treated like an empty one
        if (input == null) {
            throw new Exception(Message.INVALID_NUMBER);
        }

        // parseInt refuses everything that is not a whole int
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // letters, "1.5", an empty line or an overflow
            throw new Exception(Message.INVALID_NUMBER);
        }
    }

    // Converts a menu choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = getInt(input);

        // a number, but not one of the menu options
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return choice;
    }
}
