package constants;

/**
 * Numbers, formats and patterns the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;
    // Menu option: add an account.
    public static final int MENU_ADD = 1;
    // Menu option: login.
    public static final int MENU_LOGIN = 2;
    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 3;

    // ID of the very first account.
    public static final int FIRST_ID = 1;

    // The brief: date of birth is dd/MM/yyyy.
    public static final String DOB_FORMAT = "dd/MM/yyyy";
    // The brief: a phone number is 10 or 11 digits and nothing else.
    public static final String PHONE_REGEX = "^\\d{10,11}$";
    // A modest email shape, like the brief's nghianv@t.com: a local part, an "@", a
    // domain, a dot, and a suffix of at least two letters.
    public static final String EMAIL_REGEX
            = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    // Name of the hash algorithm the brief asks for.
    public static final String HASH_ALGORITHM = "MD5";
    // One byte of the digest as two lower-case hex digits, zero padded.
    public static final String HEX_FORMAT = "%02x";

    // The answer that opens the change-password screen (any case).
    public static final String YES = "Y";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
