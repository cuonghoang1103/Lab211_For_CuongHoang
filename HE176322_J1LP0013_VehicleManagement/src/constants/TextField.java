package constants;

/**
 * The text fields of a vehicle: prompt, pattern and error of each, so one input loop
 * and one check serve them all.
 *
 * @author HE176322
 */
public enum TextField {

    // Vehicle id.
    ID(Message.INPUT_ID, Message.INPUT_ID, Constants.ID_PATTERN, Message.INVALID_ID),

    // Vehicle name.
    NAME(Message.INPUT_NAME, Message.INPUT_NEW_NAME, Constants.NAME_PATTERN,
            Message.INVALID_NAME),

    // Vehicle color.
    COLOR(Message.INPUT_COLOR, Message.INPUT_NEW_COLOR, Constants.COLOR_PATTERN,
            Message.INVALID_COLOR),

    // Vehicle brand.
    BRAND(Message.INPUT_BRAND, Message.INPUT_NEW_BRAND, Constants.BRAND_PATTERN,
            Message.INVALID_BRAND),

    // Type of a car.
    CAR_TYPE(Message.INPUT_CAR_TYPE, Message.INPUT_NEW_CAR_TYPE, Constants.CAR_TYPE_PATTERN,
            Message.INVALID_CAR_TYPE),

    // Text searched in the names.
    KEYWORD(Message.INPUT_KEYWORD, Message.INPUT_KEYWORD, Constants.KEYWORD_PATTERN,
            Message.INVALID_KEYWORD);

    // Prompt when the field is typed for a new vehicle.
    private final String prompt;

    // Prompt when the field is typed in update (blank keeps the old value).
    private final String newPrompt;

    // Regular expression the text must match.
    private final String pattern;

    // Message shown when it does not.
    private final String error;

    // Creates one constant.
    TextField(String prompt, String newPrompt, String pattern, String error) {
        this.prompt = prompt;
        this.newPrompt = newPrompt;
        this.pattern = pattern;
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

    // Returns the pattern.
    public String getPattern() {
        return pattern;
    }

    // Returns the error message.
    public String getError() {
        return error;
    }
}
