package constants;

/**
 * Every message, prompt and label the manager's program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Main menu of the manager's program.
    public static final String MENU = "\n========== BMLT ASSET - MANAGER ==========\n"
            + "1. Login\n"
            + "2. Search asset by name\n"
            + "3. Create new asset\n"
            + "4. Update asset's information\n"
            + "5. Approve the request of employee\n"
            + "6. Show list of borrow asset\n"
            + "7. Quit\n"
            + "==========================================";
    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Your choice: ";

    // ----- screen titles -----
    // Title of Function 1.
    public static final String TITLE_LOGIN = "--- Login ---";
    // Title of Function 2.
    public static final String TITLE_SEARCH = "--- Search asset by name ---";
    // Title of Function 3.
    public static final String TITLE_CREATE = "--- Create new asset ---";
    // Title of Function 4.
    public static final String TITLE_UPDATE = "--- Update asset information ---";
    // Title of Function 5.
    public static final String TITLE_APPROVE = "--- Approve the request of employee ---";
    // Title of Function 6.
    public static final String TITLE_BORROWS = "--- List of borrowed assets ---";

    // ----- prompts -----
    // Prompt for the employee id at login.
    public static final String INPUT_EMPLOYEE_ID = "Employee ID: ";
    // Prompt for the password at login.
    public static final String INPUT_PASSWORD = "Password: ";
    // Prompt for the text searched in the asset names.
    public static final String INPUT_KEYWORD = "Enter a part of the asset name: ";
    // Prompt for the asset id.
    public static final String INPUT_ASSET_ID = "Enter asset id: ";
    // Prompt for the asset name.
    public static final String INPUT_NAME = "Enter name: ";
    // Prompt for the color.
    public static final String INPUT_COLOR = "Enter color: ";
    // Prompt for the price.
    public static final String INPUT_PRICE = "Enter price: ";
    // Prompt for the weight.
    public static final String INPUT_WEIGHT = "Enter weight: ";
    // Prompt for the quantity in stock.
    public static final String INPUT_QUANTITY = "Enter quantity: ";
    // Hint printed before the update prompts.
    public static final String KEEP_HINT = "Leave a field blank to keep the current value.";
    // Prompt for a new name.
    public static final String INPUT_NEW_NAME = "New name: ";
    // Prompt for a new color.
    public static final String INPUT_NEW_COLOR = "New color: ";
    // Prompt for a new price.
    public static final String INPUT_NEW_PRICE = "New price: ";
    // Prompt for a new weight.
    public static final String INPUT_NEW_WEIGHT = "New weight: ";
    // Prompt for a new quantity.
    public static final String INPUT_NEW_QUANTITY = "New quantity: ";
    // Prompt for the request to approve.
    public static final String INPUT_REQUEST_ID = "Enter request id to approve: ";
    // The brief: "Ask to continuous create new asset or go back to the main menu".
    public static final String ASK_CREATE_ANOTHER = "Create another asset? (Y/N): ";

    // ----- input errors -----
    // Choice outside the menu; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";
    // Answer that is not Y or N.
    public static final String INVALID_YES_NO = "Please enter Y or N.";
    // Wrong asset id format.
    public static final String INVALID_ASSET_ID
            = "Asset id must be the letter A and 3 digits, for example A003.";
    // Wrong name.
    public static final String INVALID_NAME = "Name must be 1 to 40 characters without commas.";
    // Wrong color.
    public static final String INVALID_COLOR = "Color must be 1 to 40 characters without commas.";
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
    // Price not greater than 0.
    public static final String INVALID_PRICE = "Price must be a number greater than 0.";
    // Weight not greater than 0.
    public static final String INVALID_WEIGHT = "Weight must be a number greater than 0.";
    // Quantity not a whole number from 0.
    public static final String INVALID_QUANTITY
            = "Quantity must be a whole number from 0 to 1000000.";

    // ----- login -----
    // The brief's words for a good login.
    public static final String LOGIN_SUCCESS = "Successfully";
    // The brief's words for a bad login.
    public static final String LOGIN_FAILED = "Incorrect id or password";
    // Line after a good login; %s the name, %s the title.
    public static final String WELCOME = "Welcome, %s (%s).";
    // A guarded function chosen before login.
    public static final String LOGIN_FIRST = "You must login first.";
    // A guarded function chosen by an employee; %s is the name.
    public static final String NOT_MANAGER
            = "%s is not a manager. This function is for the manager only.";
    // Title of role MA.
    public static final String TITLE_MANAGER = "Manager";
    // Title of role EM.
    public static final String TITLE_EMPLOYEE = "Employee";

    // ----- assets -----
    // The id is used by another asset; %s is the id.
    public static final String ASSET_EXISTS = "Asset %s already exists.";
    // The brief's words when an asset id is not found.
    public static final String ASSET_NOT_EXIST = "Asset does not exist";
    // Search matched nothing.
    public static final String NOT_FOUND = "No asset found.";
    // An asset was created; %s is the id.
    public static final String CREATE_SUCCESS = "Asset %s has been created.";
    // An asset was updated; %s is the id.
    public static final String UPDATE_SUCCESS = "Asset %s has been updated.";

    // ----- requests and borrows -----
    // request.dat is empty.
    public static final String NO_REQUEST = "There is no request to approve.";
    // No request has this id; %s is the id.
    public static final String REQUEST_NOT_EXIST = "Request %s does not exist.";
    // The asset of a request is gone from asset.dat; %s is its id.
    public static final String REQUEST_ASSET_MISSING = "Asset %s of this request does not exist.";
    // Stock too low; %s asset, %d stock, %s request, %d wanted.
    public static final String NOT_ENOUGH
            = "Not enough stock: %s has %d left but request %s needs %d.";
    // Approval done; %s the request, %s the new borrow.
    public static final String APPROVE_SUCCESS = "Request %s has been approved as borrow %s.";
    // borrow.dat is empty.
    public static final String NO_BORROW = "There is no borrowed asset.";
    // Name shown for an id that is in no file.
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
    // Header of the employee id column.
    public static final String COL_EMPLOYEE = "Employee";
    // Header of the employee name column.
    public static final String COL_EMPLOYEE_NAME = "Employee name";
    // Header of the date column of requests.
    public static final String COL_REQUESTED_AT = "Requested at";
    // Header of the date column of borrows.
    public static final String COL_BORROWED_AT = "Borrowed at";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
