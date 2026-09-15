package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title and options of the main menu.
    public static final String MENU = "========= Doctor Management ==========\n"
            + "1. Add Doctor\n"
            + "2. Update Doctor\n"
            + "3. Delete Doctor\n"
            + "4. Search Doctor\n"
            + "5. Exit";
    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Please choose an option: ";

    // ----- screen titles -----
    // Title printed before the add-doctor form.
    public static final String TITLE_ADD = "--------- Add Doctor ----------";
    // Title printed before the update-doctor form.
    public static final String TITLE_UPDATE = "--------- Update Doctor -------";
    // Title printed before the delete-doctor form.
    public static final String TITLE_DELETE = "--------- Delete Doctor -------";
    // Title printed before the search form.
    public static final String TITLE_SEARCH = "---------- Search Doctor --------";
    // Title printed above the search result.
    public static final String TITLE_RESULT = "--------- Result ------------";

    // ----- prompts -----
    // Prompt for the doctor code.
    public static final String INPUT_CODE = "Enter Code: ";
    // Prompt for the doctor name.
    public static final String INPUT_NAME = "Enter Name: ";
    // Prompt for the specialization.
    public static final String INPUT_SPECIALIZATION = "Enter Specialization: ";
    // Prompt for the availability.
    public static final String INPUT_AVAILABILITY = "Enter Availability: ";
    // Prompt for the search text.
    public static final String INPUT_SEARCH = "Enter text: ";

    // ----- table header labels -----
    // Header label of the code column.
    public static final String LABEL_CODE = "Code";
    // Header label of the name column.
    public static final String LABEL_NAME = "Name";
    // Header label of the specialization column.
    public static final String LABEL_SPECIALIZATION = "Specialization";
    // Header label of the availability column.
    public static final String LABEL_AVAILABILITY = "Availability";

    // ----- validation errors -----
    // A value that must be a number was not a number.
    public static final String INVALID_NUMBER = "Please input number";
    // Menu choice outside the allowed range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";
    // The doctor code was left blank.
    public static final String CODE_BLANK = "Code cannot be blank.";
    // Availability below zero (the brief: Availability >= 0).
    public static final String INVALID_AVAILABILITY
            = "Availability must be greater than or equal to 0";

    // ----- business errors (the brief's own wording, character for character) -----
    // The doctor map does not exist (brief, all four functions).
    public static final String DATABASE_NOT_EXIST = "Database does not exist";
    // The doctor passed to add was null (brief, function 1).
    public static final String DATA_NOT_EXIST_ADD = "Data does not exist";
    // The doctor passed to update/delete was null (brief, functions 2-3).
    public static final String DATA_NOT_EXIST = "Data doesn't exist";
    // Duplicate code on add; %s is the code (brief, function 1).
    public static final String DUPLICATE_CODE = "Doctor code [%s] is duplicate";
    // Unknown code on update/delete (brief, functions 2-3).
    public static final String CODE_NOT_EXIST = "Doctor code doesn’t exist";

    // ----- results -----
    // Shown after a doctor is added.
    public static final String ADD_SUCCESS = "Add doctor successfully.";
    // Shown after a doctor is updated.
    public static final String UPDATE_SUCCESS = "Update doctor successfully.";
    // Shown after a doctor is deleted.
    public static final String DELETE_SUCCESS = "Delete doctor successfully.";
    // Shown when a search matches nobody.
    public static final String NOT_FOUND = "No doctor found.";
    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
