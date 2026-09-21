package constants;

/**
 * The text fields typed in the program: prompt, pattern and error of each, so one input
 * loop and one check serve them all.
 *
 * @author HE176322
 */
public enum TextField {

    // Employee id at login.
    EMPLOYEE_ID(Message.INPUT_EMPLOYEE_ID, Constants.ID_PATTERN, Message.INVALID_EMPLOYEE_ID),

    // Password at login.
    PASSWORD(Message.INPUT_PASSWORD, Constants.NOT_BLANK_PATTERN, Message.INVALID_PASSWORD),

    // Text searched in the names.
    KEYWORD(Message.INPUT_KEYWORD, Constants.NOT_BLANK_PATTERN, Message.INVALID_KEYWORD),

    // Asset to borrow.
    ASSET_ID(Message.INPUT_ASSET_ID, Constants.ASSET_ID_PATTERN, Message.INVALID_ASSET_ID),

    // Request to cancel.
    REQUEST_ID(Message.INPUT_REQUEST_ID, Constants.ID_PATTERN, Message.INVALID_REQUEST_ID),

    // Borrow to return.
    BORROW_ID(Message.INPUT_BORROW_ID, Constants.ID_PATTERN, Message.INVALID_BORROW_ID);

    // Prompt printed before the text is typed.
    private final String prompt;

    // Regular expression the text must match.
    private final String pattern;

    // Message shown when it does not.
    private final String error;

    // Creates one constant.
    TextField(String prompt, String pattern, String error) {
        this.prompt = prompt;
        this.pattern = pattern;
        this.error = error;
    }

    // Returns the prompt.
    public String getPrompt() {
        return prompt;
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
