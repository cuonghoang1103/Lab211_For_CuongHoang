package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title and options of the main menu (the brief's screen).
    public static final String MENU = "============ Login Program =========\n"
            + "1. Add User\n"
            + "2. Login\n"
            + "3) Exit";

    // Prompt for the menu choice (the brief's wording).
    public static final String INPUT_CHOICE = "Please choice one option:";

    // ----- add user screen -----
    // Title of the add screen.
    public static final String TITLE_ADD = "---------- Add User --------";

    // Prompt for the username on the add screen.
    public static final String INPUT_ADD_ACCOUNT = "Account:";

    // Prompt for the password on the add screen.
    public static final String INPUT_ADD_PASSWORD = "Password:";

    // Prompt for the name.
    public static final String INPUT_NAME = "Name:";

    // Prompt for the phone.
    public static final String INPUT_PHONE = "Phone:";

    // Prompt for the email.
    public static final String INPUT_EMAIL = "Email:";

    // Prompt for the address.
    public static final String INPUT_ADDRESS = "Address:";

    // Prompt for the date of birth.
    public static final String INPUT_DOB = "DOB:";

    // ----- login screen -----
    // Title of the login screen.
    public static final String TITLE_LOGIN = "------------- Login ----------------";

    // Prompt for the username on the login screen.
    public static final String INPUT_LOGIN_ACCOUNT = "Account: ";

    // Prompt for the password on the login screen.
    public static final String INPUT_LOGIN_PASSWORD = "Password: ";

    // Title of the welcome screen (the brief's spelling).
    public static final String TITLE_WELCOME = "------------ Wellcome -----------";

    // The greeting; %s is the username (the brief: "Hello + Username").
    public static final String HELLO = "Hello %s";

    // The change-password question; %s is the name.
    public static final String ASK_CHANGE = "Hi %s, do you want change password now? Y/N:";

    // Prompt for the old password.
    public static final String INPUT_OLD_PASSWORD = "Old password:";

    // Prompt for the new password.
    public static final String INPUT_NEW_PASSWORD = "new password:";

    // Prompt for the new password again.
    public static final String INPUT_RENEW_PASSWORD = "renew password:";

    // ----- validation errors -----
    // Menu choice is not a number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // Menu choice out of range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // Username blank (the brief: cannot null, empty).
    public static final String USERNAME_EMPTY = "Username cannot be empty.";

    // Password blank.
    public static final String PASSWORD_EMPTY = "Password cannot be empty.";

    // Name blank.
    public static final String NAME_EMPTY = "Name cannot be empty.";

    // Phone blank.
    public static final String PHONE_EMPTY = "Phone number cannot be empty.";

    // Phone not 10-11 digits (the brief's wording).
    public static final String PHONE_INVALID = "Phone number must be 10 or 11 number.";

    // Email blank.
    public static final String EMAIL_EMPTY = "Email cannot be empty.";

    // Email in a wrong format.
    public static final String EMAIL_INVALID = "Email is not in the correct format.";

    // Date of birth blank.
    public static final String DOB_EMPTY = "Date of birth cannot be empty.";

    // Date of birth not a real dd/MM/yyyy date.
    public static final String DOB_INVALID
            = "Date of birth must be a real date in the format dd/MM/yyyy.";

    // New password blank.
    public static final String NEW_PASSWORD_EMPTY = "New password cannot be empty.";

    // New password and renew password differ.
    public static final String PASSWORD_MISMATCH = "The two new passwords do not match.";

    // The JVM has no MD5 (never happens: every Java platform must have it).
    public static final String HASH_MISSING = "MD5 is required of every Java platform.";

    // ----- business errors -----
    // Username taken; %s is the stored spelling.
    public static final String USERNAME_EXIST = "Username [%s] already exists.";

    // Change password for an account that is not stored.
    public static final String ACCOUNT_NOT_EXIST = "Account does not exist.";

    // Old password wrong on the change-password screen.
    public static final String OLD_PASSWORD_WRONG = "Old password is not correct.";

    // Shown when username or password is wrong (the brief: "login fail").
    public static final String LOGIN_FAIL = "Login fail.";

    // ----- results -----
    // Shown after an account is added; %s is the username, %d the id.
    public static final String ADD_SUCCESS = "Account [%s] has been added with id %d.";

    // Shown after the password is changed.
    public static final String CHANGE_SUCCESS = "Password has been changed.";

    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
