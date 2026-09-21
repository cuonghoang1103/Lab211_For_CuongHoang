package model;

import constants.Message;
import constants.Rank;
import constants.Suit;

/**
 * MODEL: one playing card - a rank and a suit, and nothing else.
 *
 * @author HE176322
 */
public class Card {

    // The rank.
    private Rank rank;

    // The suit.
    private Suit suit;

    // JavaBean constructor (MVC of JSP): the first card of a new deck, the 2 of Clubs.
    public Card() {
        this(Rank.TWO, Suit.CLUBS);
    }

    // Creates a card with both properties (the brief's Card(rank, suit)).
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // Returns the rank.
    public Rank getRank() {
        return rank;
    }

    // Returns the suit.
    public Suit getSuit() {
        return suit;
    }

    // Polymorphism: overrides Object.toString() to give "Ace of Spades", the text the
    // brief asks for.
    @Override
    public String toString() {
        return String.format(Message.CARD_FORMAT, rank.getLabel(), suit.getLabel());
    }
}
