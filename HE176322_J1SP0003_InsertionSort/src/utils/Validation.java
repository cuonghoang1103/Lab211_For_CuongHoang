package utils;

import constants.Constants;
import constants.Message;

/**
 * Shared checks of what the user typed. A utility: no object, no field, no keyboard, no
 * print - it only answers "is this line valid?".
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Converts the size of the array and checks it is a positive number no bigger than
    // Constants.MAX_SIZE.
    public static int getSize(String input) throws Exception {
        int size = 0;

        // parse first, so letters give the "number" message
        try {
            size = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // letters, "3.5" or an empty line: not a whole number at all
            throw new Exception(Message.INVALID_NUMBER);
        }

        // zero, negative or too big: a number, but not a legal size
        if ((size < Constants.MIN_SIZE) || (size > Constants.MAX_SIZE)) {
            throw new Exception(String.format(Message.INVALID_RANGE,
                    Constants.MIN_SIZE, Constants.MAX_SIZE));
        }

        return size;
    }
}
