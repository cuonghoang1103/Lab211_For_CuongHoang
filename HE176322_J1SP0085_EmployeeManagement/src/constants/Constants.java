package constants;

/**
 * Numbers, formats and patterns the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;

    // Menu option: add an employee.
    public static final int MENU_ADD = 1;

    // Menu option: update an employee.
    public static final int MENU_UPDATE = 2;

    // Menu option: remove an employee.
    public static final int MENU_REMOVE = 3;

    // Menu option: search employees by name.
    public static final int MENU_SEARCH = 4;

    // Menu option: sort employees by salary.
    public static final int MENU_SORT = 5;

    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 6;

    // The brief: DOB is a valid date.
    public static final String DOB_FORMAT = "yyyy-MM-dd";

    // The brief: phone is digits only.
    public static final String PHONE_REGEX = "\\d+";

    // The brief: email "contains @ and a domain".
    public static final String EMAIL_REGEX
            = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    // The brief: sex is "Male" ...
    public static final String SEX_MALE = "Male";

    // ... or "Female".
    public static final String SEX_FEMALE = "Female";

    // The brief: salary is a positive number, so it must be above this.
    public static final double MIN_SALARY = 0;

    // Salary as on the brief's screen: 1500.00.
    public static final String SALARY_FORMAT = "%.2f";

    // One row of the search result: Id, First name, Last name, Salary, Agency.
    public static final String SEARCH_ROW = "%-7s%-12s%-11s%9s  %s";

    // One row of the sorted list: Id, Name, Salary, Agency.
    public static final String SORT_ROW = "%-7s%-19s%7s  %s";

    // A full name: first name, a space, last name.
    public static final String FULL_NAME_FORMAT = "%s %s";

    // An employee on one line (toString), shown after an update.
    public static final String EMPLOYEE_FORMAT = "Employee{id=%s, name=%s, phone=%s, "
            + "email=%s, address=%s, dob=%s, sex=%s, salary=%s, agency=%s}";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
