package constants;

/**
 * The number fields of a vehicle: prompt, range and error of each, so one input loop
 * and one check serve them all.
 *
 * @author HE176322
 */
public enum NumberField {

    // Price of any vehicle.
    PRICE(Message.INPUT_PRICE, Message.INPUT_NEW_PRICE, Constants.MIN_PRICE,
            Constants.MAX_PRICE, false, Message.INVALID_PRICE),

    // Year of manufacture of a car (a whole number).
    YEAR(Message.INPUT_YEAR, Message.INPUT_NEW_YEAR, Constants.MIN_YEAR,
            Constants.MAX_YEAR, true, Message.INVALID_YEAR),

    // Speed of a motorbike.
    SPEED(Message.INPUT_SPEED, Message.INPUT_NEW_SPEED, Constants.MIN_SPEED,
            Constants.MAX_SPEED, false, Message.INVALID_SPEED);

    // Prompt when the field is typed for a new vehicle.
    private final String prompt;

    // Prompt when the field is typed in update (blank keeps the old value).
    private final String newPrompt;

    // Smallest legal value.
    private final double min;

    // Largest legal value.
    private final double max;

    // True when decimals are not allowed.
    private final boolean wholeNumber;

    // Message shown for any wrong value (letters, decimals, out of range).
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

    // Returns the prompt for a new vehicle.
    public String getPrompt() {
        return prompt;
    }

    // Returns the prompt of update.
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
