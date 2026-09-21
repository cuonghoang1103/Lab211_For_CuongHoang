package utils;

import constants.Constants;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Writing money the way the brief's screen does: a whole value with no decimals ("1100",
 * "550"), any other value with its decimals ("1100.5").
 *
 * @author HE176322
 */
public final class FormatUtils {

    // Private constructor: every method is called through the class name.
    private FormatUtils() {
    }

    // Writes an amount of money with pattern Constants.MONEY_FORMAT ("0.##": at most two
    // decimals, none when the value is whole).
    public static String formatMoney(double amount) {
        DecimalFormat format = new DecimalFormat(Constants.MONEY_FORMAT,
                new DecimalFormatSymbols(Locale.US));

        // a dot for the decimals on every machine (Locale.US), never "100,1"
        return format.format(amount);
    }
}
