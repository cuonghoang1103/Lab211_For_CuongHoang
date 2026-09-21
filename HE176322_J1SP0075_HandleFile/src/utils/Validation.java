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

    // Returns the text without surrounding spaces (a pasted path often ends with an
    // invisible space).
    public static String getText(String input) {
        // a missing line is treated like an empty one
        if (input == null) {
            return "";
        }

        return input.trim();
    }

    // Converts a menu choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = 0;

        // parse first, so letters give the "number" message
        try {
            choice = Integer.parseInt(getText(input));
        } catch (NumberFormatException e) {
            // letters or an empty line
            throw new Exception(Message.INVALID_NUMBER);
        }

        // then the range, so 9 gives the "range" message
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return choice;
    }

    // Converts the size n of option 3 (the brief: "Check n must be numeric").
    public static int getSize(String input) throws Exception {
        // anything that is not a whole number is refused
        try {
            return Integer.parseInt(getText(input));
        } catch (NumberFormatException e) {
            // the brief's own message
            throw new Exception(Message.INVALID_SIZE);
        }
    }

    // Returns the path when something exists there (options 3 and 4 ask again until it
    // does: "Please Try again").
    public static String getExistPath(String input) throws Exception {
        String path = getText(input);

        // nothing on the disk at this path
        if (!FileUtils.isExist(path)) {
            throw new Exception(Message.PATH_NOT_EXIST);
        }

        return path;
    }
}
