package utils;

import constants.Message;

/**
 * Shared checks of what the user typed. A utility: no object, no field, no keyboard, no
 * print - it only answers "is this line valid?".
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Converts a menu choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = 0;

        // parse first, so letters give the "number" message
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // letters or an empty line: not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }

        // then the range, so 9 gives the "range" message
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return choice;
    }

    // Tells whether the WHOLE input matches the regex (no trim: " 0123456789" must fail).
    public static boolean isMatch(String input, String regex) {
        // no input at all never matches
        if (input == null) {
            return false;
        }

        return input.matches(regex);
    }

    // Tells whether the typed characters are a non-empty part of the captcha (contains()).
    public static boolean isCaptchaMatch(String captchaInput, String captchaGenerate) {
        // nothing typed: "H9MOA".contains("") is true, so it must be refused first
        if ((captchaInput == null) || captchaInput.isEmpty()) {
            return false;
        }

        // case-sensitive on purpose: the captcha is upper-case, so "h" is wrong (brief)
        return captchaGenerate.contains(captchaInput);
    }
}
