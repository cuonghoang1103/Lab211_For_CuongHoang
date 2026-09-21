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

    // Returns the text without surrounding spaces when it is not blank (a path or a file
    // name).
    public static String getNonBlank(String input) throws Exception {
        // a missing or blank line is refused so main can ask again
        if ((input == null) || input.trim().isEmpty()) {
            throw new Exception(Message.BLANK);
        }

        return input.trim();
    }

    // The minimum salary typed at the keyboard: a number, not less than 0.
    public static double getMoney(String input) throws Exception {
        double money = 0;

        // letters or an empty line are not a number
        try {
            money = Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            // not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }

        // parseDouble also accepts "NaN" and "Infinity": not real amounts
        if (Double.isNaN(money) || Double.isInfinite(money)) {
            throw new Exception(Message.INVALID_NUMBER);
        }

        // the brief: "The amount not less than 0"
        if (money < Constants.MIN_MONEY) {
            throw new Exception(Message.MONEY_NEGATIVE);
        }

        return money;
    }
}
