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

    // Returns the text without surrounding spaces.
    public static String getText(String input) {
        // a missing line is treated like an empty one
        if (input == null) {
            return "";
        }

        return input.trim();
    }

    // Converts a menu choice and checks it lies in [min, max]; the two failures are
    // caught separately, so "9" is reported as out of range.
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = 0;

        // parse first, so a letter gives the "number" message
        try {
            choice = Integer.parseInt(getText(input));
        } catch (NumberFormatException e) {
            // letters or an empty line: not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }

        // a number, but not one of the menu options
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return choice;
    }

    // The brief's checkin(String floatString): converts a coefficient, or refuses it.
    // brief: public Float checkin(String floatString)
    public static Float getFloat(String floatString) throws Exception {
        Float value = null;

        // Float.valueOf refuses letters and an empty line
        try {
            value = Float.valueOf(getText(floatString));
        } catch (NumberFormatException e) {
            // the brief's message for a coefficient that is not a number
            throw new Exception(Message.INVALID_NUMBER);
        }

        // a "number" that cannot take part in a calculation
        if (value.isNaN() || value.isInfinite()) {
            throw new Exception(Message.INVALID_NUMBER);
        }

        return value;
    }
}
