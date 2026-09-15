package utils;

import constants.Constants;
import constants.Message;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Converting between a Date and the brief's text "11-Apr-2009".
 *
 * @author HE176322
 */
public final class DateUtils {

    // Private constructor: every method is called through the class name.
    private DateUtils() {
    }

    // Writes a date as dd-MMM-yyyy in English.
    public static String formatDate(Date date) {
        return createFormat().format(date);
    }

    // Reads a dd-MMM-yyyy date strictly: the shape must match (4-digit year), the day
    // must exist (31-Feb-2009 is refused because the format is not lenient) and the whole
    // text must be used.
    public static Date parseDate(String text) throws Exception {
        // wrong shape, e.g. 2009-04-11 or 11-Apr-09
        if (text == null || !text.matches(Constants.DATE_REGEX)) {
            throw new Exception(Message.INVALID_DATE);
        }
        ParsePosition position = new ParsePosition(0);
        Date date = createFormat().parse(text, position);
        // not a real day, or characters left over
        if (date == null || position.getIndex() != text.length()) {
            throw new Exception(Message.INVALID_DATE);
        }
        return date;
    }

    // Creates the strict English dd-MMM-yyyy format.
    private static SimpleDateFormat createFormat() {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT,
                Locale.ENGLISH);
        format.setLenient(false);
        return format;
    }
}
