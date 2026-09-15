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

    // Converts the size of the array and checks it is a positive number no bigger than
    // Constants.MAX_SIZE.
    public static int getSize(String input) throws Exception {
        int size;
        // parse first, so letters give the "number" message
        try {
            size = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // letters or an empty line: not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }
        // zero, negative or too big: a number, but not a legal size
        if (size < Constants.MIN_SIZE || size > Constants.MAX_SIZE) {
            throw new Exception(String.format(Message.INVALID_RANGE,
                    Constants.MIN_SIZE, Constants.MAX_SIZE));
        }
        return size;
    }
}
