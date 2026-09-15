package constants;

/**
 * File names, special characters, menu numbers and the sample document the program logic
 * depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // ----- files (at the project root, next to build.xml) -----
    // The file the brief reads.
    public static final String INPUT_FILE = "input.txt";
    // The file the brief writes.
    public static final String OUTPUT_FILE = "output.txt";

    // ----- main menu -----
    // Smallest menu option; also Exit.
    public static final int MENU_EXIT = 0;
    // Menu option 1: create the sample input file.
    public static final int MENU_SAMPLE = 1;
    // Menu option 2: normalize input.txt into output.txt.
    public static final int MENU_NORMALIZE = 2;
    // Menu option 3: show output.txt from the disk.
    public static final int MENU_SHOW_OUTPUT = 3;
    // Menu option 4: normalize one typed line.
    public static final int MENU_TYPED_LINE = 4;
    // Menu option 5: show the rules on sample cases; largest option.
    public static final int MENU_CASES = 5;

    // ----- characters the rules look at -----
    // Comma, dot and colon: the punctuation the brief names.
    public static final String PUNCTUATION = ",.:";
    // Marks that end a sentence (the brief names the dot; ?
    public static final String TERMINATORS = ".?!";
    // The dot the text must end with.
    public static final char DOT = '.';
    // A comma.
    public static final char COMMA = ',';
    // A colon.
    public static final char COLON = ':';
    // The single space between words.
    public static final char SPACE = ' ';
    // Opening typographic quotation mark.
    public static final char OPEN_QUOTE = '“';
    // Closing typographic quotation mark.
    public static final char CLOSE_QUOTE = '”';
    // Straight ASCII quotation mark (same character at both ends).
    public static final char STRAIGHT_QUOTE = '"';
    // Non-breaking space: Character.isWhitespace() says false for it, so the rules list
    // it by hand.
    public static final char NBSP = ' ';
    // Tab character.
    public static final char TAB = '\t';
    // Separator used to join the lines of a file into one text.
    public static final String LINE_BREAK = "\n";
    // How a tab is shown on screen, so it can be seen.
    public static final String VISIBLE_TAB = "\\t";
    // How a non-breaking space is shown on screen.
    public static final String VISIBLE_NBSP = "<nbsp>";

    // The untidy sample document written by menu option 1 - the brief's own example,
    // broken on purpose so every rule has work to do: spaces before punctuation, no space
    // after it, spaces inside quotes, a blank line, a tab, a line of spaces, runs of
    // spaces, no final dot.
    public static final String[] SAMPLE_LINES = {
        "   as you can see , detecting whether a string is normalized can be"
        + " quite efficient.A lot of the cost",
        "of normalizing in the “ second row ” is for the initialization"
        + " of buffers .",
        "",
        "\tThe cost of which is amortized   when one is    processing larger"
        + " strings.",
        "   ",
        "as it turns out,these buffers are rarely needed , so we may change the"
        + " implementation",
        "at some point   to speed up the common case for small strings even"
        + " further"
    };

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
