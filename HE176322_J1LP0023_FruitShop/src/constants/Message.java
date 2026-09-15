package constants;

/**
 * Every message the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- main screen (the brief's wording) -----
    // Blank line, then the brief's main screen with the sentence under the options.
    public static final String MENU = "\nFRUIT SHOP SYSTEM\n"
            + "1. Create Fruit\n"
            + "2. View orders\n"
            + "3. Shopping (for buyer)\n"
            + "4. Exit\n"
            + "(Please choose 1 to create product, 2 to view order, "
            + "3 for shopping, 4 to Exit program).";
    // Prompt for the menu choice.
    public static final String INPUT_CHOICE = "Your choice: ";

    // ----- create fruit -----
    // Prompt for the fruit id.
    public static final String INPUT_FRUIT_ID = "Fruit ID: ";
    // Prompt for the fruit name.
    public static final String INPUT_FRUIT_NAME = "Fruit name: ";
    // Prompt for the price.
    public static final String INPUT_PRICE = "Price: ";
    // Prompt for the quantity in stock.
    public static final String INPUT_STOCK = "Quantity: ";
    // Prompt for the origin.
    public static final String INPUT_ORIGIN = "Origin: ";
    // The brief's question after each fruit.
    public static final String ASK_CONTINUE = "Do you want to continue (Y/N)? ";
    // A fruit was created; %s is its id.
    public static final String CREATE_SUCCESS = "Fruit %s has been created.";

    // ----- fruit tables -----
    // Title above both fruit tables.
    public static final String LIST_TITLE = "List of Fruit:";
    // Header of the owner's table (with the quantity in stock).
    public static final String STOCK_HEADER = "| ++ Item ++ | ++ Fruit Name ++ "
            + "| ++ Origin ++ | ++ Price ++ | ++ Quantity ++ |";
    // Header of the buyer's table (the brief's "List of Fruit").
    public static final String LIST_HEADER = "| ++ Item ++ | ++ Fruit Name ++ "
            + "| ++ Origin ++ | ++ Price ++ |";

    // ----- shopping -----
    // Prompt for the item number.
    public static final String INPUT_ITEM = "Please choose item (0 to return to main screen): ";
    // The brief's line after an item is chosen; %s is the fruit name.
    public static final String SELECTED = "You selected: %s";
    // Prompt for the quantity to buy.
    public static final String INPUT_ORDER_QUANTITY = "Please input quantity: ";
    // The brief's question after a quantity (written without "?" like the brief).
    public static final String ASK_ORDER_NOW = "Do you want to order now (Y/N) ";
    // Header of the cart and of every order.
    public static final String ORDER_HEADER = "Product | Quantity | Price | Amount";
    // Last line of the cart and of every order; %s is the money text.
    public static final String TOTAL = "Total: %s";
    // Prompt for the customer name.
    public static final String INPUT_NAME = "Input your name: ";
    // The order was saved; %s is the customer name.
    public static final String ORDER_SUCCESS = "Thank you %s, your order has been saved.";
    // The buyer left with a non-empty cart.
    public static final String ORDER_CANCELLED = "Your order has been cancelled.";

    // ----- view orders -----
    // First line of one order; %s is the customer name.
    public static final String CUSTOMER = "Customer: %s";

    // ----- input errors -----
    // A value that must be a number was not one.
    public static final String INVALID_NUMBER = "You must input a number.";
    // Choice outside the allowed range; %d are the bounds.
    public static final String INVALID_RANGE = "Please choose from %d to %d.";
    // Answer that is not Y or N.
    public static final String INVALID_YES_NO = "Please enter Y or N.";
    // A text field was left blank.
    public static final String EMPTY_FIELD = "This field must not be empty.";
    // Price of 0 or below.
    public static final String INVALID_PRICE = "Price must be greater than 0.";
    // Quantity in stock below 0.
    public static final String INVALID_STOCK = "Quantity must not be negative.";
    // Quantity to buy of 0 or below.
    public static final String INVALID_ORDER_QUANTITY = "Quantity must be greater than 0.";

    // ----- business errors -----
    // The fruit id is already used; %s is the id.
    public static final String ID_EXISTS = "Fruit ID %s already exists.";
    // Every unit of this fruit is sold or in the cart; %s is the fruit name.
    public static final String OUT_OF_STOCK = "%s is out of stock.";
    // The buyer wants more than is left; %d the units left, %s the fruit name.
    public static final String NOT_ENOUGH = "Only %d %s left in stock.";
    // Shopping chosen before any fruit exists.
    public static final String NO_FRUIT = "There is no fruit in the shop yet.";
    // View orders chosen before anyone ordered.
    public static final String NO_ORDER = "There is no order yet.";
    // Shown when the user exits.
    public static final String GOODBYE = "Goodbye.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
