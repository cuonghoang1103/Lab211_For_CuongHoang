package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // ----- screen -----
    // Title printed once when the program starts.
    public static final String TITLE = "===== Showroom car program =====";

    // Line printed under the title.
    public static final String INPUT_INFO = "Input Information of Car";

    // Prompt for the car name.
    public static final String INPUT_NAME = "Name: ";

    // Prompt for the colour.
    public static final String INPUT_COLOR = "Color: ";

    // Prompt for the price.
    public static final String INPUT_PRICE = "Price: ";

    // Prompt for the day of the week.
    public static final String INPUT_TODAY = "Today: ";

    // Question asked after every check.
    public static final String FIND_MORE = "Do you want find more?(Y/N):";

    // ----- results -----
    // The request matches a car of the showroom.
    public static final String SELL_CAR = "Sell Car";

    // First line of every refusal (curly apostrophe, as in the brief).
    public static final String CANT_SELL = "Can’t sell Car";

    // ----- refusal reasons (CarException messages, from the brief) -----
    // The car name is not a Car of the showroom.
    public static final String CAR_BREAK = "Car break";

    // The colour is not a Color, or this car is not painted in it.
    public static final String COLOR_NOT_EXIST = "Color Car does not exist";

    // The price is zero or negative.
    public static final String PRICE_GREATER_ZERO = "Price greater than zero";

    // The price is not a number.
    public static final String PRICE_DIGIT = "Price is digit";

    // The price is below the car's price (not in the brief; see HUONG-DAN 9).
    public static final String PRICE_NOT_ENOUGH = "Price is not enough";

    // The car is not sold on this day (plain apostrophe, as in the brief).
    public static final String CANT_SELL_TODAY = "Car can't sell today";

    // ----- validation -----
    // The answer to "find more" was neither Y nor N.
    public static final String INVALID_YES_NO = "Please input Y or N.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
