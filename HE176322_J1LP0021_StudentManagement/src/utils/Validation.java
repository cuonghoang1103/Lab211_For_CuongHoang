package utils;

import constants.Message;

/**
 * Shared checks of the FORM of what the user typed (a number? Y or N?); the rules about
 * students belong to the service.
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Returns the text without surrounding spaces; empty is allowed (update uses "empty"
    // to mean "keep the old value").
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
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return choice;
    }

    // Converts a whole number, with no range attached (semester: the "> 0" rule is
    // checked by the service, after all fields are typed).
    public static int getInt(String input) throws Exception {
        // parseInt refuses letters, decimals and empty text
        try {
            return Integer.parseInt(getText(input));
        } catch (NumberFormatException e) {
            // not a whole number
            throw new Exception(Message.INVALID_NUMBER);
        }
    }

    // Like getInt, but a blank line is allowed and means "keep" (update).
    public static Integer getOptionalInt(String input) throws Exception {
        // blank: keep the old semester
        if (getText(input).isEmpty()) {
            return null;
        }

        return getInt(input);
    }

    // Accepts one of two letters, ignoring case (Y/N, U/D), and returns it as written in
    // Constants.
    public static String getOption(String input, String first, String second)
            throws Exception {
        String text = getText(input);

        // the first letter, in any case
        if (text.equalsIgnoreCase(first)) {
            return first;
        }

        // the second letter, in any case
        if (text.equalsIgnoreCase(second)) {
            return second;
        }

        throw new Exception(String.format(Message.INVALID_OPTION, first, second));
    }
}
