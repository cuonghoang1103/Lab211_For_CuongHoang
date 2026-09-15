package constants;

/**
 * Numbers, patterns, file layout and column layouts the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // ----- main menu -----
    // Smallest option of every menu.
    public static final int MENU_MIN = 1;
    // Function 1: load data from file.
    public static final int MENU_LOAD = 1;
    // Function 2: add new vehicle.
    public static final int MENU_ADD = 2;
    // Function 3: update vehicle by id.
    public static final int MENU_UPDATE = 3;
    // Function 4: delete vehicle by id.
    public static final int MENU_DELETE = 4;
    // Function 5: search vehicle.
    public static final int MENU_SEARCH = 5;
    // Function 6: show vehicle list.
    public static final int MENU_SHOW = 6;
    // Function 7: store data to file.
    public static final int MENU_STORE = 7;
    // Quit; also the largest option of the main menu.
    public static final int MENU_QUIT = 8;

    // ----- sub menus (add, search, show) -----
    // First option: Car / Search by name / Show all.
    public static final int SUB_FIRST = 1;
    // Second option: Motorbike / Search by id / Show by price.
    public static final int SUB_SECOND = 2;
    // Back to main menu; also the largest option of a sub menu.
    public static final int SUB_BACK = 3;

    // ----- input patterns -----
    // One letter and three digits: C001, M001.
    public static final String ID_PATTERN = "[A-Za-z]\\d{3}";
    // 2 to 30 letters, digits, spaces or hyphens, starting with a letter or digit.
    public static final String NAME_PATTERN = "[A-Za-z0-9][A-Za-z0-9 \\-]{1,29}";
    // 2 to 15 letters or spaces, starting with a letter.
    public static final String COLOR_PATTERN = "[A-Za-z][A-Za-z ]{1,14}";
    // 2 to 20 letters, digits, spaces or hyphens.
    public static final String BRAND_PATTERN = "[A-Za-z0-9][A-Za-z0-9 \\-]{1,19}";
    // The four car types, any case.
    public static final String CAR_TYPE_PATTERN = "(?i)(Sport|Travel|Family|Pickup)";
    // Any text that is not blank (checked after trim).
    public static final String KEYWORD_PATTERN = ".+";

    // ----- number limits -----
    // Smallest price.
    public static final double MIN_PRICE = 0.01;
    // Largest price.
    public static final double MAX_PRICE = 1000000000;
    // Oldest year of manufacture.
    public static final double MIN_YEAR = 1900;
    // Newest year of manufacture (a constant, so the rule does not change each year).
    public static final double MAX_YEAR = 2100;
    // Slowest speed.
    public static final double MIN_SPEED = 1;
    // Fastest speed.
    public static final double MAX_SPEED = 400;

    // ----- answer letters -----
    // Answer "yes".
    public static final String YES = "Y";
    // Answer "no".
    public static final String NO = "N";

    // ----- data file -----
    // The brief's file, next to build.xml.
    public static final String DATA_FILE = "vehicles.txt";
    // Separator between the columns of a line.
    public static final String DATA_SEPARATOR = ",";
    // Columns of one line: kind, id, name, color, price, brand, detail 1, detail 2.
    public static final int DATA_COLUMNS = 8;
    // Column of the kind (CAR / MOTORBIKE).
    public static final int COL_KIND = 0;
    // Column of the id.
    public static final int COL_ID = 1;
    // Column of the name.
    public static final int COL_NAME = 2;
    // Column of the color.
    public static final int COL_COLOR = 3;
    // Column of the price.
    public static final int COL_PRICE = 4;
    // Column of the brand.
    public static final int COL_BRAND = 5;
    // First detail column: car type / motorbike speed.
    public static final int COL_DETAIL_1 = 6;
    // Second detail column: car year / motorbike license.
    public static final int COL_DETAIL_2 = 7;

    // ----- table -----
    // One format for the header and every row: id, name, color, price, brand, kind, details.
    public static final String ROW_FORMAT = "%-6s %-16s %-8s %12s %-10s %-10s %s";
    // Line under the header and under the rows.
    public static final String TABLE_LINE = "--------------------------------------------"
            + "--------------------------------------------";
    // Price with thousands and two decimals: 35,000.00.
    public static final String PRICE_PATTERN = "#,##0.00";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
