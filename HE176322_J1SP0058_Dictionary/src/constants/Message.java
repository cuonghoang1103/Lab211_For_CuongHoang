package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // Title and options of the main menu (the brief's screen).
    public static final String MENU = "======== Dictionary program ========\n"
            + "1. Add Word\n"
            + "2. Delete Word\n"
            + "3. Translate\n"
            + "4. Exit";
    // Prompt for the menu choice (the brief's screen).
    public static final String INPUT_CHOICE = "Your choice: ";

    // ----- screen titles (the brief's screens) -----
    // Title printed before the add form.
    public static final String TITLE_ADD = "------------- Add -------------";
    // Title printed before the delete form.
    public static final String TITLE_DELETE = "------------ Delete ----------------";
    // Title printed before the translate form.
    public static final String TITLE_TRANSLATE = "------------- Translate ------------";

    // ----- prompts -----
    // Prompt for the English word.
    public static final String INPUT_ENGLISH = "Enter English: ";
    // Prompt for the Vietnamese meaning.
    public static final String INPUT_VIETNAMESE = "Enter Vietnamese: ";
    // Question asked when the English word is already in the dictionary.
    public static final String ASK_UPDATE
            = "This word already exists. Do you want to update its meaning (Y/N)? ";

    // ----- validation errors -----
    // A menu choice that is not a number.
    public static final String INVALID_NUMBER = "You must input a number.";
    // A menu choice outside the menu; %d are the bounds.
    public static final String INVALID_RANGE = "Value must be between %d and %d.";
    // The English word was left blank.
    public static final String ENGLISH_BLANK = "English word must not be empty.";
    // The English word contains the separator used in the data file.
    public static final String ENGLISH_SEPARATOR = "English word must not contain '='.";
    // The Vietnamese meaning was left blank.
    public static final String VIETNAMESE_BLANK = "Vietnamese word must not be empty.";
    // The answer to the Y/N question was neither Y nor N.
    public static final String INVALID_YES_NO = "Please input Y or N.";

    // ----- business errors -----
    // Delete of a word that is not stored (the brief: "does not exist").
    public static final String KEY_NOT_EXIST = "Key does not exist in the dictionary.";
    // The data file exists but could not be read.
    public static final String CANNOT_READ = "Can't read the dictionary file.";
    // The data file could not be written; the change was undone.
    public static final String CANNOT_WRITE = "Can't write the dictionary file.";

    // ----- results -----
    // Shown after a word is added, updated or deleted (the brief's screen).
    public static final String SUCCESS = "Successful";
    // Shown when the user answers N: the old meaning stays.
    public static final String NOT_UPDATED = "The old meaning is kept.";
    // Label in front of the translation (the brief's screen).
    public static final String LABEL_VIETNAMESE = "Vietnamese: ";
    // Shown when the word is not found (the brief: "display empty").
    public static final String TRANSLATE_EMPTY
            = "Empty - this word is not in the dictionary.";
    // Shown when the user exits.
    public static final String GOODBYE = "Bye";

    // Private constructor: this class only holds constants, so nobody should ever create
    // an object of it.
    private Message() {
    }
}
