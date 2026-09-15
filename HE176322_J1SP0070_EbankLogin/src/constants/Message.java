package constants;

/**
 * Every message of the program that does NOT depend on the language, plus the KEYS of the
 * texts that do.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu (always English, as in the brief) -----
    // Title and options of the menu.
    public static final String MENU = "-------Login Program-------\n"
            + "1. Vietnamese\n"
            + "2. English\n"
            + "3. Exit";
    // Prompt for the menu choice (the brief's wording).
    public static final String INPUT_CHOICE = "Please choice one option: ";
    // The menu choice was not a number.
    public static final String INVALID_NUMBER = "You must input a number.";
    // The menu choice is outside the menu; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // ----- keys of the translated texts (.properties) -----
    // Key: prompt for the account number.
    public static final String KEY_ACCOUNT_PROMPT = "account.prompt";
    // Key: the account number is not 10 digits.
    public static final String KEY_ACCOUNT_ERROR = "account.error";
    // Key: prompt for the password.
    public static final String KEY_PASSWORD_PROMPT = "password.prompt";
    // Key: the password breaks the rule.
    public static final String KEY_PASSWORD_ERROR = "password.error";
    // Key: label in front of the generated captcha.
    public static final String KEY_CAPTCHA_LABEL = "captcha.label";
    // Key: prompt for the captcha characters.
    public static final String KEY_CAPTCHA_PROMPT = "captcha.prompt";
    // Key: the typed characters are not in the captcha.
    public static final String KEY_CAPTCHA_ERROR = "captcha.error";
    // Key: shown once the captcha is right.
    public static final String KEY_LOGIN_SUCCESS = "login.success";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
