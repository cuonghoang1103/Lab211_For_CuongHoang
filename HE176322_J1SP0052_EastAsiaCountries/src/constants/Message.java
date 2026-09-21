package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title, options and frame of the main menu (the brief's screen).
    public static final String MENU = "                               MENU\n"
            + "=========================================================================="
            + "\n1. Input the information of 11 countries in East Asia\n"
            + "2. Display the information of country you've just input\n"
            + "3. Search the information of country by user-entered name\n"
            + "4. Display the information of countries sorted name in ascending order\n"
            + "5. Exit\n"
            + "==========================================================================";

    // Prompt for the menu choice (the brief's screen).
    public static final String INPUT_CHOICE = "Enter your choice : ";

    // ----- prompts (the value is typed on the next line, as in the brief) -----
    // Prompt for the country code.
    public static final String INPUT_CODE = "Enter code of country:";

    // Prompt for the country name.
    public static final String INPUT_NAME = "Enter name of country:";

    // Prompt for the total area.
    public static final String INPUT_AREA = "Enter total Area:";

    // Prompt for the terrain.
    public static final String INPUT_TERRAIN = "Enter terrain of country:";

    // Prompt for the name to search.
    public static final String INPUT_SEARCH = "Enter the name you want to search for:";

    // ----- table header labels -----
    // Header of the code column.
    public static final String LABEL_ID = "ID";

    // Header of the name column.
    public static final String LABEL_NAME = "Name";

    // Header of the area column.
    public static final String LABEL_AREA = "Total Area";

    // Header of the terrain column.
    public static final String LABEL_TERRAIN = "Terrain";

    // ----- validation errors -----
    // Menu choice not a number or out of range; %d are the bounds.
    public static final String INVALID_CHOICE = "Please choose an option from %d to %d.";

    // A text field was left blank.
    public static final String FIELD_BLANK = "This field must not be blank.";

    // The total area typed is not a number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // The brief: "Total area must be greater than 0".
    public static final String INVALID_AREA = "Total area must be greater than 0.";

    // ----- business errors -----
    // Option 1 when 11 countries are already stored; %d is the capacity.
    public static final String LIST_FULL = "The list already has %d countries.";

    // Options 2 and 4 before any country was entered.
    public static final String LIST_EMPTY = "There is no country in the list.";

    // Option 3 found nothing; %s is the name searched.
    public static final String NOT_FOUND = "No country found with the name [%s].";

    // ----- results -----
    // Shown after a country is added.
    public static final String SUCCESSFUL = "Successful";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
