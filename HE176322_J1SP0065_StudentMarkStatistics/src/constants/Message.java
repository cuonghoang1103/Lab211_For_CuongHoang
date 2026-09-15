package constants;

/**
 * Every message and label the program shows on screen, copied from the brief's screen
 * character for character.
 *
 * @author HE176322
 */
public final class Message {

    // Title printed once at the start.
    public static final String TITLE = "====== Management Student Program ======";
    // Prompt for the name.
    public static final String INPUT_NAME = "Name:";
    // Prompt for the class.
    public static final String INPUT_CLASSES = "Classes:";
    // Label of the maths mark (prompt and messages).
    public static final String MATHS = "Maths";
    // Label of the chemistry mark (prompt and messages).
    public static final String CHEMISTRY = "Chemistry";
    // Label of the physics mark (prompt and messages).
    public static final String PHYSICS = "Physics";
    // A mark prompt: the label followed by a colon.
    public static final String INPUT_MARK = "%s:";
    // Prompt of the Y/N question.
    public static final String INPUT_MORE
            = "Do you want to enter more student information?(Y/N):";

    // The mark is above 10; %s is the subject.
    public static final String MARK_TOO_BIG = "%s is less than equal ten";
    // The mark is below 0; %s is the subject.
    public static final String MARK_TOO_SMALL = "%s is greater than equal zero";
    // The mark is not a number (also an empty line); %s is the subject.
    public static final String MARK_NOT_DIGIT = "%s is digit";

    // Title of one student's block; %d counts from 1.
    public static final String TITLE_STUDENT = "------ Student%d Info ------";
    // Label of the name line.
    public static final String LABEL_NAME = "Name:";
    // Label of the class line.
    public static final String LABEL_CLASSES = "Classes:";
    // Label of the average line.
    public static final String LABEL_AVG = "AVG:";
    // Label of the type line.
    public static final String LABEL_TYPE = "Type:";
    // Title of the statistics block.
    public static final String TITLE_CLASSIFICATION = "--------Classification Info -----";
    // One statistics line: type and percent.
    public static final String PERCENT_LINE = "%s: %s%%";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
