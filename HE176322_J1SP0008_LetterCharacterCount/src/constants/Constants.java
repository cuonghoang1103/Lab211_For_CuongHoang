package constants;

/**
 * Values the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Characters that separate two words: space, tab, new line, carriage return, form
    // feed - the same set StringTokenizer uses by default, written out so the rule is
    // visible in one place.
    public static final String WORD_DELIMITERS = " \t\n\r\f";

    // The count a word or character gets the first time it is seen.
    public static final int FIRST_COUNT = 1;

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
