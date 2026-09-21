package constants;

/**
 * The number fields typed in the program: prompt, range and error, so the input loop and
 * the check are the same as in the manager's program.
 *
 * @author HE176322
 */
public enum NumberField {

    // Quantity to borrow: a whole number, at least 1.
    QUANTITY(Message.INPUT_QUANTITY, Constants.MIN_BORROW_QUANTITY, Constants.MAX_QUANTITY,
            true, Message.INVALID_QUANTITY);

    // Prompt printed before the number is typed.
    private final String prompt;

    // Smallest legal value.
    private final double min;

    // Largest legal value.
    private final double max;

    // True when decimals are refused.
    private final boolean wholeNumber;

    // Message shown for any wrong value.
    private final String error;

    // Creates one constant.
    NumberField(String prompt, double min, double max, boolean wholeNumber, String error) {
        this.prompt = prompt;
        this.min = min;
        this.max = max;
        this.wholeNumber = wholeNumber;
        this.error = error;
    }

    // Returns the prompt.
    public String getPrompt() {
        return prompt;
    }

    // Returns the smallest legal value.
    public double getMin() {
        return min;
    }

    // Returns the largest legal value.
    public double getMax() {
        return max;
    }

    // Tells whether decimals are refused.
    public boolean isWholeNumber() {
        return wholeNumber;
    }

    // Returns the error message.
    public String getError() {
        return error;
    }
}
