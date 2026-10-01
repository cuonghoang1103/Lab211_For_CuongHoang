package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // Title and options of the main menu.
    public static final String MENU = "========= Note program =========\n"
            + "1. Add Note\n"
            + "2. Delete Note\n"
            + "3. Display Note\n"
            + "4. Exit";

    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Please choose one option: ";

    // Title of the add screen.
    public static final String TITLE_ADD = "------------Add Note------------";

    // Prompt for the content of a note.
    public static final String INPUT_CONTENT = "Content: ";

    // Title of the delete screen.
    public static final String TITLE_DELETE = "------------Del Note------------";

    // Prompt for the ID to delete.
    public static final String INPUT_ID = "ID: ";

    // Title of the note table.
    public static final String TITLE_NOTE = "------------- Note -------------";

    // Header label: ID.
    public static final String LABEL_ID = "ID";

    // Header label: content.
    public static final String LABEL_CONTENT = "Content";

    // Shown instead of the table when there is no note.
    public static final String NO_NOTE = "There is no note yet.";

    // Menu choice is not a number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // Menu choice out of range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // Content left blank.
    public static final String CONTENT_EMPTY = "Content cannot be empty.";

    // ID to delete left blank.
    public static final String ID_EMPTY = "ID cannot be empty.";

    // ID to delete is not a number.
    public static final String ID_NOT_NUMBER = "ID must be a number.";

    // No note has this ID.
    public static final String NOTE_NOT_EXIST = "Note [%d] does not exist.";

    // Shown after a note is added; %d is its new ID.
    public static final String ADD_SUCCESS = "Note [%d] has been added.";

    // Shown after a note is deleted; %d is the ID typed.
    public static final String DELETE_SUCCESS = "Note [%d] has been deleted.";

    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
