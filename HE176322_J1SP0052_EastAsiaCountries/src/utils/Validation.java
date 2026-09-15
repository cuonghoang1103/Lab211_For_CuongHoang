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

    // Converts a menu choice and checks it lies in [min, max]; the program answers both
    // failures with the same sentence.
    public static int getChoice(String input, int min, int max) throws Exception {
        String error = String.format(Message.INVALID_CHOICE, min, max);
        int choice;
        // parse first; letters or blank are not a choice
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // not a whole number at all
            throw new Exception(error);
        }
        // a number, but not on the menu
        if (choice < min || choice > max) {
            throw new Exception(error);
        }
        return choice;
    }

    // Returns the text when it is not blank.
    public static String getNonBlank(String input) throws Exception {
        String text = input == null ? "" : input.trim();
        // blank text is refused so main can ask again
        if (text.isEmpty()) {
            throw new Exception(Message.FIELD_BLANK);
        }
        return text;
    }

    // Converts the total area and checks it is greater than 0 (the brief).
    public static float getTotalArea(String input) throws Exception {
        float area;
        // parse first, so letters give the "number" message
        try {
            area = Float.parseFloat(input.trim());
        } catch (NumberFormatException e) {
            // letters or an empty line: not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }
        // parseFloat accepts "NaN" and "Infinity" (and 1e39 overflows)
        if (Float.isNaN(area) || Float.isInfinite(area)) {
            throw new Exception(Message.INVALID_NUMBER);
        }
        // the brief: total area must be greater than 0
        if (area <= Constants.MIN_AREA) {
            throw new Exception(Message.INVALID_AREA);
        }
        return area;
    }
}
