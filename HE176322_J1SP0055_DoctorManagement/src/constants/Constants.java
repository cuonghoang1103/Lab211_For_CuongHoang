package constants;

/**
 * Numbers and formats the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;
    // Menu option: add a doctor.
    public static final int MENU_ADD = 1;
    // Menu option: update a doctor.
    public static final int MENU_UPDATE = 2;
    // Menu option: delete a doctor.
    public static final int MENU_DELETE = 3;
    // Menu option: search doctors.
    public static final int MENU_SEARCH = 4;
    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 5;

    // Smallest legal availability (the brief: Availability >= 0).
    public static final int MIN_AVAILABILITY = 0;

    // One table row: code, name, specialization, availability.
    public static final String ROW_FORMAT = "%-10s%-15s%-20s%d";
    // The table header uses the same widths, with a text last column.
    public static final String HEADER_FORMAT = "%-10s%-15s%-20s%s";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
