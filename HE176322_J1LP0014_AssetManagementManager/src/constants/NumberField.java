package constants;

/**
 * The number fields of an asset: prompt, range and error of each, so one input loop and
 * one check serve them all.
 *
 * @author HE176322
 */
public enum NumberField {

    // Price: greater than 0.
    PRICE(Message.INPUT_PRICE, Message.INPUT_NEW_PRICE, Constants.MIN_POSITIVE,
            Constants.MAX_PRICE, false, Message.INVALID_PRICE),
    // Weight: greater than 0.
    WEIGHT(Message.INPUT_WEIGHT, Message.INPUT_NEW_WEIGHT, Constants.MIN_POSITIVE,
            Constants.MAX_WEIGHT, false, Message.INVALID_WEIGHT),
    // Quantity in stock: a whole number, 0 allowed (every unit may be on loan).
    QUANTITY(Message.INPUT_QUANTITY, Message.INPUT_NEW_QUANTITY, Constants.MIN_QUANTITY,
            Constants.MAX_QUANTITY, true, Message.INVALID_QUANTITY);

    // Prompt when the field is typed the first time.
    private final String prompt;
    // Prompt in update (blank keeps the old value).
    private final String newPrompt;
    // Smallest legal value.
    private final double min;
    // Largest legal value.
    private final double max;
    // True when decimals are refused.
    private final boolean wholeNumber;
    // Message shown for any wrong value.
    private final String error;

    // Creates one constant.
    NumberField(String prompt, String newPrompt, double min, double max,
            boolean wholeNumber, String error) {
        this.prompt = prompt;
        this.newPrompt = newPrompt;
        this.min = min;
        this.max = max;
        this.wholeNumber = wholeNumber;
        this.error = error;
    }

    // Returns the first-time prompt.
    public String getPrompt() {
        return prompt;
    }

    // Returns the update prompt.
    public String getNewPrompt() {
        return newPrompt;
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
