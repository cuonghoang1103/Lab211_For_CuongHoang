package constants;

/**
 * Every message, prompt and label the employee's program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Main menu of the employee's program.
    public static final String MENU = "\n========== BMLT ASSET - EMPLOYEE ==========\n"
            + "1. Login\n"
            + "2. Search asset by name\n"
            + "3. Borrow the assets\n"
            + "4. Cancel request\n"
            + "5. Return asset\n"
            + "6. Quit\n"
            + "===========================================";
    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Your choice: ";

    // ----- screen titles -----
    // Title of Function 1.
    public static final String TITLE_LOGIN = "--- Login ---";
    // Title of Function 2.
    public static final String TITLE_SEARCH = "--- Search asset by name ---";
    // Title of Function 3.
    public static final String TITLE_BORROW = "--- Borrow the assets ---";
    // Title of Function 4.
    public static final String TITLE_CANCEL = "--- Cancel request ---";
    // Title of Function 5.
    public static final String TITLE_RETURN = "--- Return asset ---";

    // ----- prompts -----
    // Prompt for the employee id at login.
    public static final String INPUT_EMPLOYEE_ID = "Employee ID: ";
    // Prompt for the password at login.
    public static final String INPUT_PASSWORD = "Password: ";
    // Prompt for the text searched in the asset names.
    public static final String INPUT_KEYWORD = "Enter a part of the asset name: ";
    // Prompt for the asset to borrow.
    public static final String INPUT_ASSET_ID = "Enter asset id: ";
    // Prompt for the quantity to borrow.
    public static final String INPUT_QUANTITY = "Enter quantity: ";
    // Prompt for the request to cancel.
    public static final String INPUT_REQUEST_ID = "Enter request id to cancel: ";
    // Prompt for the borrow to return.
    public static final String INPUT_BORROW_ID = "Enter borrow id to return: ";
    // The brief: "Ask to continuous or go back to the main menu".
    public static final String ASK_CONTINUE = "Do you want to continue (Y/N)? ";
    // The brief: "confirm message before cancel"; %s is the request id.
    public static final String ASK_CANCEL = "Do you want to cancel request %s? (Y/N): ";
    // The brief: confirm before the return; %s is the borrow id.
    public static final String ASK_RETURN = "Do you want to return borrow %s? (Y/N): ";

    // ----- input errors -----
    // Choice outside the menu; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";
    // Answer that is not Y or N.
    public static final String INVALID_YES_NO = "Please enter Y or N.";
    // Wrong asset id format.
    public static final String INVALID_ASSET_ID
            = "Asset id must be the letter A and 3 digits, for example A001.";
    // Blank search text.
    public static final String INVALID_KEYWORD = "Search text must not be empty.";
    // Blank or spaced employee id.
    public static final String INVALID_EMPLOYEE_ID
            = "Employee ID must be 1 to 10 characters without spaces.";
    // Blank password.
    public static final String INVALID_PASSWORD = "Password must not be empty.";
    // Blank or spaced request id.
    public static final String INVALID_REQUEST_ID
            = "Request id must be 1 to 10 characters without spaces.";
    // Blank or spaced borrow id.
    public static final String INVALID_BORROW_ID
            = "Borrow id must be 1 to 10 characters without spaces.";
    // Quantity not a whole number from 1.
    public static final String INVALID_QUANTITY
            = "Quantity must be a whole number from 1 to 1000000.";

    // ----- login -----
    // The brief's words for a good login.
    public static final String LOGIN_SUCCESS = "Successfully";
    // The brief's words for a bad login.
    public static final String LOGIN_FAILED = "Incorrect id or password";
    // Line after a good login; %s the name, %s the title.
    public static final String WELCOME = "Welcome, %s (%s).";
    // A guarded function chosen before login.
    public static final String LOGIN_FIRST = "You must login first.";
    // A guarded function chosen by the manager.
    public static final String MANAGER_CANNOT_BORROW
            = "A manager cannot borrow assets. Please use the manager's program.";
    // Title of role MA.
    public static final String TITLE_MANAGER = "Manager";
    // Title of role EM.
    public static final String TITLE_EMPLOYEE = "Employee";

    // ----- assets -----
    // No asset has this id.
    public static final String ASSET_NOT_EXIST = "Asset does not exist";
    // Search matched nothing.
    public static final String NOT_FOUND = "No asset found.";
    // asset.dat is empty.
    public static final String NO_ASSET = "There is no asset in the company.";
    // More than the stock; %d is the stock, %s the asset name.
    public static final String NOT_ENOUGH = "Only %d %s left in stock.";

    // ----- requests -----
    // A request was written; %s is its id.
    public static final String REQUEST_SENT = "Request %s has been sent.";
    // This employee has no request.
    public static final String NO_REQUEST = "You have no request.";
    // The id is not one of this employee's requests; %s is the id.
    public static final String NOT_MY_REQUEST = "You have no request with id %s.";
    // A request was deleted; %s is its id.
    public static final String REQUEST_CANCELLED = "Request %s has been cancelled.";
    // The confirmation was answered N.
    public static final String NOTHING_CANCELLED = "Nothing was cancelled.";

    // ----- borrows -----
    // This employee holds no asset.
    public static final String NO_BORROW = "You have no borrowed asset.";
    // The id is not one of this employee's borrows; %s is the id.
    public static final String NOT_MY_BORROW = "You have no borrowed asset with id %s.";
    // A borrow was returned; %s is its id.
    public static final String BORROW_RETURNED = "Borrow %s has been returned.";
    // The confirmation was answered N.
    public static final String NOTHING_RETURNED = "Nothing was returned.";
    // Name shown for an asset id that is in no file.
    public static final String UNKNOWN = "(unknown)";

    // ----- files -----
    // A data file cannot be read or written; %s is its name.
    public static final String FILE_ERROR = "Cannot access data file %s.";
    // The JVM has no MD5 (never happens on a standard JVM).
    public static final String MD5_ERROR = "MD5 is not available.";
    // Shown when the user quits.
    public static final String GOODBYE = "Goodbye.";

    // ----- table headers -----
    // Header of the id column.
    public static final String COL_ID = "Id";
    // Header of the name column.
    public static final String COL_NAME = "Name";
    // Header of the color column.
    public static final String COL_COLOR = "Color";
    // Header of the price column.
    public static final String COL_PRICE = "Price";
    // Header of the weight column.
    public static final String COL_WEIGHT = "Weight";
    // Header of the quantity column.
    public static final String COL_QUANTITY = "Qty";
    // Header of the asset id column.
    public static final String COL_ASSET = "Asset";
    // Header of the asset name column.
    public static final String COL_ASSET_NAME = "Asset name";
    // Header of the date column of requests.
    public static final String COL_REQUESTED_AT = "Requested at";
    // Header of the date column of borrows.
    public static final String COL_BORROWED_AT = "Borrowed at";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
