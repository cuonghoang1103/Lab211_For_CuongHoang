package constants;

/**
 * Numbers and formats the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // Menu option: input a country; also the smallest option.
    public static final int MENU_INPUT = 1;
    // Menu option: display the country just entered.
    public static final int MENU_RECENT = 2;
    // Menu option: search by name.
    public static final int MENU_SEARCH = 3;
    // Menu option: display sorted by name.
    public static final int MENU_SORT = 4;
    // Menu option: exit; also the largest option.
    public static final int MENU_EXIT = 5;

    // How many countries the program keeps (the brief: "11 countries").
    public static final int MAX_COUNTRIES = 11;
    // The total area must be greater than this (the brief).
    public static final float MIN_AREA = 0;

    // The three columns a Country owns: code, name, total area, each 16 characters wide
    // as in the brief's table.
    public static final String COUNTRY_FORMAT = "%-16s%-16s%-16s";
    // Header: the same three widths, then the Terrain column.
    public static final String HEADER_FORMAT = COUNTRY_FORMAT + "%s";

    // Private constructor: this class only holds constants.
    private Constants() {
    }
}
