package constants;

/**
 * Numbers, file names and formats the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;

    // Menu option: create a new account.
    public static final int MENU_CREATE = 1;

    // Menu option: login.
    public static final int MENU_LOGIN = 2;

    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 3;

    // The brief: "Username must be at least 5 characters".
    public static final int USERNAME_MIN_LENGTH = 5;

    // The brief: "Password must be at least 6 characters".
    public static final int PASSWORD_MIN_LENGTH = 6;

    // A text made only of non-space characters (the brief: "no spaces").
    public static final String NO_SPACE_REGEX = "\\S*";

    // The brief: accounts are stored in the file named "user.dat".
    public static final String DATA_FILE = "user.dat";

    // Separates user name and password on one line of user.dat.
    public static final String SEPARATOR = " ";

    // A line of user.dat has exactly two parts: user name and password.
    public static final int LINE_PARTS = 2;

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
