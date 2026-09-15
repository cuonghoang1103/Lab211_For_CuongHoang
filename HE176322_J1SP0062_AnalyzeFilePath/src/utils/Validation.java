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

    // Checks that the text is a Windows path to a file, such as "C:\Windows\test.txt", so
    // the five analyses always have a disk and a file name to find.
    public static String getFilePath(String input) throws Exception {
        String path = input == null ? "" : input.trim();
        // nothing typed
        if (path.isEmpty()) {
            throw new Exception(Message.PATH_EMPTY);
        }
        // no drive letter, no file name, or a forbidden character
        if (!path.matches(Constants.PATH_PATTERN)) {
            throw new Exception(Message.PATH_INVALID);
        }
        return path;
    }
}
