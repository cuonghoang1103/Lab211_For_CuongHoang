package constants;

/**
 * Regular expressions, map keys and numbers the analysis depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // ----- regular expressions (the brief: "using Regular Expressions") -----
    // One number = one run of digits, so "321" is 321, not 3, 2, 1.
    public static final String NUMBER_REGEX = "\\d+";
    // One digit character.
    public static final String DIGIT_REGEX = "\\d";
    // A special character: neither an English letter nor a digit.
    public static final String SPECIAL_REGEX = "[^a-zA-Z0-9]";

    // ----- keys of getNumber's HashMap -----
    // Key of the list of all numbers.
    public static final String KEY_ALL = "all";
    // Key of the list of even numbers.
    public static final String KEY_EVEN = "even";
    // Key of the list of odd numbers.
    public static final String KEY_ODD = "odd";
    // Key of the list of perfect square numbers.
    public static final String KEY_SQUARE = "square";

    // ----- keys of getCharacter's HashMap -----
    // Key of all non-digit characters.
    public static final String KEY_ALL_CHARS = "allChars";
    // Key of the uppercase characters.
    public static final String KEY_UPPER = "upper";
    // Key of the lowercase characters.
    public static final String KEY_LOWER = "lower";
    // Key of the special characters.
    public static final String KEY_SPECIAL = "special";

    // Divisor of the even/odd test (the brief: number % 2).
    public static final int EVEN_DIVISOR = 2;

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
