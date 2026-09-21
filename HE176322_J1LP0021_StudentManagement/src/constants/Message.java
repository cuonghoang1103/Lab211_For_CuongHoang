package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- main screen (the brief's wording) -----
    // The brief's main screen, including the sentence under the options.
    public static final String MENU = "WELCOME TO STUDENT MANAGEMENT\n"
            + "1. Create\n"
            + "2. Find and Sort\n"
            + "3. Update/Delete\n"
            + "4. Report\n"
            + "5. Exit\n"
            + "(Please choose 1 to Create, 2 to Find and Sort, "
            + "3 to Update/Delete, 4 to Report and 5 to Exit program).";

    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Enter your choice: ";

    // ----- screen titles -----
    // Title of the Create screen.
    public static final String TITLE_CREATE = "---------- Create Student ----------";

    // Title of the Find and Sort screen.
    public static final String TITLE_FIND_SORT = "---------- Find and Sort ----------";

    // Title of the Update/Delete screen.
    public static final String TITLE_UPDATE_DELETE = "---------- Update/Delete ----------";

    // Title of the Report screen.
    public static final String TITLE_REPORT = "-------------- Report --------------";

    // ----- prompts -----
    // Prompt for the id of a new student.
    public static final String INPUT_ID = "Enter id: ";

    // Prompt for the name of a new student.
    public static final String INPUT_NAME = "Enter student name: ";

    // Prompt for the semester of a new student.
    public static final String INPUT_SEMESTER = "Enter semester: ";

    // Prompt for the course of a new student.
    public static final String INPUT_COURSE = "Enter course name (Java, .Net, C/C++): ";

    // The brief's question once 10 students have been typed.
    public static final String ASK_CONTINUE = "Do you want to continue (Y/N)? ";

    // Prompt of Find and Sort: a name or a part of it.
    public static final String INPUT_SEARCH = "Enter student name (or a part of it): ";

    // Prompt of Update/Delete: the id to find.
    public static final String INPUT_STUDENT_ID = "Enter student id: ";

    // The brief's question after a student is found by id.
    public static final String ASK_UPDATE_DELETE
            = "Do you want to update (U) or delete (D) student? ";

    // Prompt for a new name; a blank answer keeps the old one.
    public static final String INPUT_NEW_NAME
            = "Enter new student name (blank to keep the old one): ";

    // Prompt for a new semester; a blank answer keeps the old one.
    public static final String INPUT_NEW_SEMESTER
            = "Enter new semester (blank to keep the old one): ";

    // Prompt for a new course; a blank answer keeps the old one.
    public static final String INPUT_NEW_COURSE
            = "Enter new course name (blank to keep the old one): ";

    // ----- table header labels -----
    // Header label of the name column.
    public static final String LABEL_NAME = "Student name";

    // Header label of the semester column.
    public static final String LABEL_SEMESTER = "Semester";

    // Header label of the course column.
    public static final String LABEL_COURSE = "Course";

    // ----- input errors -----
    // A value that must be a whole number was not one.
    public static final String INVALID_NUMBER = "You must input a number.";

    // Menu choice outside the allowed range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // Answer that is not one of two letters; %s are the two letters.
    public static final String INVALID_OPTION = "Please enter %s or %s.";

    // Fewer than 10 students in the list (stored + kept in this Create); %d the minimum,
    // %d the count.
    public static final String NEED_MORE = "At least %d students are required - %d so far.";

    // ----- business errors -----
    // The id was left blank.
    public static final String ID_EMPTY = "ID cannot be empty.";

    // The id is already used; %s is the id.
    public static final String ID_EXIST = "ID [%s] already exists.";

    // No student has this id; %s is the id.
    public static final String ID_NOT_EXIST = "ID [%s] does not exist.";

    // The student name was left blank.
    public static final String NAME_EMPTY = "Student name cannot be empty.";

    // Semester of 0 or below.
    public static final String INVALID_SEMESTER = "Semester must be greater than 0.";

    // Course outside the brief's three; %s lists the three.
    public static final String INVALID_COURSE = "Course must be one of: %s.";

    // Find, update, delete or report asked on an empty list.
    public static final String LIST_EMPTY = "The student list is empty.";

    // Find and Sort with a blank search text.
    public static final String KEYWORD_EMPTY = "Search keyword cannot be empty.";

    // ----- results -----
    // A student was added; %s is the id.
    public static final String ADD_SUCCESS = "Student [%s] has been added.";

    // A student was updated; %s is the id.
    public static final String UPDATE_SUCCESS = "Student [%s] has been updated.";

    // A student was deleted; %s is the id.
    public static final String DELETE_SUCCESS = "Student [%s] has been deleted.";

    // Find and Sort matched nobody.
    public static final String NOT_FOUND = "No student found.";

    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
