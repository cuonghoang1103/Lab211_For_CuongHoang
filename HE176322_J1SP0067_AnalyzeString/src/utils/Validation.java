package utils;

import constants.Constants;
import constants.Message;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    // Checks the string to analyse: not blank, and every run of digits must fit an
    // Integer (the brief's getNumber returns List of Integer, so "99999999999" would
    // otherwise crash the analysis).
    public static String getInput(String input) throws Exception {
        String text = (input == null) ? "" : input.trim();
        Matcher matcher = Pattern.compile(Constants.NUMBER_REGEX).matcher(text);

        // a missing or blank line is refused so main can ask again
        if (text.isEmpty()) {
            throw new Exception(Message.INPUT_EMPTY);
        }

        // try every run of digits as an int
        while (matcher.find()) {
            // parseInt fails only when the run is bigger than Integer.MAX_VALUE
            try {
                Integer.parseInt(matcher.group());
            } catch (NumberFormatException e) {
                // too many digits for an Integer
                throw new Exception(String.format(Message.NUMBER_TOO_BIG,
                        Integer.MAX_VALUE));
            }
        }

        return text;
    }
}
