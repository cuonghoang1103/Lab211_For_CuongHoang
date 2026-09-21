package constants;

/**
 * Every message and label the program shows on screen.
 *
 * @author HE176322
 */
public final class Message {

    // Title line of the brief's screen.
    public static final String TITLE = "========= DECK OF CARDS =========";

    // Second line of the brief's screen; %d is the number of cards.
    public static final String DECK_CREATED = "Deck created with %d cards.";

    // One numbered line of the deck.
    public static final String CARD_LINE = "%d. %s";

    // Line under the deck (the brief's screen).
    public static final String FOOTER = "=================================";

    // Last line of the brief's screen; %d is the number of cards.
    public static final String TOTAL = "Total: %d cards";

    // How one card reads.
    public static final String CARD_FORMAT = "%s of %s";

    // Title of the optional shuffle/deal part; %d is the hand size.
    public static final String DEAL_TITLE = "--- After shuffling, dealing %d cards ---";

    // One dealt card.
    public static final String DEALT_CARD = "- %s";

    // How many cards stay in the deck after dealing.
    public static final String CARDS_LEFT = "Cards left in the deck: %d";

    // deal() was called on a deck that has no card left.
    public static final String EMPTY_DECK = "The deck is empty.";

    // Private constructor: this class only holds constants.
    private Message() {
    }
}
