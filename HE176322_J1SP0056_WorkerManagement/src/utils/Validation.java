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

    // Returns the text without surrounding spaces.
    public static String getText(String input) {
        // a missing line is treated like an empty one
        if (input == null) {
            return "";
        }
        return input.trim();
    }

    // Converts a menu choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = getInt(input);
        // a number, but not one of the menu options
        if (choice < min || choice > max) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }
        return choice;
    }

    // Converts a whole number (the age).
    public static int getInt(String input) throws Exception {
        // parseInt refuses letters, decimals and an empty line
        try {
            return Integer.parseInt(getText(input));
        } catch (NumberFormatException e) {
            // not a whole number at all
            throw new Exception(Message.INVALID_NUMBER);
        }
    }

    // Converts a real number (salary, amount).
    public static double getDouble(String input) throws Exception {
        double value;
        // parseDouble refuses letters and an empty line
        try {
            value = Double.parseDouble(getText(input));
        } catch (NumberFormatException e) {
            // not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }
        // "NaN" and "Infinity" parse, but nobody means them as money
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new Exception(Message.INVALID_NUMBER);
        }
        return value;
    }
}
