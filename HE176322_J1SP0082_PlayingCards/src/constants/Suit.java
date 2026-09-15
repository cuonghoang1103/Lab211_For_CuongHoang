package constants;

/**
 * The four suits of a standard deck, in the order the deck is built.
 *
 * @author HE176322
 */
public enum Suit {

    // Clubs - the first suit of a new deck.
    CLUBS("Clubs"),
    // Diamonds.
    DIAMONDS("Diamonds"),
    // Hearts.
    HEARTS("Hearts"),
    // Spades - the last suit of a new deck.
    SPADES("Spades");

    // The word printed on screen.
    private final String label;

    // Creates one suit constant; private because only the four constants above may ever
    // exist.
    private Suit(String label) {
        this.label = label;
    }

    // Returns the word printed on screen.
    public String getLabel() {
        return label;
    }
}
