package constants;

/**
 * Numbers, patterns, file layouts and column layouts the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // ----- menu -----
    // Smallest option.
    public static final int MENU_MIN = 1;
    // Function 1: login.
    public static final int MENU_LOGIN = 1;
    // Function 2: search asset by name.
    public static final int MENU_SEARCH = 2;
    // Function 3: create new asset.
    public static final int MENU_CREATE = 3;
    // Function 4: update asset.
    public static final int MENU_UPDATE = 4;
    // Function 5: approve a request.
    public static final int MENU_APPROVE = 5;
    // Function 6: show the borrowed assets.
    public static final int MENU_BORROWS = 6;
    // Quit; also the largest option.
    public static final int MENU_QUIT = 7;

    // ----- roles (column 4 of employee.dat) -----
    // Role of the manager.
    public static final String ROLE_MANAGER = "MA";
    // Role of an employee.
    public static final String ROLE_EMPLOYEE = "EM";

    // ----- data files (next to build.xml) -----
    // Assets.
    public static final String ASSET_FILE = "asset.dat";
    // Employees and the manager.
    public static final String EMPLOYEE_FILE = "employee.dat";
    // Borrow requests waiting for the manager.
    public static final String REQUEST_FILE = "request.dat";
    // Approved borrows.
    public static final String BORROW_FILE = "borrow.dat";
    // Separator used to split a line.
    public static final String DATA_SEPARATOR = ",";
    // Separator used to write a line (the brief's files have a space after the comma).
    public static final String DATA_JOINER = ", ";

    // ----- columns of asset.dat -----
    // Columns of one asset line.
    public static final int ASSET_COLUMNS = 6;
    // Column of the asset id.
    public static final int ASSET_ID = 0;
    // Column of the name.
    public static final int ASSET_NAME = 1;
    // Column of the color.
    public static final int ASSET_COLOR = 2;
    // Column of the price.
    public static final int ASSET_PRICE = 3;
    // Column of the weight.
    public static final int ASSET_WEIGHT = 4;
    // Column of the quantity.
    public static final int ASSET_QUANTITY = 5;

    // ----- columns of employee.dat -----
    // Columns of one employee line.
    public static final int EMPLOYEE_COLUMNS = 6;
    // Column of the employee id.
    public static final int EMPLOYEE_ID = 0;
    // Column of the name.
    public static final int EMPLOYEE_NAME = 1;
    // Column of the birthdate.
    public static final int EMPLOYEE_BIRTHDATE = 2;
    // Column of the role.
    public static final int EMPLOYEE_ROLE = 3;
    // Column of the sex.
    public static final int EMPLOYEE_SEX = 4;
    // Column of the MD5 password.
    public static final int EMPLOYEE_PASSWORD = 5;

    // ----- columns of request.dat and borrow.dat -----
    // Columns of one request or borrow line.
    public static final int TRANSACTION_COLUMNS = 5;
    // Column of the request or borrow id.
    public static final int TRANSACTION_ID = 0;
    // Column of the asset id.
    public static final int TRANSACTION_ASSET = 1;
    // Column of the employee id.
    public static final int TRANSACTION_EMPLOYEE = 2;
    // Column of the quantity.
    public static final int TRANSACTION_QUANTITY = 3;
    // Column of the date and time.
    public static final int TRANSACTION_DATE = 4;

    // ----- ids and dates -----
    // First letter of a borrow id.
    public static final String BORROW_PREFIX = "B";
    // A new id: prefix and a 3-digit number, e.g. B008.
    public static final String ID_FORMAT = "%s%03d";
    // The number part of an id: digits only.
    public static final String DIGITS_PATTERN = "\\d+";
    // The brief's date and time: 23-12-2021 13:17:56.
    public static final String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss";

    // ----- MD5 -----
    // Name of the hash of employee.dat (MD5 of 123456 = e10adc39...).
    public static final String MD5_ALGORITHM = "MD5";
    // Two lowercase hex digits per byte.
    public static final String HEX_FORMAT = "%02x";

    // ----- input patterns -----
    // The letter A and 3 digits, any case: A001.
    public static final String ASSET_ID_PATTERN = "(?i)A\\d{3}";
    // 1 to 40 characters without commas (a comma would break the file).
    public static final String TEXT_PATTERN = "[^,]{1,40}";
    // 1 to 10 characters, no comma, no space.
    public static final String ID_PATTERN = "[^,\\s]{1,10}";
    // Anything not blank.
    public static final String NOT_BLANK_PATTERN = ".+";

    // ----- number limits -----
    // Smallest positive double: "greater than 0".
    public static final double MIN_POSITIVE = Double.MIN_VALUE;
    // Largest price.
    public static final double MAX_PRICE = 1000000000;
    // Largest weight.
    public static final double MAX_WEIGHT = 100000;
    // Smallest quantity.
    public static final double MIN_QUANTITY = 0;
    // Largest quantity.
    public static final double MAX_QUANTITY = 1000000;

    // ----- answer letters -----
    // Answer "yes".
    public static final String YES = "Y";
    // Answer "no".
    public static final String NO = "N";

    // ----- tables -----
    // Asset row: id, name, color, price, weight, quantity.
    public static final String ASSET_ROW = "%-7s%-22s%-8s%10s%9s%6s";
    // Request / borrow row: id, asset, asset name, employee, employee name, qty, date.
    public static final String TRANSACTION_ROW = "%-7s%-7s%-20s%-9s%-19s%5s  %s";
    // Line under headers and under rows.
    public static final String TABLE_LINE = "--------------------------------------"
            + "--------------------------------------";
    // Two decimals: 500.00.
    public static final String DECIMAL_PATTERN = "0.00";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
