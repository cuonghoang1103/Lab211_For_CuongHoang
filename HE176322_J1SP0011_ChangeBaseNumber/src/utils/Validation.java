package utils;

import constants.Base;
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

        // then check the range, so 5 gives the "range" message
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return choice;
    }

    // Returns the value to convert when it is not empty.
    public static String getValue(String input) throws Exception {
        // an empty line has nothing to convert
        if ((input == null) || input.trim().isEmpty()) {
            throw new Exception(Message.EMPTY_VALUE);
        }

        return input.trim();
    }

    // Checks that the value is written in its base: an optional sign, then at least one
    // digit, every digit one of that base ("1G" is not HEX, "2" is not BIN; "ff" is HEX).
    public static void checkValue(String value, Base base) throws Exception {
        String digits = value.toUpperCase();
        int digit = 0;

        // a sign is not a digit: check only what follows it
        if (digits.startsWith(Constants.MINUS) || digits.startsWith(Constants.PLUS)) {
            digits = digits.substring(1);
        }

        // a sign alone has no digit at all
        if (digits.isEmpty()) {
            throw new Exception(String.format(Message.INVALID_VALUE, value, base.getLabel()));
        }

        // check EVERY character, so "1G" is "not valid", never "too big"
        for (int i = 0; i < digits.length(); i++) {
            digit = Constants.DIGITS.indexOf(digits.charAt(i));

            // not a digit at all, or a digit too big for this base
            if ((digit < 0) || (digit >= base.getRadix())) {
                throw new Exception(String.format(Message.INVALID_VALUE, value,
                        base.getLabel()));
            }
        }
    }
}
