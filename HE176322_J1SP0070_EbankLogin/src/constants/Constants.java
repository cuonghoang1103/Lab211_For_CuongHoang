package constants;

/**
 * Numbers, rules (regular expressions) and names the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the menu.
    public static final int MENU_MIN = 1;
    // Menu option: Vietnamese.
    public static final int MENU_VIETNAMESE = 1;
    // Menu option: English.
    public static final int MENU_ENGLISH = 2;
    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 3;

    // Language code of Vietnamese (finds Language_vi.properties).
    public static final String LANGUAGE_VI = "vi";
    // Language code of English (finds Language_en.properties).
    public static final String LANGUAGE_EN = "en";
    // Base name of the ResourceBundle: the files Language_en.properties and
    // Language_vi.properties in the folder src/constants.
    public static final String BUNDLE_NAME = "constants.Language";

    // Account number: exactly 10 digits, nothing else.
    public static final String ACCOUNT_REGEX = "[0-9]{10}";
    // Password: 8 to 31 letters or digits, with at least one letter AND at least one
    // digit (the brief rejects "12345678" and "aaaaaaaa").
    public static final String PASSWORD_REGEX = "(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,31}";

    // Characters a captcha is made of.
    public static final String CAPTCHA_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    // Number of characters of a captcha (the brief's example: H9MOA).
    public static final int CAPTCHA_LENGTH = 5;

    // What a check returns when the value is valid: no message.
    public static final String VALID = "";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
