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

    // Converts a menu choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice;
        // parse first, so letters give the "number" message
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // letters or an empty line
            throw new Exception(Message.INVALID_NUMBER);
        }
        // a number, but not one of the menu options
        if (choice < min || choice > max) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }
        return choice;
    }

    // Returns the typed line EXACTLY as typed - not trimmed.
    public static String getRawText(String input) {
        // a missing line is treated like an empty one
        if (input == null) {
            return "";
        }
        return input;
    }
}
