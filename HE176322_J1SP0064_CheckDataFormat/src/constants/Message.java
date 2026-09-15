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
    // Prompt for the phone number.
    public static final String INPUT_PHONE = "Phone number:";
    // Prompt for the email.
    public static final String INPUT_EMAIL = "Email:";
    // Prompt for the date.
    public static final String INPUT_DATE = "Date:";

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
    // Label in front of the accepted phone number.
    public static final String LABEL_PHONE = "Phone number: ";
    // Label in front of the accepted email.
    public static final String LABEL_EMAIL = "Email: ";
    // Label in front of the accepted date.
    public static final String LABEL_DATE = "Date: ";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
