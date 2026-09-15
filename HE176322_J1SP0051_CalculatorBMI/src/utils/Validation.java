package utils;

import constants.Constants;
import constants.Message;
import constants.Operator;

/**
 * Shared checks for what the user typed, including the brief's two mandated public
 * functions checkin and checkOperator.
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // The brief's "Double checkin(String inputVal)": is the input a number?
    public static Double checkin(String inputVal) {
        // the brief: "Use the try catch to catch NumberFormatException,
        // NullPointerException"
        try {
            Double value = Double.valueOf(inputVal.trim());
            // "NaN" and "Infinity" parse, but are not numeric data to compute with
            if (value.isNaN() || value.isInfinite()) {
                return null;
            }
            return value;
        } catch (NumberFormatException | NullPointerException e) {
            // letters, an empty line, or no line at all
            return null;
        }
    }

    // The brief's "Operator checkOperator(String operator)": is the input one of +, -, *,
    // /, ^, = ?
    public static Operator checkOperator(String operator) {
        // no line at all is not an operator
        if (operator == null) {
            return null;
        }
        String text = operator.trim();
        // the Guidelines' spelling of multiplication
        if (Constants.MULTIPLY_ALIAS.equalsIgnoreCase(text)) {
            return Operator.MULTIPLY;
        }
        // compare with the symbol of every enum constant
        for (Operator candidate : Operator.values()) {
            // found the operator the user typed
            if (candidate.getSymbol().equals(text)) {
                return candidate;
            }
        }
        return null;
    }

    // A number of the normal calculator (any real number).
    public static double getNumber(String input) throws Exception {
        Double value = checkin(input);
        // checkin said "not a number"
        if (value == null) {
            throw new Exception(Message.INVALID_NUMBER);
        }
        return value;
    }

    // A weight or a height: a number greater than 0.
    public static double getBodyValue(String input) throws Exception {
        Double value = checkin(input);
        // not a number, or not a real measurement
        if (value == null || value <= 0) {
            throw new Exception(Message.INVALID_BMI);
        }
        return value;
    }

    // An operator of the normal calculator.
    public static Operator getOperator(String input) throws Exception {
        Operator operator = checkOperator(input);
        // checkOperator said "not an operator"
        if (operator == null) {
            throw new Exception(Message.INVALID_OPERATOR);
        }
        return operator;
    }

    // Converts a menu choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice;
        // parse first; letters and empty lines fail here
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // not a whole number
            throw new Exception(String.format(Message.INVALID_CHOICE, min, max));
        }
        // a whole number, but not on the menu
        if (choice < min || choice > max) {
            throw new Exception(String.format(Message.INVALID_CHOICE, min, max));
        }
        return choice;
    }
}
