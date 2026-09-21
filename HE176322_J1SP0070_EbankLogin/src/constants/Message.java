package constants;

/**
 * Every message of the program that does not depend on the language, and the keys of the
 * texts that do (the keys are looked up in Language_xx.properties).
 *
 * @author HE176322
 */
public final class Message {

    // Title and options of the menu - always English, as in the brief.
    public static final String MENU = "-------Login Program-------\n"
            + "1. Vietnamese\n"
            + "2. English\n"
            + "3. Exit";

    // Prompt for the menu choice (the brief's wording).
    public static final String INPUT_CHOICE = "Please choice one option: ";

    // The menu choice was not a number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // The menu choice is outside the menu; the two %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // Key of the prompt for the account number.
    public static final String KEY_ACCOUNT_PROMPT = "account.prompt";

    // Key of the error when the account number is not 10 digits.
    public static final String KEY_ACCOUNT_ERROR = "account.error";

    // Key of the prompt for the password.
    public static final String KEY_PASSWORD_PROMPT = "password.prompt";

    // Key of the error when the password breaks the rule.
    public static final String KEY_PASSWORD_ERROR = "password.error";

    // Key of the label printed in front of the generated captcha.
    public static final String KEY_CAPTCHA_LABEL = "captcha.label";

    // Key of the prompt for the captcha characters.
    public static final String KEY_CAPTCHA_PROMPT = "captcha.prompt";

    // Key of the error when the typed characters are not in the captcha.
    public static final String KEY_CAPTCHA_ERROR = "captcha.error";

    // Key of the line shown once the whole login is right.
    public static final String KEY_LOGIN_SUCCESS = "login.success";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
