package constants;

/**
 * Numbers the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // At least one bill, otherwise there is nothing to pay.
    public static final int MIN_BILLS = 1;
    // At most 100 bills, so a typo such as 99999999 cannot exhaust memory.
    public static final int MAX_BILLS = 100;
    // Smallest value of one bill: a bill of 0 is not a bill.
    public static final int MIN_BILL_VALUE = 1;
    // Largest value of one bill.
    public static final int MAX_BILL_VALUE = 10000000;
    // An empty wallet is legal.
    public static final int MIN_WALLET = 0;
    // Largest wallet amount; still well inside the int range.
    public static final int MAX_WALLET = 1000000000;

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
