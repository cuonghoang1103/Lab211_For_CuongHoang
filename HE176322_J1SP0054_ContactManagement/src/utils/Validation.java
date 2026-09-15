package utils;

import constants.Constants;
import constants.Message;

/**
 * Shared checks for what the user typed.
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Converts a menu choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        String error = String.format(Message.INVALID_CHOICE, min, max);
        int choice;
        // parse first; letters or blank are not a choice
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // not a whole number at all
            throw new Exception(error);
        }
        // a number, but not on the menu
        if (choice < min || choice > max) {
            throw new Exception(error);
        }
        return choice;
    }

    // Returns the text when it is not blank.
    public static String getNonBlank(String input, String error) throws Exception {
        String text = input == null ? "" : input.trim();
        // blank text is refused so main can ask again
        if (text.isEmpty()) {
            throw new Exception(error);
        }
        return text;
    }

    // Checks the phone is in one of the seven formats of the brief.
    public static String checkPhone(String input) throws Exception {
        String phone = input == null ? "" : input.trim();
        // none of the seven formats matches the whole text
        if (!phone.matches(Constants.PHONE_PATTERN)) {
            throw new Exception(Message.INVALID_PHONE);
        }
        return phone;
    }

    // Converts the ID to delete: it must be a whole number from 1 (IDs start at 1, so 0
    // or a negative number can never exist either).
    public static int checkId(String input) throws Exception {
        int id;
        // letters or blank are not an ID
        try {
            id = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            // not a whole number at all
            throw new Exception(Message.ID_DIGIT);
        }
        // zero or negative: no contact can have it
        if (id < Constants.FIRST_ID) {
            throw new Exception(Message.ID_DIGIT);
        }
        return id;
    }
}
