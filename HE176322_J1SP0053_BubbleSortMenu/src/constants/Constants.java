package constants;

/**
 * Numbers and formats the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Smallest option of the menu.
    public static final int MENU_MIN = 1;

    // Menu option: input the elements.
    public static final int MENU_INPUT = 1;

    // Menu option: sort ascending.
    public static final int MENU_ASCENDING = 2;

    // Menu option: sort descending.
    public static final int MENU_DESCENDING = 3;

    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 4;

    // Smallest length (the brief: "Length of the array must be more than 0").
    public static final int MIN_LENGTH = 1;

    // Largest length, so a typo such as 999999999 cannot exhaust memory.
    public static final int MAX_LENGTH = 1000;

    // One element on screen.
    public static final String ELEMENT_FORMAT = "[%d]";

    // Separator of the ascending screen: [1]->[3]->[5].
    public static final String ARROW_ASCENDING = "->";

    // Separator of the descending screen: [5]<-[3]<-[1].
    public static final String ARROW_DESCENDING = "<-";

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
