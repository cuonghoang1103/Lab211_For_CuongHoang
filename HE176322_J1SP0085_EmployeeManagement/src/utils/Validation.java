package utils;

import constants.Constants;
import constants.Message;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Shared checks for what the user typed. A utility: no object, no field, no keyboard, no
 * print - it only answers "is this line valid?" and throws the message of the broken rule.
 * Every check takes the current value too: null on Add (blank = refused), the stored value
 * on Update (blank = keep it).
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
        int choice = 0;

        // parse first, so a letter gives the "number" message
        try {
            choice = Integer.parseInt(getText(input));
        } catch (NumberFormatException e) {
            // letters or an empty line: not a number at all
            throw new Exception(Message.INVALID_NUMBER);
        }

        // then check the range, so 9 gives the "range" message
        if ((choice < min) || (choice > max)) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }

        return choice;
    }

    // A required text field (Id, names, address, agency).
    public static String getField(String input, String current) throws Exception {
        String text = getText(input);

        // a blank line: keep the old value, or refuse when there is none
        if (text.isEmpty()) {
            // Update: Enter keeps the value in brackets
            if (current != null) {
                return current;
            }

            throw new Exception(Message.FIELD_REQUIRED);
        }

        return text;
    }

    // The brief: phone "digits only".
    public static String getPhone(String input, String current) throws Exception {
        String phone = getField(input, current);

        // a letter, a space or a "+" inside the number
        if (!phone.matches(Constants.PHONE_REGEX)) {
            throw new Exception(Message.PHONE_INVALID);
        }

        return phone;
    }

    // The brief: email "contains @ and a domain".
    public static String getEmail(String input, String current) throws Exception {
        String email = getField(input, current);

        // no "@", or no domain with a dot after it
        if (!email.matches(Constants.EMAIL_REGEX)) {
            throw new Exception(Message.EMAIL_INVALID);
        }

        return email;
    }

    // The brief: DOB "a valid date (for example yyyy-MM-dd)".
    public static Date getDob(String input, String current) throws Exception {
        String text = getField(input, current);
        SimpleDateFormat format = new SimpleDateFormat(Constants.DOB_FORMAT);
        Date dob = null;

        // strict: 1994-02-30 is refused instead of becoming 1994-03-02
        format.setLenient(false);

        // parse() throws for text that is not a date at all
        try {
            dob = format.parse(text);
        } catch (ParseException e) {
            // "abc", "1994-13-40"
            throw new Exception(Message.DOB_INVALID);
        }

        // the round trip rejects "1994-5-20" and "1994-05-20xyz"
        if (!format.format(dob).equals(text)) {
            throw new Exception(Message.DOB_INVALID);
        }

        return dob;
    }

    // The brief: sex is "Male or Female".
    public static String getSex(String input, String current) throws Exception {
        String sex = getField(input, current);

        // "male", "MALE" -> "Male"
        if (sex.equalsIgnoreCase(Constants.SEX_MALE)) {
            return Constants.SEX_MALE;
        }

        // "female" -> "Female"
        if (sex.equalsIgnoreCase(Constants.SEX_FEMALE)) {
            return Constants.SEX_FEMALE;
        }

        throw new Exception(Message.SEX_INVALID);
    }

    // The brief: salary "a positive number".
    public static double getSalary(String input, String current) throws Exception {
        String text = getField(input, current);
        double salary = 0;

        // Double (wrapper class) decides whether the text is a number
        try {
            salary = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            // letters, "1,500"
            throw new Exception(Message.SALARY_NOT_NUMBER);
        }

        // "NaN" and "Infinity" are accepted by parseDouble but are no salary
        if (Double.isNaN(salary) || Double.isInfinite(salary)) {
            throw new Exception(Message.SALARY_NOT_NUMBER);
        }

        // zero and negative salaries
        if (salary <= Constants.MIN_SALARY) {
            throw new Exception(Message.SALARY_NOT_POSITIVE);
        }

        return salary;
    }

    // The search text: anything but blank.
    public static String getKeyword(String input) throws Exception {
        String keyword = getText(input);

        // a blank search would match everybody
        if (keyword.isEmpty()) {
            throw new Exception(Message.KEYWORD_EMPTY);
        }

        return keyword;
    }
}
