package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title and options of the main menu (the brief's screen).
    public static final String MENU = "========= Contact program =========\n"
            + "1. Add a Contact\n"
            + "2. Display all Contact\n"
            + "3. Delete a Contact\n"
            + "4. Exit";

    // Prompt for the menu choice (the brief's screen, both halves).
    public static final String INPUT_CHOICE = "Please choice one option: Your choice: ";

    // ----- screen titles -----
    // Title printed before the add form.
    public static final String TITLE_ADD = "-------- Add a Contact --------";

    // Title printed before the contact table.
    public static final String TITLE_DISPLAY = "--------------------------------- "
            + "Display all Contact ----------------------------";

    // Title printed before the delete form.
    public static final String TITLE_DELETE = "------- Delete a Contact -------";

    // ----- prompts -----
    // Prompt for a text field; %s is Name, Group or Address.
    public static final String INPUT_FIELD = "Enter %s: ";

    // Prompt for the phone.
    public static final String INPUT_PHONE = "Enter Phone: ";

    // Prompt for the ID to delete.
    public static final String INPUT_ID = "Enter ID: ";

    // ----- field / table header labels -----
    // Header of the ID column.
    public static final String LABEL_ID = "ID";

    // Header of the full-name column.
    public static final String LABEL_NAME = "Name";

    // Header of the first-name column.
    public static final String LABEL_FIRST_NAME = "First Name";

    // Header of the last-name column.
    public static final String LABEL_LAST_NAME = "Last Name";

    // Header of the group column.
    public static final String LABEL_GROUP = "Group";

    // Header of the address column.
    public static final String LABEL_ADDRESS = "Address";

    // Header of the phone column.
    public static final String LABEL_PHONE = "Phone";

    // ----- validation errors -----
    // Menu choice not a number or out of range; %d are the bounds.
    public static final String INVALID_CHOICE = "Please choice one option from %d to %d.";

    // A text field was left blank; %s is Name, Group or Address.
    public static final String FIELD_BLANK = "%s must not be blank.";

    // The phone is in none of the seven formats: the brief's own list, bullet for bullet
    // (the bullet is written as an escape to keep the file ASCII).
    public static final String INVALID_PHONE = "Please input Phone flow\n"
            + "\u2022 1234567890\n"
            + "\u2022 123-456-7890\n"
            + "\u2022 123-456-7890 x1234\n"
            + "\u2022 123-456-7890 ext1234\n"
            + "\u2022 (123)-456-7890\n"
            + "\u2022 123.456.7890\n"
            + "\u2022 123 456 7890";

    // The ID typed is not a positive whole number (the brief's screen).
    public static final String ID_DIGIT = "ID is digit";

    // ----- results -----
    // Shown after an add or a delete (the brief's screen).
    public static final String SUCCESSFUL = "Successful";

    // No contact with this ID, or nothing to display (the brief).
    public static final String NOT_FOUND = "No found contact";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
