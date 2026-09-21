package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- title and prompts (the brief's screen, typos included) -----
    // Title printed when the program starts ("Progaram" is the brief's).
    public static final String TITLE = "====== Validate Progaram ======";

    // Prompt for the phone number; the brief's prompt ends with one space.
    public static final String INPUT_PHONE = "Phone number: ";

    // Prompt for the email; the brief's prompt ends with one space.
    public static final String INPUT_EMAIL = "Email: ";

    // Prompt for the date; the brief's prompt ends with one space.
    public static final String INPUT_DATE = "Date: ";

    // ----- errors: the Guidelines' wording (the screen's "must is" is a typo) -----
    // checkPhone: the phone contains something that is not a digit.
    public static final String PHONE_NOT_NUMBER = "Phone number must be number";

    // checkPhone: the phone is digits only, but not exactly 10 of them.
    public static final String PHONE_NOT_10_DIGITS = "Phone number must be 10 digits";

    // checkEmail: the email does not look like name@domain.ext.
    public static final String EMAIL_INVALID = "Email must be correct format";

    // checkDate: the date is not a real dd/MM/yyyy date.
    public static final String DATE_INVALID = "Date to correct format(dd/MM/yyyy)";

    // The "no error" answer of the three check methods (the brief).
    public static final String NO_ERROR = "";

    // ----- result (kept from the reference program; the brief is silent) -----
    // Title printed above the accepted values.
    public static final String TITLE_RESULT = "----- Result -----";

    // Line of the accepted phone number; %s is the phone.
    public static final String RESULT_PHONE = "Phone number: %s";

    // Line of the accepted email; %s is the email.
    public static final String RESULT_EMAIL = "Email: %s";

    // Line of the accepted date; %s is the date as dd/MM/yyyy.
    public static final String RESULT_DATE = "Date: %s";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
