package constants;

/**
 * The thirteen ranks of a suit, from the lowest (2) to the highest (Ace).
 *
 * @author HE176322
 */
public enum Rank {

    // Rank 2.
    TWO("2"),
    // Rank 3.
    THREE("3"),
    // Rank 4.
    FOUR("4"),
    // Rank 5.
    FIVE("5"),
    // Rank 6.
    SIX("6"),
    // Rank 7.
    SEVEN("7"),
    // Rank 8.
    EIGHT("8"),
    // Rank 9.
    NINE("9"),
    // Rank 10.
    TEN("10"),
    // Jack.
    JACK("Jack"),
    // Queen.
    QUEEN("Queen"),
    // King.
    KING("King"),
    // Ace - the highest rank, printed last in each suit.
    ACE("Ace");

    // The text printed on screen.
    private final String label;

    // Creates one rank constant; private because only the thirteen constants above may
    // ever exist.
    private Rank(String label) {
        this.label = label;
    }

    // Returns the text printed on screen.
    public String getLabel() {
        return label;
    }
}
