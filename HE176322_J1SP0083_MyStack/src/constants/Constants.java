package constants;

/**
 * Numbers the program logic depends on: the menu options.
 *
 * @author HE176322
 */
public final class Constants {

    // Menu option: exit; also the smallest option.
    public static final int MENU_EXIT = 0;

    // Menu option: push a value.
    public static final int MENU_PUSH = 1;

    // Menu option: pop the top value.
    public static final int MENU_POP = 2;

    // Menu option: get (peek) the top value.
    public static final int MENU_GET = 3;

    // Menu option: display the stack; also the largest option.
    public static final int MENU_DISPLAY = 4;

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
