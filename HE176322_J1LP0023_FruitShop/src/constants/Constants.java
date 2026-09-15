package constants;

/**
 * Numbers, answer letters and column layouts the program logic depends on.
 *
 * @author HE176322
 */
public final class Constants {

    // ----- main menu -----
    // Smallest option of the main menu.
    public static final int MENU_MIN = 1;
    // Menu option 1: create fruits.
    public static final int MENU_CREATE = 1;
    // Menu option 2: view orders.
    public static final int MENU_VIEW_ORDERS = 2;
    // Menu option 3: shopping.
    public static final int MENU_SHOPPING = 3;
    // Menu option 4: exit; also the largest option.
    public static final int MENU_EXIT = 4;

    // ----- business rules -----
    // Item 0 of the fruit list means "return to main screen".
    public static final int RETURN_ITEM = 0;
    // Smallest quantity in stock a new fruit may have.
    public static final int MIN_STOCK = 0;
    // Smallest quantity a buyer may order.
    public static final int MIN_ORDER_QUANTITY = 1;

    // ----- answer letters -----
    // Answer "yes" to a (Y/N) question.
    public static final String YES = "Y";
    // Answer "no" to a (Y/N) question.
    public static final String NO = "N";

    // ----- screen layouts -----
    // Money without useless decimals: 2 -> "2", 2.5 -> "2.5".
    public static final String MONEY_PATTERN = "0.##";
    // Sign written after every amount, like the brief's "2$".
    public static final String CURRENCY = "$";
    // One row of the owner's table: item, name, origin, price, quantity.
    public static final String STOCK_ROW_FORMAT = "%12d  %-18s %-14s %-13s %d";
    // One row of the buyer's table: item, name, origin, price.
    public static final String LIST_ROW_FORMAT = "%12d  %-18s %-14s %s";
    // One row of the cart: product, quantity, price, amount.
    public static final String CART_ROW_FORMAT = "%-16s %8d %8s %8s";
    // One row of a saved order: number, product, quantity, price, amount.
    public static final String ORDER_ROW_FORMAT = "%d. %-16s %8d %8s %8s";

    // Private constructor: a holder of constants is never instantiated.
    private Constants() {
    }
}
