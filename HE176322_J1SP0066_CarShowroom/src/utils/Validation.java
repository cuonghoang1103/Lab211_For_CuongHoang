package utils;

import constants.Constants;
import constants.Message;
import exceptions.CarException;

/**
 * Shared checks for what the user typed. A utility: no object, no field, no keyboard, no
 * print.
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Turns the price text into a number - the price part of the brief's checkCar, so a
    // wrong price is a CarException (the brief's ExceptionCar).
    public static double checkPrice(String input) throws CarException {
        String text = (input == null) ? "" : input.trim();
        double price = 0;

        // letters, blank, "NaN", "1e3"...: not a plain number
        if (!text.matches(Constants.PRICE_PATTERN)) {
            throw new CarException(Message.PRICE_DIGIT);
        }

        price = Double.parseDouble(text);

        // a number, but not a price anybody can pay
        if (price <= Constants.MIN_PRICE) {
            throw new CarException(Message.PRICE_GREATER_ZERO);
        }

        return price;
    }

    // Reads the answer to "Do you want find more?(Y/N):".
    public static boolean checkYesNo(String input) throws Exception {
        String answer = (input == null) ? "" : input.trim();

        // Y: check another request
        if (answer.equalsIgnoreCase(Constants.YES)) {
            return true;
        }

        // N: stop the program
        if (answer.equalsIgnoreCase(Constants.NO)) {
            return false;
        }

        // anything else is refused, so main asks again
        throw new Exception(Message.INVALID_YES_NO);
    }
}
