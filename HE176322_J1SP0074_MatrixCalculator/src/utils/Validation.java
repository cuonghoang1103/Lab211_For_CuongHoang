package utils;

import constants.Constants;
import constants.Message;

/**
 * Shared checks for what the user typed - the menu choice, the sizes, the values, and
 * whether the two sizes fit the chosen operation.
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Converts a menu choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = parseNumber(input, Message.INVALID_CHOICE);

        // a number, but not one of the options
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return choice;
    }

    // Converts a number of rows or columns and checks it is from Constants.MIN_SIZE to
    // Constants.MAX_SIZE.
    public static int getSize(String input) throws Exception {
        int size = parseNumber(input, Message.INVALID_SIZE);

        // zero, negative or too big: not a usable size
        if ((size < Constants.MIN_SIZE) || (size > Constants.MAX_SIZE)) {
            throw new Exception(String.format(Message.INVALID_RANGE,
                    Constants.MIN_SIZE, Constants.MAX_SIZE));
        }

        return size;
    }

    // Converts one value of a matrix (any integer, negative included).
    public static int getValue(String input) throws Exception {
        return parseNumber(input, Message.INVALID_VALUE);
    }

    // Checks the sizes of an addition or a subtraction: the same number of rows AND of
    // columns.
    public static void checkSameSize(int[][] firstArray, int[][] secondArray)
            throws Exception {
        // a different shape cannot be added or subtracted cell by cell
        if ((firstArray.length != secondArray.length) ||
                (firstArray[0].length != secondArray[0].length)) {
            throw new Exception(Message.SIZE_NOT_SAME);
        }
    }

    // Checks the sizes of a multiplication (m x n) * (n x p): the columns of the first
    // matrix must equal the rows of the second.
    public static void checkMultiplySize(int[][] firstArray, int[][] secondArray)
            throws Exception {
        // the shared dimension n must be the same on both sides
        if (firstArray[0].length != secondArray.length) {
            throw new Exception(Message.SIZE_NOT_MULTIPLY);
        }
    }

    // Parses a whole number; the caller chooses the error message because the brief words
    // the value error its own way.
    private static int parseNumber(String input, String error) throws Exception {
        // letters, "1.5", an empty line or a number too big for an int
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // not a whole number at all
            throw new Exception(error);
        }
    }
}
