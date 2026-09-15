package constants;

/**
 * Numbers, formats and file names the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;
    // Menu option: add an expense.
    public static final int MENU_ADD = 1;
    // Menu option: display all expenses.
    public static final int MENU_DISPLAY = 2;
    // Menu option: delete an expense.
    public static final int MENU_DELETE = 3;
    // Menu option: quit; also the largest option.
    public static final int MENU_EXIT = 4;

    // ID of the very first expense (the brief: "the first expense ID: 1").
    public static final int FIRST_ID = 1;

    // The data file, in the project folder (next to build.xml).
    public static final String FILE_NAME = "expenses.txt";
    // Field separator in the data file (commas are common in content).
    public static final String FILE_SEPARATOR = "|";
    // The same separator as a regular expression, for split().
    public static final String FILE_SEPARATOR_REGEX = "\\|";
    // Fields per line: id, date, amount, content.
    public static final int FILE_FIELDS = 4;
    // Position of the ID in a line of the data file.
    public static final int FIELD_ID = 0;
    // Position of the date in a line of the data file.
    public static final int FIELD_DATE = 1;
    // Position of the amount in a line of the data file.
    public static final int FIELD_AMOUNT = 2;
    // Position of the content in a line of the data file.
    public static final int FIELD_CONTENT = 3;

    // The brief's date format.
    public static final String DATE_FORMAT = "dd-MMM-yyyy";
    // Shape of a typed date: 1-2 digit day, 3-letter month, 4-digit year.
    public static final String DATE_REGEX = "\\d{1,2}-[A-Za-z]{3}-\\d{4}";

    // Table header: ID, Date, Amount, Content.
    public static final String HEADER_FORMAT = "%-4s %-13s %-12s %s";
    // One table row, same widths; the amount arrives already as text.
    public static final String ROW_FORMAT = "%-4d %-13s %-12s %s";
    // Money as in the brief's table: "100", or "100.1" when not whole.
    public static final String MONEY_FORMAT = "0.##";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
