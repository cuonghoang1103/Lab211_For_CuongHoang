package utils;

import constants.CandidateType;
import constants.Constants;
import constants.GraduationRank;
import constants.Message;
import java.time.Year;

/**
 * Shared checks for what the user typed - the five checks the brief lists (birth date,
 * phone, email, year of experience, rank of graduation) plus numbers, choices and Y/N.
 *
 * @author HE176322
 */
public final class Validation {

    // Private constructor: every method is called through the class name.
    private Validation() {
    }

    // Returns the text without surrounding spaces; empty is allowed (the service decides
    // whether an empty id or name is an error).
    public static String getText(String input) {
        // a missing line is treated like an empty one
        if (input == null) {
            return "";
        }
        return input.trim();
    }

    // Converts a whole number (semester).
    public static int getInt(String input) throws Exception {
        // letters, decimals or an empty line are not a whole number
        try {
            return Integer.parseInt(getText(input));
        } catch (NumberFormatException e) {
            // translate Java's exception into the program's message
            throw new Exception(Message.INVALID_NUMBER);
        }
    }

    // Converts a choice and checks it lies in [min, max]; the two failures give two
    // different messages.
    public static int getChoice(String input, int min, int max) throws Exception {
        int choice = getInt(input);
        // a number, but not one of the choices
        if (choice < min || choice > max) {
            throw new Exception(String.format(Message.INVALID_RANGE, min, max));
        }
        return choice;
    }

    // Search: the type code 0..2, turned into the enum.
    public static CandidateType getCandidateType(String input) throws Exception {
        int code = getChoice(input, Constants.TYPE_MIN, Constants.TYPE_MAX);
        return CandidateType.fromCode(code);
    }

    // The brief: birth date is a number of 4 characters, 1900..current year.
    public static int checkBirthDate(String input) throws Exception {
        String text = getText(input);
        // not exactly four digits: "19", "nineteen", "19888"
        if (!text.matches(Constants.BIRTH_YEAR_REGEX)) {
            throw new Exception(Message.INVALID_BIRTH_DATE);
        }
        int year = Integer.parseInt(text);
        // four digits, but before 1900 or in the future
        if (year < Constants.MIN_BIRTH_YEAR || year > Year.now().getValue()) {
            throw new Exception(Message.INVALID_BIRTH_DATE);
        }
        return year;
    }

    // The brief: phone is a number with at least 10 characters.
    public static String checkPhone(String input) throws Exception {
        String text = getText(input);
        // letters, dashes or fewer than 10 digits
        if (!text.matches(Constants.PHONE_REGEX)) {
            throw new Exception(Message.INVALID_PHONE);
        }
        return text;
    }

    // The brief: email in the form account name @ domain.
    public static String checkEmail(String input) throws Exception {
        String text = getText(input);
        // no '@', no dotted domain, or spaces
        if (!text.matches(Constants.EMAIL_REGEX)) {
            throw new Exception(Message.INVALID_EMAIL);
        }
        return text;
    }

    // The brief: year of experience is a number from 0 to 100.
    public static int checkExperience(String input) throws Exception {
        int years;
        // letters: same message as out of range - one field, one message
        try {
            years = Integer.parseInt(getText(input));
        } catch (NumberFormatException e) {
            // not a whole number at all
            throw new Exception(Message.INVALID_EXPERIENCE);
        }
        // a number, but outside 0..100
        if (years < Constants.MIN_EXPERIENCE || years > Constants.MAX_EXPERIENCE) {
            throw new Exception(Message.INVALID_EXPERIENCE);
        }
        return years;
    }

    // The brief: rank is one of Excellence, Good, Fair, Poor (any case in, canonical
    // spelling out).
    public static GraduationRank checkRank(String input) throws Exception {
        GraduationRank rank = GraduationRank.fromText(getText(input));
        // "Average" or anything else
        if (rank == null) {
            throw new Exception(Message.INVALID_RANK);
        }
        return rank;
    }

    // The answer to "Do you want to continue (Y/N)?".
    public static boolean checkYesNo(String input) throws Exception {
        String text = getText(input);
        // Y or y: go on
        if (text.equalsIgnoreCase(Constants.YES)) {
            return true;
        }
        // N or n: stop
        if (text.equalsIgnoreCase(Constants.NO)) {
            return false;
        }
        throw new Exception(Message.INVALID_YES_NO);
    }
}
