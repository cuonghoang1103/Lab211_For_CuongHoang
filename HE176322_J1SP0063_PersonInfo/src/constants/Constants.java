package constants;

/**
 * Numbers, patterns and formats the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // The brief: "Create an array of 3 person".
    public static final int NUMBER_OF_PERSONS = 3;
    // A salary must be strictly greater than this (the brief).
    public static final double MIN_SALARY = 0;
    // A plain decimal number: optional minus, digits, optional ".digits".
    public static final String SALARY_PATTERN = "-?\\d+(\\.\\d+)?";
    // How a salary is shown: at least one decimal, like the brief's "500.0", but never
    // the scientific "1.0E7" that Double.toString gives from ten million up.
    public static final String SALARY_FORMAT = "0.0##########";

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
