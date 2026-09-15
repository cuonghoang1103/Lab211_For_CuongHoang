package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Main screen of the brief, word for word.
    public static final String MENU = "CANDIDATE MANAGEMENT SYSTEM\n"
            + "1. Experience\n"
            + "2. Fresher\n"
            + "3. Internship\n"
            + "4. Searching\n"
            + "5. Exit\n"
            + "(Please choose 1 to Create Experience Candidate, "
            + "2 to Create Fresher Candidate, 3 to Internship Candidate, "
            + "4 to Searching and 5 to Exit program).";
    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Enter your choice: ";
    // Title of the create screen; %s is the type label.
    public static final String TITLE_CREATE = "---------- Create %s Candidate ----------";

    // ----- prompts: common fields -----
    // Prompt for the candidate id.
    public static final String INPUT_ID = "Input candidate id: ";
    // Prompt for the first name.
    public static final String INPUT_FIRST_NAME = "Input first name: ";
    // Prompt for the last name.
    public static final String INPUT_LAST_NAME = "Input last name: ";
    // Prompt for the birth date (a year).
    public static final String INPUT_BIRTH_DATE = "Input birth date: ";
    // Prompt for the address.
    public static final String INPUT_ADDRESS = "Input address: ";
    // Prompt for the phone.
    public static final String INPUT_PHONE = "Input phone: ";
    // Prompt for the email.
    public static final String INPUT_EMAIL = "Input email: ";

    // ----- prompts: fields of one kind only -----
    // Experience: years of experience.
    public static final String INPUT_EXPERIENCE = "Input year of experience: ";
    // Experience: professional skill.
    public static final String INPUT_SKILL = "Input professional skill: ";
    // Fresher: graduation date.
    public static final String INPUT_GRADUATION_DATE = "Input graduation date: ";
    // Fresher: rank of graduation, listing the 4 legal values.
    public static final String INPUT_RANK
            = "Input rank of graduation (Excellence, Good, Fair, Poor): ";
    // Fresher: university the student graduated from.
    public static final String INPUT_EDUCATION = "Input education: ";
    // Intern: majors.
    public static final String INPUT_MAJORS = "Input majors: ";
    // Intern: semester.
    public static final String INPUT_SEMESTER = "Input semester: ";
    // Intern: university name.
    public static final String INPUT_UNIVERSITY = "Input university name: ";
    // The brief's question after each created candidate.
    public static final String ASK_CONTINUE = "Do you want to continue (Y/N)? ";

    // ----- search -----
    // Search prompt for the name (the brief's wording).
    public static final String INPUT_SEARCH_NAME
            = "Input Candidate name (First name or Last name): ";
    // Search prompt for the type (the brief's wording).
    public static final String INPUT_TYPE = "Input type of candidate: ";
    // Title above the grouped listing (the brief's wording).
    public static final String LIST_TITLE = "List of candidate:";
    // Title above the search result (the brief's wording).
    public static final String FOUND_TITLE = "The candidates found:";
    // Shown when the search matches nobody.
    public static final String NOT_FOUND = "No candidate found.";

    // ----- results -----
    // After a candidate is stored; %s are the type label and the id.
    public static final String CREATE_SUCCESS = "%s candidate [%s] has been created.";
    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // ----- validation errors (one field at a time, asked again) -----
    // A value that must be a whole number was not.
    public static final String INVALID_NUMBER = "You must input a number.";
    // A choice outside its range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";
    // The brief: 4 digits, 1900..current year.
    public static final String INVALID_BIRTH_DATE
            = "Birth date must be a number of 4 digits from 1900 to the current year.";
    // The brief: a number with at least 10 characters.
    public static final String INVALID_PHONE
            = "Phone must be a number with at least 10 digits.";
    // The brief: account name @ domain.
    public static final String INVALID_EMAIL = "Email must have the format "
            + "<account name>@<domain> (eg: annguyen@fpt.edu.vn).";
    // The brief: 0..100.
    public static final String INVALID_EXPERIENCE
            = "Year of experience must be a number from 0 to 100.";
    // The brief: one of 4 values.
    public static final String INVALID_RANK
            = "Rank of graduation must be one of: Excellence, Good, Fair, Poor.";
    // Anything but Y or N to the continue question.
    public static final String INVALID_YES_NO = "Please enter Y or N.";

    // ----- business errors (need the whole list, thrown by the service) -----
    // The id was left blank.
    public static final String ID_EMPTY = "Candidate id cannot be empty.";
    // The id is already used by another candidate; %s is the id.
    public static final String ID_EXISTS = "Candidate id [%s] already exists.";
    // The first name was left blank.
    public static final String FIRST_NAME_EMPTY = "First name cannot be empty.";
    // The last name was left blank.
    public static final String LAST_NAME_EMPTY = "Last name cannot be empty.";
    // Search chosen before any candidate was created.
    public static final String LIST_EMPTY = "The candidate list is empty.";
    // The search name was left blank.
    public static final String NAME_EMPTY = "Candidate name cannot be empty.";
    // A type the factory was never taught to build (programming mistake).
    public static final String UNKNOWN_TYPE = "Type of candidate must be 0, 1 or 2.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
