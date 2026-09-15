package constants;

/**
 * Numbers, separators and formats the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;
    // Menu option: find person info.
    public static final int MENU_FIND = 1;
    // Menu option: copy the words into a new file.
    public static final int MENU_COPY = 2;
    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 3;

    // The brief: "The amount not less than 0".
    public static final double MIN_MONEY = 0;
    // The brief: a salary in wrong format or missing "defaults to 0".
    public static final double DEFAULT_SALARY = 0;

    // The brief: the fields of a line are "separated by ;".
    public static final String SEPARATOR = ";";
    // split(..., -1) keeps empty fields, so "Lan;Hue;" has 3 parts.
    public static final int KEEP_EMPTY_FIELDS = -1;
    // Position of the name on a line.
    public static final int INDEX_NAME = 0;
    // Position of the address on a line.
    public static final int INDEX_ADDRESS = 1;
    // Position of the salary on a line.
    public static final int INDEX_SALARY = 2;

    // Words of a text are separated by one or more spaces/tabs.
    public static final String WORD_SEPARATOR = "\\s+";

    // One row of the result table: name, address, money (the brief).
    public static final String ROW_FORMAT = "%s\t\t%s\t\t%.1f";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
