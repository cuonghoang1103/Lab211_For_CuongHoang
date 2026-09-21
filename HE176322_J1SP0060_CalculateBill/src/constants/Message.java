package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- title and prompts (the brief's screen, character for character) -----
    // Title printed when the program starts.
    public static final String TITLE = "======= Shopping program ==========";

    // Prompt for how many bills there are.
    public static final String INPUT_NUMBER_OF_BILL = "input number of bill:";

    // Prompt for one bill; %d is the bill number, counted from 1.
    public static final String INPUT_BILL = "input value of bill %d:";

    // Prompt for the amount in the wallet.
    public static final String INPUT_WALLET = "input value of wallet:";

    // ----- validation errors -----
    // The line typed was not a whole number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // The number is outside the allowed range; %d are the bounds.
    public static final String INVALID_RANGE = "Value must be between %d and %d.";

    // ----- results (the brief's screen) -----
    // Line of the total of the bills; %d is the total.
    public static final String LABEL_TOTAL = "this is total of bill:%d";

    // The wallet holds enough money.
    public static final String CAN_BUY = "You can buy it.";

    // The wallet does not hold enough money.
    public static final String CANNOT_BUY = "You can’t buy it.";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
