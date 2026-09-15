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

    // Converts any whole number (negative too): used for the search value, which may well
    // be a number that is not in the array.
    public static int getInt(String input) throws Exception {
        // parseInt is the check: letters, decimals and empty lines fail
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // letters, "3.5", an empty line or a number too big for int
            throw new Exception(Message.INVALID_NUMBER);
        }
    }

    // Converts the size of the array and checks it is a positive number no bigger than
    // Constants.MAX_SIZE.
    public static int getSize(String input) throws Exception {
        int size = getInt(input);
        // zero, negative or too big: a number, but not a legal size
        if (size < Constants.MIN_SIZE || size > Constants.MAX_SIZE) {
            throw new Exception(String.format(Message.INVALID_RANGE,
                    Constants.MIN_SIZE, Constants.MAX_SIZE));
        }
        return size;
    }
}
