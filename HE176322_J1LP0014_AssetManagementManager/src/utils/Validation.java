package utils;

import constants.Constants;
import constants.Message;
import constants.NumberField;
import constants.TextField;

/**
 * Shared checks for what the user typed: each method converts one text or throws the
 * message to show. Main calls them; the controller only receives values already checked.
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

        // parseInt refuses letters, decimals and empty text
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // not a whole number: same message as out of range
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        // a number, but not one of the options
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return choice;
    }

    // Returns the text without surrounding spaces when it matches the field's pattern.
    public static String checkText(String input, TextField field) throws Exception {
        String text = input.trim();

        // blank, too long or a forbidden character
        if (!text.matches(field.getPattern())) {
            throw new Exception(field.getError());
        }

        return text;
    }

    // Converts a number and checks the field's range (and "whole" for a quantity).
    public static double checkNumber(String input, NumberField field) throws Exception {
        double value = 0;

        // parseDouble refuses letters and empty text
        try {
            value = Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            // not a number: same message as out of range - one field, one message
            throw new Exception(field.getError());
        }

        // "NaN" and "Infinity" parse but are not values
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new Exception(field.getError());
        }

        // a quantity must not have decimals
        if (field.isWholeNumber() && (value != Math.floor(value))) {
            throw new Exception(field.getError());
        }

        // outside the field's range
        if ((value < field.getMin()) || (value > field.getMax())) {
            throw new Exception(field.getError());
        }

        return value;
    }

    // Converts a Y/N answer, ignoring case: true for Y, false for N.
    public static boolean getYesNo(String input) throws Exception {
        String answer = input.trim();

        // Y or y
        if (answer.equalsIgnoreCase(Constants.YES)) {
            return true;
        }

        // N or n
        if (answer.equalsIgnoreCase(Constants.NO)) {
            return false;
        }

        throw new Exception(Message.INVALID_YES_NO);
    }
}
