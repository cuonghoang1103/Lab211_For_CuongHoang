package utils;

import constants.Constants;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Writing a salary and a date the way the brief's screen does.
 *
 * @author HE176322
 */
public final class FormatUtils {

    // Private constructor: every method is called through the class name.
    private FormatUtils() {
    }

    // Writes a salary with two decimals, as on the brief's screen.
    public static String formatSalary(double salary) {
        return String.format(Locale.US, Constants.SALARY_FORMAT, salary);
    }

    // Writes a date as yyyy-MM-dd.
    public static String formatDate(Date date) {
        return new SimpleDateFormat(Constants.DOB_FORMAT).format(date);
    }
}
