package constants;

/**
 * Numbers, separators and patterns the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;
    // Menu option 1: import a CSV file.
    public static final int MENU_IMPORT = 1;
    // Menu option 2: format the Address column.
    public static final int MENU_FORMAT_ADDRESS = 2;
    // Menu option 3: format the Name column.
    public static final int MENU_FORMAT_NAME = 3;
    // Menu option 4: export a CSV file.
    public static final int MENU_EXPORT = 4;
    // Menu option 5: exit; also the largest option.
    public static final int MENU_EXIT = 5;

    // Columns of a row: ID, Name, Email, Phone, Address.
    public static final int FIELD_COUNT = 5;
    // Position of ID in a row.
    public static final int ID_INDEX = 0;
    // Position of Name in a row.
    public static final int NAME_INDEX = 1;
    // Position of Email in a row.
    public static final int EMAIL_INDEX = 2;
    // Position of Phone in a row.
    public static final int PHONE_INDEX = 3;
    // Position of Address in a row (the last one).
    public static final int ADDRESS_INDEX = 4;

    // What separates two fields in the file.
    public static final String FIELD_SEPARATOR = ",";
    // What separates two fields when a row is written back: the brief's ", ".
    public static final String FIELD_JOIN = ", ";
    // One or more whitespace characters (spaces, tabs).
    public static final String SPACES = "\\s+";
    // The single space that replaces a run of whitespace.
    public static final String SPACE = " ";
    // Joins the lines of dataCSV.
    public static final String NEW_LINE = "\n";
    // Splits dataCSV back into lines (a Windows \r\n works too).
    public static final String LINE_SPLIT = "\\r?\\n";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
