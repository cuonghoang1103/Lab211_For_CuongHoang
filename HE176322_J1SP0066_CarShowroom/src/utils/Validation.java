package utils;

import constants.Constants;
import constants.Message;
import exceptions.ExceptionCar;

/**
 * Shared checks for what the user typed.
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Turns the price text into a number.
    public static double checkPrice(String input) throws ExceptionCar {
        // letters, blank, "NaN", "1e3"...: not a plain number
        if (input == null || !input.trim().matches(Constants.PRICE_PATTERN)) {
            throw new ExceptionCar(Message.PRICE_DIGIT);
        }
        double price = Double.parseDouble(input.trim());
        // a number, but not a price anybody can pay
        if (price <= Constants.MIN_PRICE) {
            throw new ExceptionCar(Message.PRICE_GREATER_ZERO);
        }
        return price;
    }

    // Reads the answer to "Do you want find more?(Y/N):".
    public static boolean checkYesNo(String input) throws Exception {
        String answer = input == null ? "" : input.trim();
        // Y: check another request
        if (answer.equalsIgnoreCase(Constants.YES)) {
            return true;
        }
        // N: stop the program
        if (answer.equalsIgnoreCase(Constants.NO)) {
            return false;
        }
        throw new Exception(Message.INVALID_YES_NO);
    }
}
