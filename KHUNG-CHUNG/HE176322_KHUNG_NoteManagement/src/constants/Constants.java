package constants;

/**
 * Numbers, formats and patterns the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;

    // Menu option: add a note.
    public static final int MENU_ADD = 1;

    // Menu option: delete a note.
    public static final int MENU_DELETE = 2;

    // Menu option: display the notes.
    public static final int MENU_DISPLAY = 3;

    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 4;

    // ID of the very first note.
    public static final int FIRST_ID = 1;

    // A new ID is the last ID plus this step.
    public static final int ID_STEP = 1;

    // One row of the note table: ID, then content.
    public static final String ROW_FORMAT = "%-4d%s";

    // The table header, same widths with a text first column.
    public static final String HEADER_FORMAT = "%-4s%s";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
