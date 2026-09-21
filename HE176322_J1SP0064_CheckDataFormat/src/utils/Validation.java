package utils;

import constants.Constants;
import constants.Message;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The brief's three format checks: checkPhone, checkEmail, checkDate. A utility: no
 * object, no field, no keyboard, no print.
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Returns the text without surrounding spaces; null counts as empty.
    public static String getText(String input) {
        // a missing line is treated like an empty one
        if (input == null) {
            return "";
        }

        return input.trim();
    }

    // The brief's checkPhone: digits only first, then exactly 10 of them.
    public static String checkPhone(String phone) {
        String value = getText(phone);

        // letters, spaces, "+" or nothing at all: not a number
        if (!value.matches(Constants.PHONE_PATTERN)) {
            return Message.PHONE_NOT_NUMBER;
        }

        // a number, but too short or too long
        if (value.length() != Constants.PHONE_LENGTH) {
            return Message.PHONE_NOT_10_DIGITS;
        }

        return Message.NO_ERROR;
    }

    // The brief's checkEmail: the email must match name@domain.ext.
    public static String checkEmail(String email) {
        // the regex of the brief's suggestion ("Use regex")
        if (!getText(email).matches(Constants.EMAIL_PATTERN)) {
            return Message.EMAIL_INVALID;
        }

        return Message.NO_ERROR;
    }

    // The brief's checkDate: the shape dd/MM/yyyy (regex), then a real calendar date
    // (SimpleDateFormat, as the brief suggests).
    public static String checkDate(String date) {
        String value = getText(date);
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);

        // "abc", "5/6/2015", "15-06-2015": not 2 + 2 + 4 digits
        if (!value.matches(Constants.DATE_PATTERN)) {
            return Message.DATE_INVALID;
        }

        // a strict calendar: 31/02 is an error, not 03/03
        formatter.setLenient(false);

        // right shape: now ask the calendar whether the day exists
        try {
            formatter.parse(value);
        } catch (ParseException e) {
            // 31/02, 29/02 of a non-leap year, month 13, day 00...
            return Message.DATE_INVALID;
        }

        return Message.NO_ERROR;
    }
}
