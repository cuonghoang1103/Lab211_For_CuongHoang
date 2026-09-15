package utils;

import constants.Constants;
import constants.Message;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Shared checks for what the user typed.
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

    // Converts a menu choice and checks it lies in [min, max].
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice;
        // parse first, so a letter gives the "number" message
        try {
            choice = Integer.parseInt(getText(input));
        } catch (NumberFormatException e) {
            // letters or an empty line: not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }
        // then check the range, so 9 gives the "range" message
        if (choice < min || choice > max) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }
        return choice;
    }

    // The brief: "cannot null, empty".
    public static String getRequired(String input, String error) throws Exception {
        String text = getText(input);
        // null and blank are both refused
        if (text.isEmpty()) {
            throw new Exception(error);
        }
        return text;
    }

    // The brief: "Phone number must be 10 or 11 number".
    public static String getPhone(String input) throws Exception {
        String phone = getRequired(input, Message.PHONE_EMPTY);
        // letters, 9 digits, 12 digits
        if (!phone.matches(Constants.PHONE_REGEX)) {
            throw new Exception(Message.PHONE_INVALID);
        }
        return phone;
    }

    // The brief: "Email is valid format".
    public static String getEmail(String input) throws Exception {
        String email = getRequired(input, Message.EMAIL_EMPTY);
        // no "@", no dot in the domain, one-letter suffix
        if (!email.matches(Constants.EMAIL_REGEX)) {
            throw new Exception(Message.EMAIL_INVALID);
        }
        return email;
    }

    // The brief: "Date of birth is valid format dd/MM/yyyy".
    public static Date getDob(String input) throws Exception {
        String text = getRequired(input, Message.DOB_EMPTY);
        SimpleDateFormat format = new SimpleDateFormat(Constants.DOB_FORMAT);
        format.setLenient(false);
        Date dob;
        // parse() throws for text that is not a date at all
        try {
            dob = format.parse(text);
        } catch (ParseException e) {
            // "abc", "31/02/2003"
            throw new Exception(Message.DOB_INVALID);
        }
        // the round trip rejects "1/2/2015" and "26/06/2016xyz"
        if (!format.format(dob).equals(text)) {
            throw new Exception(Message.DOB_INVALID);
        }
        return dob;
    }

    // Tells whether the answer to "Y/N" is yes.
    public static boolean isYes(String input) {
        return Constants.YES.equalsIgnoreCase(getText(input));
    }
}
