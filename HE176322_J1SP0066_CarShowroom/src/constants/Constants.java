package constants;

/**
 * Numbers and formats the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Discount for an unpainted car (the brief: "$100 discount").
    public static final double NO_COLOR_DISCOUNT = 100;
    // A price must be greater than this (the brief: "greater than zero").
    public static final double MIN_PRICE = 0;

    // What counts as a price: optional sign, digits, optional decimals.
    public static final String PRICE_PATTERN = "[-+]?\\d+(\\.\\d+)?";

    // Answer that means "find more".
    public static final String YES = "Y";
    // Answer that means "stop".
    public static final String NO = "N";

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
