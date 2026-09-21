package constants;

/**
 * Every message, prompt and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menus -----
    // Main menu: the brief's lines, word for word, ending with "Others- Quit".
    public static final String MAIN_MENU = "\n===== VEHICLE MANAGEMENT =====\n"
            + "1. Load data from file\n"
            + "2. Add new vehicle\n"
            + "3. Update vehicle by ID\n"
            + "4. Delete vehicle ID\n"
            + "5. Search vehicle\n"
            + "6. Show vehicle list\n"
            + "7. Store data to file\n"
            + "Others- Quit\n"
            + "==============================";

    // Sub menu of Function 2.
    public static final String ADD_MENU = "\n===== ADD NEW VEHICLE =====\n"
            + "1. Car\n"
            + "2. Motorbike\n"
            + "3. Back to main menu\n"
            + "===========================";

    // Sub menu of Function 5: the brief's 5.1 and 5.2, word for word.
    public static final String SEARCH_MENU = "\n===== SEARCH VEHICLE =====\n"
            + "1. Search by name(descending)\n"
            + "2. Search by id\n"
            + "3. Back to main menu\n"
            + "==========================";

    // Sub menu of Function 6: the brief's 6.1 and 6.2, word for word.
    public static final String SHOW_MENU = "\n===== SHOW VEHICLE LIST =====\n"
            + "1. Show all\n"
            + "2. Show all (descending by price)\n"
            + "3. Back to main menu\n"
            + "=============================";

    // Prompt for any menu choice.
    public static final String INPUT_CHOICE = "Your choice: ";

    // ----- screen titles -----
    // Title of Function 1.
    public static final String TITLE_LOAD = "--- Load data from file ---";

    // Title of Function 2; %s is Car or Motorbike.
    public static final String TITLE_ADD = "--- Add new %s ---";

    // Title of Function 3.
    public static final String TITLE_UPDATE = "--- Update vehicle by ID ---";

    // Title of Function 4.
    public static final String TITLE_DELETE = "--- Delete vehicle by ID ---";

    // Title of Function 5.1.
    public static final String TITLE_SEARCH_NAME = "--- Search vehicle by name ---";

    // Title of Function 5.2.
    public static final String TITLE_SEARCH_ID = "--- Search vehicle by id ---";

    // Title of Function 6.1.
    public static final String TITLE_SHOW_ALL = "--- All vehicles in the show room ---";

    // Title of Function 6.2.
    public static final String TITLE_SHOW_PRICE = "--- All vehicles, most expensive first ---";

    // Title of Function 7.
    public static final String TITLE_STORE = "--- Store data to file ---";

    // ----- prompts (add) -----
    // Prompt for the id.
    public static final String INPUT_ID = "Enter id: ";

    // Prompt for the name.
    public static final String INPUT_NAME = "Enter name: ";

    // Prompt for the color.
    public static final String INPUT_COLOR = "Enter color: ";

    // Prompt for the price.
    public static final String INPUT_PRICE = "Enter price: ";

    // Prompt for the brand.
    public static final String INPUT_BRAND = "Enter brand: ";

    // Prompt for the type of a car.
    public static final String INPUT_CAR_TYPE = "Enter type (Sport/Travel/Family/Pickup): ";

    // Prompt for the year of manufacture of a car.
    public static final String INPUT_YEAR = "Enter year of manufacture: ";

    // Prompt for the speed of a motorbike.
    public static final String INPUT_SPEED = "Enter speed (km/h): ";

    // Prompt for the license of a motorbike.
    public static final String INPUT_LICENSE = "Require license? (Y/N): ";

    // Prompt for the text searched in the names.
    public static final String INPUT_KEYWORD = "Enter a part of the name: ";

    // ----- prompts (update: blank keeps the old value) -----
    // Hint printed before the update prompts.
    public static final String KEEP_HINT = "Leave a field blank to keep the current value.";

    // Prompt for a new name.
    public static final String INPUT_NEW_NAME = "New name: ";

    // Prompt for a new color.
    public static final String INPUT_NEW_COLOR = "New color: ";

    // Prompt for a new price.
    public static final String INPUT_NEW_PRICE = "New price: ";

    // Prompt for a new brand.
    public static final String INPUT_NEW_BRAND = "New brand: ";

    // Prompt for a new car type.
    public static final String INPUT_NEW_CAR_TYPE = "New type (Sport/Travel/Family/Pickup): ";

    // Prompt for a new year of manufacture.
    public static final String INPUT_NEW_YEAR = "New year of manufacture: ";

    // Prompt for a new speed.
    public static final String INPUT_NEW_SPEED = "New speed (km/h): ";

    // Prompt for a new license answer.
    public static final String INPUT_NEW_LICENSE = "New require license (Y/N): ";

    // ----- questions -----
    // The brief: "Ask to continuous create new vehicle or go back to the main menu".
    public static final String ASK_ADD_ANOTHER = "Add another vehicle? (Y/N): ";

    // The brief: "Before the delete system must show confirm message"; %s is the id typed.
    public static final String ASK_DELETE = "Delete vehicle %s? (Y/N): ";

    // ----- input errors -----
    // Main menu choice that is not a number (any other number means Quit).
    public static final String INVALID_NUMBER = "Please enter a number.";

    // Sub menu choice outside the menu; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // Answer that is not Y or N.
    public static final String INVALID_YES_NO = "Please enter Y or N.";

    // Wrong id format.
    public static final String INVALID_ID
            = "ID must be one letter followed by 3 digits, for example C001.";

    // Wrong name format.
    public static final String INVALID_NAME
            = "Name must be 2 to 30 letters, digits, spaces or hyphens.";

    // Wrong color format.
    public static final String INVALID_COLOR = "Color must be 2 to 15 letters.";

    // Wrong brand format.
    public static final String INVALID_BRAND
            = "Brand must be 2 to 20 letters, digits, spaces or hyphens.";

    // Car type outside the four allowed.
    public static final String INVALID_CAR_TYPE
            = "Type must be one of: Sport, Travel, Family, Pickup.";

    // Blank search text.
    public static final String INVALID_KEYWORD = "Search text must not be empty.";

    // Price not a positive number.
    public static final String INVALID_PRICE = "Price must be a positive number.";

    // Year not a whole number from 1900 to 2100.
    public static final String INVALID_YEAR = "Year of manufacture must be from 1900 to 2100.";

    // Speed not a number from 1 to 400.
    public static final String INVALID_SPEED = "Speed must be a number from 1 to 400 km/h.";

    // ----- business errors -----
    // The id is used by another vehicle; %s is the id.
    public static final String ID_EXISTS = "Vehicle ID %s already exists.";

    // The brief's words when an id is not found.
    public static final String NOT_EXIST = "Vehicle does not exist";

    // Search by name matched nothing.
    public static final String NOT_FOUND = "No vehicle found.";

    // A list was asked for while the show room is empty.
    public static final String SHOW_ROOM_EMPTY = "The show room is empty.";

    // The data file is missing; %s is its name.
    public static final String FILE_NOT_FOUND = "Data file %s does not exist.";

    // The data file cannot be read or written; %s is its name.
    public static final String FILE_ERROR = "Cannot access data file %s.";

    // A line of the data file is not a valid vehicle (never shown, the line is counted).
    public static final String DAMAGED_LINE = "Damaged line.";

    // A vehicle kind without a class (programming error).
    public static final String UNKNOWN_TYPE = "Unknown vehicle type.";

    // ----- results -----
    // Function 1 done; %d is the count, %s the file name.
    public static final String LOAD_SUCCESS = "Loaded %d vehicle(s) from %s";

    // Function 1 done but bad lines were skipped; the last %d is how many.
    public static final String LOAD_SKIPPED = "Loaded %d vehicle(s) from %s\n"
            + "%d damaged line(s) were ignored.";

    // Function 2 done.
    public static final String ADD_SUCCESS = "Add successfully!";

    // Function 3 done.
    public static final String UPDATE_SUCCESS = "Update successfully!";

    // Function 4 done.
    public static final String DELETE_SUCCESS = "Delete successfully!";

    // Function 4 failed: no vehicle has the id typed.
    public static final String DELETE_FAILED = "Delete failed!";

    // Function 4 answered N.
    public static final String DELETE_CANCELLED = "Delete cancelled.";

    // Function 7 done; %d is the count, %s the file name.
    public static final String STORE_SUCCESS = "Stored %d vehicle(s) to %s";

    // Last line of every list; %d is the count.
    public static final String TOTAL = "Total: %d vehicle(s)";

    // Shown when the user quits.
    public static final String GOODBYE = "Goodbye.";

    // ----- table -----
    // Header label of the id column.
    public static final String COL_ID = "ID";

    // Header label of the name column.
    public static final String COL_NAME = "Name";

    // Header label of the color column.
    public static final String COL_COLOR = "Color";

    // Header label of the price column.
    public static final String COL_PRICE = "Price";

    // Header label of the brand column.
    public static final String COL_BRAND = "Brand";

    // Header label of the kind column.
    public static final String COL_KIND = "Kind";

    // Header label of the details column.
    public static final String COL_DETAILS = "Details";

    // Details of a car; %s the type, %d the year.
    public static final String CAR_DETAILS = "Type: %s, Year: %d";

    // Details of a motorbike; %.1f the speed, %s Yes/No.
    public static final String MOTORBIKE_DETAILS = "Speed: %.1fkm/h, License: %s";

    // "Yes" in the details.
    public static final String YES_TEXT = "Yes";

    // "No" in the details.
    public static final String NO_TEXT = "No";

    // The brief's sound of a motorbike.
    public static final String MOTORBIKE_SOUND = "Tin tin tin";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
