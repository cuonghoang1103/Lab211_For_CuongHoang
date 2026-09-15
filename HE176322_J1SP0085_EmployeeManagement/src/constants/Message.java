package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title, options and closing line of the main menu (the brief).
    public static final String MENU = "========= EMPLOYEE MANAGEMENT =========\n"
            + "1. Add employees\n"
            + "2. Update employees\n"
            + "3. Remove employees\n"
            + "4. Search employees\n"
            + "5. Sort employees by salary\n"
            + "6. Exit\n"
            + "=======================================";
    // Prompt for the menu choice (the brief).
    public static final String INPUT_CHOICE = "Please select an option: ";

    // ----- screen titles -----
    // Title of the add screen (the brief).
    public static final String TITLE_ADD = "-- Add employee --";
    // Title of the update screen.
    public static final String TITLE_UPDATE = "-- Update employee --";
    // Title of the remove screen.
    public static final String TITLE_REMOVE = "-- Remove employee --";
    // Title of the search screen.
    public static final String TITLE_SEARCH = "-- Search employees --";
    // Title of the sorted list (the brief).
    public static final String TITLE_SORT = "Employees sorted by salary (ascending):";
    // Hint printed before the update prompts.
    public static final String KEEP_HINT = "Press Enter to keep the value in brackets.";

    // ----- field prompts (the brief: label padded to 11, then ": ") -----
    // Prompt for the ID.
    public static final String LABEL_ID = "Id         : ";
    // Prompt for the first name.
    public static final String LABEL_FIRST_NAME = "First name : ";
    // Prompt for the last name.
    public static final String LABEL_LAST_NAME = "Last name  : ";
    // Prompt for the phone.
    public static final String LABEL_PHONE = "Phone      : ";
    // Prompt for the email.
    public static final String LABEL_EMAIL = "Email      : ";
    // Prompt for the address.
    public static final String LABEL_ADDRESS = "Address    : ";
    // Prompt for the date of birth.
    public static final String LABEL_DOB = "DOB        : ";
    // Prompt for the sex.
    public static final String LABEL_SEX = "Sex        : ";
    // Prompt for the salary.
    public static final String LABEL_SALARY = "Salary     : ";
    // Prompt for the agency.
    public static final String LABEL_AGENCY = "Agency     : ";
    // The old value shown after a prompt on update; %s is the value.
    public static final String CURRENT_VALUE = "[%s] ";
    // Prompt for the search text (the brief).
    public static final String INPUT_KEYWORD = "Enter name (or part): ";

    // ----- table headers -----
    // Header label: ID.
    public static final String HEADER_ID = "Id";
    // Header label: first name.
    public static final String HEADER_FIRST_NAME = "First name";
    // Header label: last name.
    public static final String HEADER_LAST_NAME = "Last name";
    // Header label: full name.
    public static final String HEADER_NAME = "Name";
    // Header label: salary.
    public static final String HEADER_SALARY = "Salary";
    // Header label: agency.
    public static final String HEADER_AGENCY = "Agency";
    // Line under the search header.
    public static final String SEARCH_LINE = "-----------------------------------------------";
    // Line under the sort header.
    public static final String SORT_LINE = "-----------------------------------------";

    // ----- validation errors -----
    // Menu choice is not a number.
    public static final String INVALID_NUMBER = "You must input a number.";
    // Menu choice out of range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";
    // A required field left blank (the brief: all fields are required).
    public static final String FIELD_REQUIRED = "This field is required.";
    // Phone with a non-digit.
    public static final String PHONE_INVALID = "Phone must contain digits only.";
    // Email in a wrong format.
    public static final String EMAIL_INVALID = "Email must look like name@domain.com.";
    // DOB not a real yyyy-MM-dd date.
    public static final String DOB_INVALID = "DOB must be a real date in yyyy-MM-dd format.";
    // Sex other than Male/Female.
    public static final String SEX_INVALID = "Sex must be Male or Female.";
    // Salary is not a number.
    public static final String SALARY_NOT_NUMBER = "Salary must be a number.";
    // Salary is zero or negative.
    public static final String SALARY_NOT_POSITIVE = "Salary must be greater than 0.";
    // Search text left blank.
    public static final String KEYWORD_EMPTY = "Please type something to search for.";

    // ----- business errors -----
    // Update/remove/search/sort on an empty list.
    public static final String LIST_EMPTY = "=> The employee list is empty.";
    // Duplicate ID on add; %s is the ID.
    public static final String ID_EXIST = "=> Employee id %s already exists.";
    // Unknown ID on update/remove; %s is the ID.
    public static final String ID_NOT_FOUND = "=> No employee found with id %s.";

    // ----- results -----
    // Shown after an add; %s is the ID (the brief).
    public static final String ADD_SUCCESS = "=> Employee %s added successfully.";
    // Shown after an update; %s is the ID.
    public static final String UPDATE_SUCCESS = "=> Employee %s updated successfully.";
    // The updated employee, indented; %s is its toString().
    public static final String UPDATED_DETAIL = "   %s";
    // Shown after a remove; %s is the ID.
    public static final String REMOVE_SUCCESS = "=> Employee %s removed successfully.";
    // Shown when a search finds nobody; %s is the search text.
    public static final String NO_MATCH = "=> No employee matches \"%s\".";
    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
