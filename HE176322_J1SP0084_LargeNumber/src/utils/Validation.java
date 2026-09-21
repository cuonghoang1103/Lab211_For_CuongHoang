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

    // Checks that the line is a whole number made of digits only.
    public static String getDigits(String input) throws Exception {
        String text = "";

        // a missing or empty line is not a number
        if ((input == null) || input.trim().isEmpty()) {
            throw new Exception(Message.INVALID_DIGIT);
        }

        // the spaces around the number do not count
        text = input.trim();

        // every character must be one of 0-9
        for (int i = 0; i < text.length(); i++) {
            // one bad character refuses the whole line
            if (!Character.isDigit(text.charAt(i))) {
                throw new Exception(Message.INVALID_DIGIT);
            }
        }

        return text;
    }
}
