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

    // Converts a menu choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = 0;

        // parse first, so a letter gives the "number" message
        try {
            choice = Integer.parseInt(input.trim());
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

    // Returns the text without surrounding spaces when it is not blank.
    public static String getNonBlank(String input, String error) throws Exception {
        // a missing or blank line is refused so main can ask again
        if ((input == null) || input.trim().isEmpty()) {
            throw new Exception(error);
        }

        return input.trim();
    }

    // Checks an English word: not blank, and without the "=" that separates the word from
    // its meaning in the data file (otherwise the line could not be split back correctly
    // when the program starts again).
    public static String getEnglish(String input) throws Exception {
        String english = getNonBlank(input, Message.ENGLISH_BLANK);

        // "=" is reserved as the separator of the data file
        if (english.contains(Constants.SEPARATOR)) {
            throw new Exception(Message.ENGLISH_SEPARATOR);
        }

        return english;
    }

    // Converts the answer to a Y/N question, in upper or lower case.
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

        throw new Exception(Message.INVALID_YES_NO);
    }
}
