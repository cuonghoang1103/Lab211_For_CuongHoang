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

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Converts a length and checks it is a real number greater than zero.
    public static double getPositiveDouble(String input) throws Exception {
        double value = 0;

        // parse first, so letters give the "number" message
        try {
            value = Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            // letters or an empty line: not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }

        // parseDouble also accepts "NaN" and "Infinity" - not real lengths
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new Exception(Message.INVALID_NUMBER);
        }

        // a length must be strictly positive
        if (value <= Constants.MIN_LENGTH) {
            throw new Exception(Message.INVALID_POSITIVE);
        }

        return value;
    }

    // Checks the triangle inequality: three sides form a triangle only when each pair
    // together is LONGER than the third.
    public static void checkTriangle(double sideA, double sideB, double sideC)
            throws Exception {
        // one pair not longer than the third side: no triangle (a flat one too)
        if (((sideA + sideB) <= sideC) || ((sideA + sideC) <= sideB) ||
                ((sideB + sideC) <= sideA)) {
            throw new Exception(Message.INVALID_TRIANGLE);
        }
    }
}
