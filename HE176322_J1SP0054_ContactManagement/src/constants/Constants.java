package constants;

/**
 * Numbers, formats and patterns the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Menu option: add a contact; also the smallest option.
    public static final int MENU_ADD = 1;

    // Menu option: display all contacts.
    public static final int MENU_DISPLAY = 2;

    // Menu option: delete a contact.
    public static final int MENU_DELETE = 3;

    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 4;

    // ID of the first contact (the brief: "the first contact has ID: 1").
    public static final int FIRST_ID = 1;

    // Step between two IDs (the brief: "last ID contact + 1").
    public static final int ID_STEP = 1;

    // Separator between first name and last name (the brief: first space).
    public static final char NAME_SEPARATOR = ' ';

    // The seven phone formats of the brief, one branch each, in its order: 1234567890 |
    // 123-456-7890 | 123-456-7890 x1234 | 123-456-7890 ext1234 | (123)-456-7890 |
    // 123.456.7890 | 123 456 7890.
    public static final String PHONE_PATTERN = "\\d{10}"
            + "|\\d{3}-\\d{3}-\\d{4}( (x|ext)\\d{4})?"
            + "|\\(\\d{3}\\)-\\d{3}-\\d{4}"
            + "|\\d{3}\\.\\d{3}\\.\\d{4}"
            + "|\\d{3} \\d{3} \\d{4}";

    // One table row: ID, name, first name, last name, group, address, phone.
    public static final String ROW_FORMAT = "%-4s%-18s%-12s%-12s%-8s%-12s%s";

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
