package constants;

/**
 * Numbers, formats and patterns the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;
    // Menu option: add a task.
    public static final int MENU_ADD = 1;
    // Menu option: delete a task.
    public static final int MENU_DELETE = 2;
    // Menu option: display the tasks.
    public static final int MENU_DISPLAY = 3;
    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 4;

    // ID of the very first task.
    public static final int FIRST_ID = 1;

    // Earliest plan time: 8h00 (the brief).
    public static final double PLAN_MIN = 8.0;
    // Latest plan time: 17h30 (the brief).
    public static final double PLAN_MAX = 17.5;
    // Plan times move in half hours: 2 steps per hour.
    public static final int STEPS_PER_HOUR = 2;
    // Tolerance when checking that a double is a whole number of steps.
    public static final double EPSILON = 1e-9;

    // The date format of the brief.
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    // The "Time" column: plan from - plan to, one decimal each.
    public static final String TIME_FORMAT = "%.1f-%.1f";

    // One row of the task table.
    public static final String ROW_FORMAT = "%-4d%-20s%-12s%-14s%-12s%-12s%s";
    // The table header, same widths with a text first column.
    public static final String HEADER_FORMAT = "%-4s%-20s%-12s%-14s%-12s%-12s%s";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
