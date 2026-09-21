package constants;

/**
 * Numbers, answer letters and column layouts the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // ----- main menu -----
    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;

    // Menu option 1: create students.
    public static final int MENU_CREATE = 1;

    // Menu option 2: find and sort.
    public static final int MENU_FIND_SORT = 2;

    // Menu option 3: update or delete.
    public static final int MENU_UPDATE_DELETE = 3;

    // Menu option 4: report.
    public static final int MENU_REPORT = 4;

    // Menu option 5: exit; also the largest option.
    public static final int MENU_EXIT = 5;

    // ----- business rules -----
    // The brief: "Use has to create at least 10 students".
    public static final int MIN_STUDENTS = 10;

    // Smallest legal semester: a semester must be greater than 0.
    public static final int MIN_SEMESTER = 1;

    // ----- answer letters -----
    // Answer "yes" to "Do you want to continue (Y/N)?".
    public static final String YES = "Y";

    // Answer "no" to "Do you want to continue (Y/N)?".
    public static final String NO = "N";

    // Answer "update" to "update (U) or delete (D)".
    public static final String UPDATE = "U";

    // Answer "delete" to "update (U) or delete (D)".
    public static final String DELETE = "D";

    // ----- screen layouts -----
    // Header of the find-and-sort table: name, semester, course.
    public static final String SEARCH_HEADER_FORMAT = "%-20s %-10s %s";

    // One row of the find-and-sort table (Student.toString()).
    public static final String SEARCH_ROW_FORMAT = "%-20s %-10d %s";

    // One report line, exactly the brief's "Nguyen Van A | Java | 2" (ReportItem.toString()).
    public static final String REPORT_FORMAT = "%s | %s | %d";

    // Separator between the course names in "Java, .Net, C/C++".
    public static final String COURSE_SEPARATOR = ", ";

    // Key of a report group: the name in lower case, a NUL character, the course.
    public static final String REPORT_KEY_FORMAT = "%s\u0000%s";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
