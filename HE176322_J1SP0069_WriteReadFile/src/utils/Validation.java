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

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Converts the answer to a Y/N question (the brief: "Y/N or y/n").
    public static boolean getYesNo(String input) throws Exception {
        String answer = (input == null) ? "" : input.trim();

        // Y or y: the user agrees
        if (answer.equalsIgnoreCase(Constants.YES)) {
            return true;
        }

        // N or n: the user refuses
        if (answer.equalsIgnoreCase(Constants.NO)) {
            return false;
        }

        // anything else is refused so main can ask again
        throw new Exception(Message.INVALID_YES_NO);
    }

    // Returns the text without surrounding spaces when it is not blank.
    public static String getNonBlank(String input, String error) throws Exception {
        // a missing or blank line is refused so main can ask again
        if ((input == null) || input.trim().isEmpty()) {
            throw new Exception(error);
        }

        return input.trim();
    }

    // Tells whether a content line is the stop word.
    public static boolean isSaveCommand(String line) {
        String word = (line == null) ? "" : line.trim();

        return word.equals(Constants.SAVE_LOWER) || word.equals(Constants.SAVE_UPPER);
    }
}
