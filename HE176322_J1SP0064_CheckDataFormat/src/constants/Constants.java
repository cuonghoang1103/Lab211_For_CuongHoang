package constants;

/**
 * Patterns, lengths and formats the three checks depend on.
 *
 * @author HE176322
 */
public final class Constants {

    // Digits only, at least one (the brief: "must be numbers").
    public static final String PHONE_PATTERN = "\\d+";
    // The brief: a phone number has exactly 10 digits.
    public static final int PHONE_LENGTH = 10;
    // name@domain.ext: letters, digits, "_", ".", "+" or "-" before the "@"; then a
    // domain and at least one ".part" (so "abc" and "a@b" fail).
    public static final String EMAIL_PATTERN = "[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+";
    // The SHAPE of dd/MM/yyyy: exactly 2 + 2 + 4 digits.
    public static final String DATE_PATTERN = "\\d{2}/\\d{2}/\\d{4}";
    // The date format of the brief, for SimpleDateFormat.
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
