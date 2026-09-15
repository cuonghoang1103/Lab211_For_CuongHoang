package utils;

import constants.Constants;
import constants.Message;

/**
 * Shared checks for what the user typed; every number goes through the brief's checkIn.
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // The brief's Function 1 check: public Integer checkIn(String inputVal).
    public static Integer checkIn(String inputVal) {
        // a missing line is not a number
        if (inputVal == null) {
            return null;
        }
        // Integer.valueOf throws on letters, decimals and empty text
        try {
            return Integer.valueOf(inputVal.trim());
        } catch (NumberFormatException e) {
            // not an integer: the brief's contract is to return null
            return null;
        }
    }

    // Converts a menu choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        Integer choice = checkIn(input);
        // letters or an empty line
        if (choice == null) {
            throw new Exception(Message.INVALID_NUMBER);
        }
        // a number, but not on the menu
        if (choice < min || choice > max) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }
        return choice;
    }

    // Converts the length of the array (the brief: "Length of the array must be more than
    // 0").
    public static int getLength(String input) throws Exception {
        Integer length = checkIn(input);
        // the brief shows one message for both "a" and "-1"
        if (length == null || length < Constants.MIN_LENGTH) {
            throw new Exception(Message.INVALID_LENGTH);
        }
        // a legal number, but too big to type in or to keep in memory
        if (length > Constants.MAX_LENGTH) {
            throw new Exception(String.format(Message.LENGTH_TOO_BIG,
                    Constants.MAX_LENGTH));
        }
        return length;
    }

    // Converts one element (the brief: "The value of items in the array must be
    // integer"); negative numbers and 0 are allowed.
    public static int getElement(String input) throws Exception {
        Integer element = checkIn(input);
        // letters, decimals or an empty line
        if (element == null) {
            throw new Exception(Message.INVALID_NUMBER);
        }
        return element;
    }
}
