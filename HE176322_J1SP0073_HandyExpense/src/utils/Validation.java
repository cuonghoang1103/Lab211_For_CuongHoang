package utils;

import constants.Message;
import java.util.Date;

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

    // Returns the text without surrounding spaces.
    public static String getText(String input) {
        // a missing line is treated like an empty one
        if (input == null) {
            return "";
        }

        return input.trim();
    }

    // Converts a whole number (the ID to delete).
    public static int getInt(String input) throws Exception {
        // parseInt refuses letters, decimals and an empty line
        try {
            return Integer.parseInt(getText(input));
        } catch (NumberFormatException e) {
            // not a whole number at all
            throw new Exception(Message.INVALID_NUMBER);
        }
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

    // Converts the date of an expense (dd-MMM-yyyy, English month).
    public static Date getDate(String input) throws Exception {
        return DateUtils.parseDate(getText(input));
    }

    // Converts an amount of money and checks it is greater than 0.
    public static double getAmount(String input) throws Exception {
        double amount = 0;

        // parseDouble refuses letters and an empty line
        try {
            amount = Double.parseDouble(getText(input));
        } catch (NumberFormatException e) {
            // not a number at all
            throw new Exception(Message.INVALID_AMOUNT);
        }

        // "NaN" and "Infinity" parse, but they are not money
        if (Double.isNaN(amount) || Double.isInfinite(amount)) {
            throw new Exception(Message.INVALID_AMOUNT);
        }

        // an expense of 0 or -50 is a typo, not a purchase
        if (amount <= 0) {
            throw new Exception(Message.AMOUNT_POSITIVE);
        }

        return amount;
    }

    // Returns the content when it is not blank.
    public static String getContent(String input) throws Exception {
        String content = getText(input);

        // an expense must say what it was for
        if (content.isEmpty()) {
            throw new Exception(Message.EMPTY_FIELD);
        }

        return content;
    }
}
