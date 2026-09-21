package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- menu -----
    // The main menu.
    public static final String MENU = "============ TEXT NORMALIZER ============\n"
            + "1. Create the sample input file (input.txt)\n"
            + "2. Normalize input.txt into output.txt\n"
            + "3. Show output.txt from the disk\n"
            + "4. Normalize one line typed on the keyboard\n"
            + "5. Show the rules on sample cases\n"
            + "0. Exit\n"
            + "========================================";

    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Your choice: ";

    // Prompt of option 4.
    public static final String INPUT_LINE = "Type the line to normalize:";

    // ----- panels -----
    // Horizontal rule around every panel.
    public static final String RULE = "--------------------------------------------------";

    // Title of the before panel; %s file, %s line count.
    public static final String TITLE_BEFORE = "BEFORE - %s (%s)";

    // Title of the after panel; %s file, %s line count.
    public static final String TITLE_AFTER = "AFTER - %s (%s)";

    // Title of the on-disk panel; %s file, %s line count.
    public static final String TITLE_ON_DISK = "ON DISK - %s (%s)";

    // Title of the sample cases panel.
    public static final String TITLE_CASES = "RULES ON SAMPLE CASES";

    // One line count in the singular.
    public static final String ONE_LINE = "%d line";

    // A line count in the plural.
    public static final String MANY_LINES = "%d lines";

    // One numbered raw line; %d number, %s the line with tabs shown.
    public static final String RAW_LINE = "%d: [%s]";

    // Input of option 4.
    public static final String TYPED_IN = "IN : [%s]";

    // Output of option 4.
    public static final String TYPED_OUT = "OUT: [%s]";

    // Number and title of one sample case.
    public static final String CASE_TITLE = "%2d. %s";

    // Input of one sample case.
    public static final String CASE_IN = "    IN : [%s]";

    // Output of one sample case.
    public static final String CASE_OUT = "    OUT: [%s]";

    // ----- results -----
    // Option 1 done; %s file, %s line count.
    public static final String SAMPLE_WRITTEN = "Sample input written to %s (%s).";

    // Option 2 done; %s file.
    public static final String NORMALIZED_WRITTEN = "Normalized document written to %s.";

    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // ----- input errors -----
    // The menu choice was not a whole number.
    public static final String INVALID_NUMBER = "You must input a number.";

    // Menu choice outside the allowed range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";

    // ----- file errors (the brief: "use Exception to handle ... read or write") -----
    // The file is not on the disk; %s file.
    public static final String FILE_NOT_FOUND = "Error: File not found: %s";

    // The path is a folder; %s path.
    public static final String NOT_A_FILE = "Error: Not a file: %s";

    // The file exists but cannot be read; %s file.
    public static final String CANNOT_READ = "Error: Cannot read the file: %s";

    // The file cannot be written; %s file.
    public static final String CANNOT_WRITE = "Error: Cannot write the file: %s";

    // Option 5: the cases that break naive solutions, {title, input}.
    public static final String[][] SAMPLE_CASE_ARRAY = {
        {"leading and trailing spaces", "   hello world   "},
        {"a run of many spaces", "a    b     c"},
        {"tabs mixed with spaces", "a \t  b\t\tc"},
        {"empty text", ""},
        {"spaces only", "     "},
        {"punctuation glued to the next word", "one ,two.three:four"},
        {"space in front of punctuation", "sentence one . sentence two"},
        {"capitals in the middle", "the QUICK brown FOX jumps"},
        {"single-letter words", "a dog. i saw i and a cat"},
        {"curly quotes with spaces inside",
            "he said “  hello there  ” loudly"},
        {"straight quotes with spaces inside", "he said \"  hello there  \" loudly"},
        {"comma at the very end", "this ends with a comma ,"},
        {"a decimal number", "the price is 3.14 dollars"},
        {"a non-breaking space", "word word and more"},
        {"Vietnamese, non-ASCII",
            "  chào   bạn ,tôi tên là cường.rất vui"},
        {"already normalized", "This is fine. It is already correct."}
    };

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
