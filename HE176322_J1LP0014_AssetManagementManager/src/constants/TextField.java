package constants;

/**
 * The text fields typed in the program: prompt, pattern and error of each, so one input
 * loop and one check serve them all.
 *
 * @author HE176322
 */
public enum TextField {

    // Employee id at login.
    EMPLOYEE_ID(Message.INPUT_EMPLOYEE_ID, Message.INPUT_EMPLOYEE_ID, Constants.ID_PATTERN,
            Message.INVALID_EMPLOYEE_ID),

    // Password at login.
    PASSWORD(Message.INPUT_PASSWORD, Message.INPUT_PASSWORD, Constants.NOT_BLANK_PATTERN,
            Message.INVALID_PASSWORD),

    // Text searched in the names.
    KEYWORD(Message.INPUT_KEYWORD, Message.INPUT_KEYWORD, Constants.NOT_BLANK_PATTERN,
            Message.INVALID_KEYWORD),

    // Asset id.
    ASSET_ID(Message.INPUT_ASSET_ID, Message.INPUT_ASSET_ID, Constants.ASSET_ID_PATTERN,
            Message.INVALID_ASSET_ID),

    // Asset name.
    NAME(Message.INPUT_NAME, Message.INPUT_NEW_NAME, Constants.TEXT_PATTERN,
            Message.INVALID_NAME),

    // Asset color.
    COLOR(Message.INPUT_COLOR, Message.INPUT_NEW_COLOR, Constants.TEXT_PATTERN,
            Message.INVALID_COLOR),

    // Request id to approve.
    REQUEST_ID(Message.INPUT_REQUEST_ID, Message.INPUT_REQUEST_ID, Constants.ID_PATTERN,
            Message.INVALID_REQUEST_ID);

    // Prompt when the field is typed the first time.
    private final String prompt;

    // Prompt in update (blank keeps the old value).
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

    // Returns the first-time prompt.
    public String getPrompt() {
        return prompt;
    }

    // Returns the update prompt.
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
