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

    // Returns the content when it has at least one non-space character.
    public static String getContent(String input) throws Exception {
        // a missing or blank line leaves nothing to count
        if ((input == null) || input.trim().isEmpty()) {
            throw new Exception(Message.EMPTY_CONTENT);
        }

        return input.trim();
    }
}
