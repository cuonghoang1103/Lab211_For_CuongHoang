package utils;

import constants.Constants;
import constants.Message;

/**
 * Shared checks for what the user typed.
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
        int choice;
        // parse first, so a letter gives the "number" message
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // letters or an empty line: not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }
        // then check the range, so 9 gives the "range" message
        if (choice < min || choice > max) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }
        return choice;
    }

    // The brief: "Username must be at least 5 characters and no spaces".
    public static String getUsername(String input) throws Exception {
        return checkField(input, Constants.USERNAME_MIN_LENGTH);
    }

    // The brief: "Password must be at least 6 characters and no spaces".
    public static String getPassword(String input) throws Exception {
        return checkField(input, Constants.PASSWORD_MIN_LENGTH);
    }

    // The rule shared by user name and password, written once.
    private static String checkField(String input, int minLength) throws Exception {
        // too short, or holds a space/tab anywhere
        if (input == null || input.length() < minLength
                || !input.matches(Constants.NO_SPACE_REGEX)) {
            throw new Exception(String.format(Message.INVALID_FIELD, minLength));
        }
        return input;
    }
}
