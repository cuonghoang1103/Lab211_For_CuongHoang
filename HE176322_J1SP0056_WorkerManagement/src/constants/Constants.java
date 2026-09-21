package constants;

/**
 * Numbers and formats the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;

    // Menu option: add a worker.
    public static final int MENU_ADD = 1;

    // Menu option: up salary.
    public static final int MENU_UP = 2;

    // Menu option: down salary.
    public static final int MENU_DOWN = 3;

    // Menu option: display the salary log.
    public static final int MENU_DISPLAY = 4;

    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 5;

    // Youngest legal age (the brief: 18 to 50).
    public static final int MIN_AGE = 18;

    // Oldest legal age (the brief: 18 to 50).
    public static final int MAX_AGE = 50;

    // Date format of the brief's table.
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    // Table header: code, name, age, salary, status, date.
    public static final String HEADER_FORMAT = "%-8s%-12s%-6s%-12s%-8s%s";

    // One table row, same widths; the salary arrives already as text.
    public static final String ROW_FORMAT = "%-8s%-12s%-6d%-12s%-8s%s";

    // Money as in the brief's table: "1100", or "1100.5" when not whole.
    public static final String MONEY_FORMAT = "0.##";

    // Worker.toString(): the code, then the name.
    public static final String WORKER_FORMAT = "%s - %s";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
