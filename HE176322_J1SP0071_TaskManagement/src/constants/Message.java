package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title and options of the main menu (the brief's words, "exit" too).
    public static final String MENU = "========= Task program =========\n"
            + "1. Add Task\n"
            + "2. Delete task\n"
            + "3. Display Task\n"
            + "4. exit";
    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Please choose one option: ";

    // ----- add screen -----
    // Title of the add screen (the brief).
    public static final String TITLE_ADD = "------------Add Task---------------";
    // Prompt for the requirement name.
    public static final String INPUT_REQUIREMENT_NAME = "Requirement Name: ";
    // Prompt for the task type ID.
    public static final String INPUT_TASK_TYPE = "Task Type: ";
    // Prompt for the date.
    public static final String INPUT_DATE = "Date: ";
    // Prompt for the plan from time.
    public static final String INPUT_FROM = "From: ";
    // Prompt for the plan to time.
    public static final String INPUT_TO = "To: ";
    // Prompt for the assignee.
    public static final String INPUT_ASSIGNEE = "Assignee: ";
    // Prompt for the reviewer.
    public static final String INPUT_REVIEWER = "Reviewer: ";

    // ----- delete screen -----
    // Title of the delete screen (the brief).
    public static final String TITLE_DELETE = "---------Del Task------";
    // Prompt for the ID to delete (the brief: no space after the colon).
    public static final String INPUT_ID = "ID:";

    // ----- task table -----
    // Title of the task table (the brief).
    public static final String TITLE_TASK = "----------------------------------------- Task "
            + "---------------------------------------";
    // Header label: ID.
    public static final String LABEL_ID = "ID";
    // Header label: requirement name.
    public static final String LABEL_NAME = "Name";
    // Header label: task type.
    public static final String LABEL_TASK_TYPE = "Task Type";
    // Header label: date.
    public static final String LABEL_DATE = "Date";
    // Header label: plan time.
    public static final String LABEL_TIME = "Time";
    // Header label: assignee.
    public static final String LABEL_ASSIGNEE = "Assignee";
    // Header label: reviewer.
    public static final String LABEL_REVIEWER = "Reviewer";
    // Shown instead of the table when there is no task.
    public static final String NO_TASK = "There is no task yet.";

    // ----- field names used inside the plan-time messages -----
    // Name of the plan from field.
    public static final String LABEL_PLAN_FROM = "Plan From";
    // Name of the plan to field.
    public static final String LABEL_PLAN_TO = "Plan To";

    // ----- validation errors -----
    // Menu choice is not a number.
    public static final String INVALID_NUMBER = "You must input a number.";
    // Menu choice out of range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";
    // Requirement name left blank.
    public static final String NAME_EMPTY = "Requirement Name cannot be empty.";
    // Assignee left blank.
    public static final String ASSIGNEE_EMPTY = "Assignee cannot be empty.";
    // Reviewer left blank.
    public static final String REVIEWER_EMPTY = "Reviewer cannot be empty.";
    // Task type left blank.
    public static final String TYPE_EMPTY = "Task Type cannot be empty.";
    // Task type is not a number.
    public static final String TYPE_NOT_NUMBER = "Task Type must be a number.";
    // Task type is a number but no type has it; %d: typed, first, last.
    public static final String TYPE_NOT_EXIST
            = "Task Type [%d] does not exist. It must be %d to %d.";
    // Date left blank.
    public static final String DATE_EMPTY = "Date cannot be empty.";
    // Date is not a real dd-MM-yyyy date.
    public static final String DATE_INVALID
            = "Date must be a real date in the format dd-MM-yyyy.";
    // A plan time left blank; %s is the field name.
    public static final String PLAN_EMPTY = "%s cannot be empty.";
    // A plan time is not a number; %s is the field name.
    public static final String PLAN_NOT_NUMBER = "%s must be a number.";
    // A plan time outside 8.0..17.5; %s is the field name.
    public static final String PLAN_OUT_OF_RANGE = "%s must be between 8.0 and 17.5.";
    // A plan time not on a half hour; %s is the field name.
    public static final String PLAN_NOT_HALF
            = "%s must be a whole or half hour: 8.0, 8.5, 9.0 ... 17.5.";
    // Plan From is not before Plan To (the brief).
    public static final String PLAN_ORDER = "Plan From must be less than Plan To.";
    // ID to delete left blank.
    public static final String ID_EMPTY = "ID cannot be empty.";
    // ID to delete is not a number.
    public static final String ID_NOT_NUMBER = "ID must be a number.";
    // No task has this ID (the brief: "Id must exist in the DB").
    public static final String TASK_NOT_EXIST = "Task [%d] does not exist.";

    // ----- results -----
    // Shown after a task is added; %d is its new ID.
    public static final String ADD_SUCCESS = "Task [%d] has been added.";
    // Shown after a task is deleted; %s is the ID typed.
    public static final String DELETE_SUCCESS = "Task [%s] has been deleted.";
    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
