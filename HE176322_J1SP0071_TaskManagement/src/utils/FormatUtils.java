package utils;

import constants.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Writing a date and the hours of a task the way the brief's table does.
 *
 * @author HE176322
 */
public final class FormatUtils {

    // Private constructor: every method is called through the class name.
    private FormatUtils() {
    }

    // Writes a date as dd-MM-yyyy.
    public static String formatDate(Date date) {
        return new SimpleDateFormat(Constants.DATE_FORMAT).format(date);
    }

    // Writes the hours of a task with one decimal and a dot (8.0), even on a Vietnamese
    // machine that would write 8,0.
    public static String formatTime(double time) {
        return String.format(Locale.US, Constants.TIME_FORMAT, time);
    }
}
