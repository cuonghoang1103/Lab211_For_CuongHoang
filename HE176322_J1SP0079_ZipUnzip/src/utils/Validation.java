package utils;

import constants.Message;

/**
 * Shared checks for what the user typed. A utility: no object, no field, no keyboard, no
 * print - it only answers "is this line valid?" and throws the message of the broken rule.
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Returns the text without surrounding spaces when it is not blank.
    public static String getNonBlank(String input) throws Exception {
        // a missing or blank line is not a path or a name
        if ((input == null) || input.trim().isEmpty()) {
            throw new Exception(Message.VALUE_BLANK);
        }

        return input.trim();
    }

    // Converts a menu choice and checks it lies in [min, max]; the two failures are
    // caught separately so "9" is not reported as "not a number".
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = 0;

        // parse first, so letters give the "number" message
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // letters or an empty line: not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }

        // then the range, so 9 gives the "range" message
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return choice;
    }
}
