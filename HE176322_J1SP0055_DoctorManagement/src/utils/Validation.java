package utils;

import constants.Constants;
import constants.Message;

/**
 * Shared checks for what the user typed. A utility: no object, no field, no keyboard, no
 * print - it only answers "is this line valid?".
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: nobody may create a Validation object; every method is called
    // through the class name.
    private Validation() {
    }

    // Returns the text without surrounding spaces; an empty text is allowed (update uses
    // "empty" to mean "keep the old value").
    public static String getText(String input) {
        // a missing line is treated like an empty one
        if (input == null) {
            return "";
        }

        return input.trim();
    }

    // Returns the text when it is not blank.
    public static String getNonBlank(String input, String error) throws Exception {
        String text = getText(input);

        // blank text is refused so main can ask again
        if (text.isEmpty()) {
            throw new Exception(error);
        }

        return text;
    }

    // Converts a menu choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = 0;

        // parse first, so a letter gives the "number" message
        try {
            choice = Integer.parseInt(getText(input));
        } catch (NumberFormatException e) {
            // letters or an empty line: not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }

        // then check the range, so 9 gives the "range" message
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return choice;
    }

    // The brief's checkAvailability: converts the availability and checks Availability >=
    // 0.
    public static int checkAvailability(String input) throws Exception {
        int availability = 0;

        // "many" or an empty line is not a number
        try {
            availability = Integer.parseInt(getText(input));
        } catch (NumberFormatException e) {
            // letters or an empty line: not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }

        // the brief: Availability >= 0
        if (availability < Constants.MIN_AVAILABILITY) {
            throw new Exception(Message.INVALID_AVAILABILITY);
        }

        return availability;
    }

    // Same as checkAvailability, but a blank line is allowed and gives null (used by
    // update, where blank means "keep the old availability").
    public static Integer checkOptionalAvailability(String input) throws Exception {
        // blank: the user wants to keep the old value
        if (getText(input).isEmpty()) {
            return null;
        }

        return checkAvailability(input);
    }
}
