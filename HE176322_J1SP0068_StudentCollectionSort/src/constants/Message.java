package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- input screen of the brief -----
    // Title of the program.
    public static final String TITLE = "====== Collection Sort Program ======";

    // Line printed before each student is typed - with the space the brief's screen has
    // at its end.
    public static final String INPUT_INFO = "Please input student information ";

    // Prompt for the name.
    public static final String INPUT_NAME = "Name: ";

    // Prompt for the class.
    public static final String INPUT_CLASSES = "Classes: ";

    // Prompt for the mark.
    public static final String INPUT_MARK = "Mark: ";

    // Question after each student.
    public static final String ASK_MORE
            = "Do you want to enter more student information?(Y/N):";

    // ----- output screen of the brief -----
    // Title of one student block; %d is the position from 1.
    public static final String TITLE_STUDENT = "-------------Student %d-------------";

    // The name line of one block; %s is the name.
    public static final String LABEL_NAME = "Name: %s";

    // The class line of one block; %s is the class.
    public static final String LABEL_CLASSES = "Classes: %s";

    // The mark line of one block; %s is the mark ("100.0", as a float prints).
    public static final String LABEL_MARK = "Mark: %s";

    // ----- validation errors -----
    // The name was left blank.
    public static final String NAME_EMPTY = "Name must not be empty.";

    // The class was left blank.
    public static final String CLASS_EMPTY = "Class must not be empty.";

    // The mark is not a number (the brief: "not a valid number").
    public static final String INVALID_NUMBER = "You must input a number.";

    // The mark is outside the legal range; %s are the bounds.
    public static final String INVALID_MARK = "Mark must be between %s and %s.";

    // The answer was not Y or N.
    public static final String INVALID_YES_NO = "Please answer Y or N.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
