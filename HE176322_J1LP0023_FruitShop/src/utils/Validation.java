package utils;

import constants.Constants;
import constants.Message;

/**
 * Shared checks for what the user typed: each method converts one line or throws the
 * message to show. A utility: static methods only, no keyboard, no print.
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Returns the text without surrounding spaces; refuses a blank line.
    public static String getText(String input) throws Exception {
        String text = input.trim();

        // nothing typed
        if (text.isEmpty()) {
            throw new Exception(Message.EMPTY_FIELD);
        }

        return text;
    }

    // Converts a whole number.
    public static int getInt(String input) throws Exception {
        // parseInt refuses letters, decimals and empty text
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // not a whole number
            throw new Exception(Message.INVALID_NUMBER);
        }
    }

    // Converts a choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = getInt(input);

        // a number, but not one of the options
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return choice;
    }

    // Converts a price, which must be a real number greater than 0.
    public static double getPrice(String input) throws Exception {
        double price = 0;

        // parseDouble refuses letters and empty text
        try {
            price = Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            // not a number
            throw new Exception(Message.INVALID_NUMBER);
        }

        // "NaN" and "Infinity" are parsed by Java but are not prices
        if (Double.isNaN(price) || Double.isInfinite(price)) {
            throw new Exception(Message.INVALID_NUMBER);
        }

        // 0 or below
        if (price <= 0) {
            throw new Exception(Message.INVALID_PRICE);
        }

        return price;
    }

    // Converts the quantity in stock of a new fruit, which must not be negative.
    public static int getStock(String input) throws Exception {
        int stock = getInt(input);

        // below 0
        if (stock < Constants.MIN_STOCK) {
            throw new Exception(Message.INVALID_STOCK);
        }

        return stock;
    }

    // Converts the quantity a buyer orders, which must be greater than 0.
    public static int getOrderQuantity(String input) throws Exception {
        int quantity = getInt(input);

        // 0 or below
        if (quantity < Constants.MIN_ORDER_QUANTITY) {
            throw new Exception(Message.INVALID_ORDER_QUANTITY);
        }

        return quantity;
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
