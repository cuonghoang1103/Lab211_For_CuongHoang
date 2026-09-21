package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title and options of the main menu (the brief's screen).
    public static final String MENU = "====== USER MANAGEMENT SYSTEM ======\n"
            + "1. Create a new account\n"
            + "2. Login system\n"
            + "3. Exit";

    // Prompt for the menu choice (the brief's screen).
    public static final String INPUT_CHOICE = "> Choose: ";

    // ----- prompts (the brief's screen) -----
    // Prompt for the user name.
    public static final String INPUT_USERNAME = "Enter Username: ";

    // Prompt for the password.
    public static final String INPUT_PASSWORD = "Enter Password: ";

    // ----- validation errors -----
    // A menu choice that is not a number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // A menu choice outside the menu; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // User name or password too short or containing a space - the brief's screen, with %d
    // = 5 for the user name and 6 for the password.
    public static final String INVALID_FIELD
            = "You must enter least at %d character, and no space!";

    // ----- business errors -----
    // Create with a user name that is already stored; %s is the name.
    public static final String DUPLICATE_USERNAME = "Username [%s] already exists.";

    // Login failed (the brief's own wording).
    public static final String LOGIN_FAIL = "Invalid user name or password";

    // user.dat exists but could not be read.
    public static final String CANNOT_READ = "Can't read file user.dat";

    // user.dat could not be created or written.
    public static final String CANNOT_WRITE = "Can't write file user.dat";

    // ----- results -----
    // Shown after an account is created.
    public static final String CREATE_SUCCESS = "Create account successfully!";

    // Shown after a successful login (the brief's screen).
    public static final String LOGIN_SUCCESS = "Login successful!";

    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
